package com.song.example.study.wanandroid.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.song.example.study.wanandroid.AppTestUtils
import com.song.example.study.wanandroid.main.home.article.ArticleDAO
import com.song.example.study.wanandroid.main.home.article.ArticlePO
import com.song.example.study.wanandroid.main.home.banner.BannerDAO
import com.song.example.study.wanandroid.main.home.banner.BannerPO
import com.song.example.study.wanandroid.network.WanService
import com.song.example.study.common.network.RequestStatus
import com.song.example.study.common.network.retrofit.HttpResult
import com.song.example.study.common.network.retrofit.LifecycleCallExtensionKtPath
import com.song.example.study.common.network.retrofit.NetworkError
import com.song.example.study.common.network.retrofit.awaitWithTimeout
import com.song.example.study.wanandroid.data.wanAppDbModule
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.util.concurrent.TimeoutException
import kotlin.test.assertNull

/**
 * @author Listensong
 * @package com.song.example.study.app.main.home
 * @fileName HomeRepositoryTest
 * @date on 3/29/2020 4:51 PM
 * @desc: TODO
 * @email No
 */
@ExperimentalCoroutinesApi
class HomeRepositoryTest: KodeinAware {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val testScope = TestCoroutineScope()


    private val apiService: WanService = mockk()
    private val articleDAO: ArticleDAO = mockk()
    private val bannerDAO: BannerDAO = mockk()
    private val repository: HomeRepository by instance()

    override val kodein = Kodein.lazy {
        import(wanAppDbModule)
        import(wanHomeKodeinModule)
        bind<WanService>() with singleton { apiService }
        bind<ArticleDAO>(overrides = true) with singleton { articleDAO }
        bind<BannerDAO>(overrides = true) with singleton { bannerDAO }
    }


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        mockkStatic(LifecycleCallExtensionKtPath)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
        testScope.cleanupTestCoroutines()
        unmockkAll()
    }

    @Test
    fun getBanner_whenRepositoryGetBannerThenBannerDaoGetBannerCalled() {
        every {
            bannerDAO.getBanners()
        } returns mockk()
        repository.getBanners()
        verify(exactly = 1) {
            bannerDAO.getBanners()
        }
    }

    @Test
    fun requestBanners_whenNormalResponseThenParseSuccessfully() = runBlocking {
        val slotPOList = givenNormalBannerResponseAndCapturingSlot()

        repository.requestBanners()
        verify(exactly = 1) {
            bannerDAO.clearAndInsert(any())
        }
        assertEquals(4, slotPOList.captured.size)

        val requestStatusValue = repository.requestStatus.value
        assertTrue(requestStatusValue is RequestStatus.Complete)
        assertNull((requestStatusValue as RequestStatus.Complete).err)
    }

    private fun givenNormalBannerResponseAndCapturingSlot(): CapturingSlot<List<BannerPO>> {
        val mockResponseBody = AppTestUtils.generateMockResponseBody(HomeTestConst.WAN_HOME_BANNER_FILE)
        coEvery {
            apiService.getBannerList().awaitWithTimeout(10000)
        } returns HttpResult.Okay(mockResponseBody, mockk())

        val slotPOList = slot<List<BannerPO>>()
        every {
            bannerDAO.clearAndInsert(capture(slotPOList))
        } just Runs
        return slotPOList
    }

    @Test
    fun requestBanners_whenTimeoutThenReturnEmptyList() = runBlockingTest {
        val timeoutMillis = givenGetBannerListWithTimeoutMillis()

        repository.requestBanners()
        val requestStatusValue = repository.requestStatus.value
        assertTrue(requestStatusValue is RequestStatus.Complete)
        assertTrue((requestStatusValue as RequestStatus.Complete).err is NetworkError)
        assertEquals(0, requestStatusValue.err?.errorCode)
        assertEquals("Timeout: no response within $timeoutMillis", requestStatusValue.err?.errorMessage)
    }

    private fun givenGetBannerListWithTimeoutMillis(): Long {
        val timeoutMillis = 10000L
        coEvery {
            apiService.getBannerList().awaitWithTimeout(10000)
        } returns HttpResult.Error(
                NetworkError(
                        0, "Timeout: no response within $timeoutMillis",
                        TimeoutException("Timeout: no response within $timeoutMillis")
                )
        )
        return timeoutMillis
    }

    @Test
    fun getArticles_whenRepositoryGetArticleThenArticleDaoGetArticlesCalled() {
        every {
            articleDAO.getArticles()
        } returns mockk()
        repository.getArticles()
        verify(exactly = 1) {
            articleDAO.getArticles()
        }
    }

    @Test
    fun requestTopArticles_ThenParseDoClearRangeAndInsert() = runBlocking {
        val (slotPOList, slotStartIndex, slotEndIndex) = givenHomeTopArticlesData()

        repository.requestTopArticles()
        verify(exactly = 1) {
            articleDAO.clearRangeAndInsert(any(), any(), any())
        }
        // 5 top article + 1 banner
        assertEquals(6, slotPOList.captured.size)
        assertEquals(HomeConst.BASE_INDEX_TOP_ARTICLE, slotStartIndex.captured)
        assertEquals(HomeConst.BASE_INDEX_ARTICLE - 1, slotEndIndex.captured)

    }

    private fun givenHomeTopArticlesData():
            Triple<CapturingSlot<List<ArticlePO>>, CapturingSlot<Int>, CapturingSlot<Int>> {
        val mockResponseBody = AppTestUtils.generateMockResponseBody(HomeTestConst.WAN_HOME_TOP_ARTICLE_FILE)
        coEvery {
            apiService.getTopArticles().awaitWithTimeout(10000)
        } returns HttpResult.Okay(
                mockResponseBody,
                mockk()
        )

        val slotPOList = slot<List<ArticlePO>>()
        val slotStartIndex = slot<Int>()
        val slotEndIndex = slot<Int>()
        every {
            articleDAO.clearRangeAndInsert(
                    capture(slotStartIndex),
                    capture(slotEndIndex),
                    capture(slotPOList))
        } just Runs
        return Triple(slotPOList, slotStartIndex, slotEndIndex)
    }

    @Test
    fun requestArticles_whenPageNum0ForceRefreshThenDaoClearAboveAndInsert() = runBlocking {
        val (slotPOList, slotAboveIndexList) = givenHomeArticleDataWithPageNum0()

        repository.requestArticles(0)
        verify(exactly = 1) {
            articleDAO.clearAboveAndInsert(any(), any())
        }
        assertEquals(HomeConst.BASE_INDEX_ARTICLE, slotAboveIndexList.captured)
        assertEquals(21, slotPOList.captured.size)
        assertEquals(HomeConst.BASE_INDEX_BANNER, slotPOList.captured[0]._index)
        assertEquals(HomeConst.ITEM_TYPE_BANNER, slotPOList.captured[0].itemType)
    }

    private fun givenHomeArticleDataWithPageNum0(): Pair<CapturingSlot<List<ArticlePO>>, CapturingSlot<Int>> {
        val mockResponseBody = AppTestUtils.generateMockResponseBody(HomeTestConst.WAN_HOME_ARTICLE_FILE)
        coEvery {
            apiService.getArticleList(0).awaitWithTimeout(10000)
        } returns HttpResult.Okay(
                mockResponseBody,
                mockk()
        )

        val slotPOList = slot<List<ArticlePO>>()
        val slotAboveIndexList = slot<Int>()
        every {
            articleDAO.clearAboveAndInsert(
                    capture(slotAboveIndexList),
                    capture(slotPOList)
            )
        } just Runs
        return Pair(slotPOList, slotAboveIndexList)
    }

    @Test
    fun requestArticles_whenPageNum1NormalResponseThenParseDoInsert() = runBlocking {
        givenHomeArticleDataWithPageNum1()

        repository.requestArticles(1)
        verify(exactly = 1) {
            articleDAO.insert(any())
        }
    }

    private fun givenHomeArticleDataWithPageNum1() {
        val mockResponseBody = AppTestUtils.generateMockResponseBody(HomeTestConst.WAN_HOME_ARTICLE_FILE)
        coEvery {
            apiService.getArticleList(1).awaitWithTimeout(10000)
        } returns HttpResult.Okay(
                mockResponseBody,
                mockk()
        )

        every {
            articleDAO.insert(any())
        } just Runs
    }

    @Test
    fun requestArticles_whenTimeoutThenReturnEmptyList() = runBlockingTest {
        coEvery {
            apiService.getArticleList().awaitWithTimeout(10000)
        } returns  HttpResult.Error(
                NetworkError(
                        0, "Timeout: no response within 10000",
                        TimeoutException("Timeout: no response within 10000")
                )
        )

        repository.requestArticles(0)
        val requestStatusValue = repository.requestStatus.value
        assertTrue(requestStatusValue is RequestStatus.Complete)
        assertTrue((requestStatusValue as RequestStatus.Complete).err is NetworkError)
    }




}