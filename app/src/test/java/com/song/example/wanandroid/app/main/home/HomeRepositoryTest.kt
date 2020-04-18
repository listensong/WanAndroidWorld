package com.song.example.wanandroid.app.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.song.example.wanandroid.app.main.home.article.ArticleDAO
import com.song.example.wanandroid.app.main.home.article.ArticlePO
import com.song.example.wanandroid.app.main.home.banner.BannerDAO
import com.song.example.wanandroid.app.main.home.banner.BannerPO
import com.song.example.wanandroid.app.main.home.banner.BannerVO
import com.song.example.wanandroid.app.network.BaseWanApiCallMock
import com.song.example.wanandroid.app.network.WanService
import com.song.example.wanandroid.common.network.RequestStatus
import com.song.example.wanandroid.common.network.retrofit.HttpResult
import com.song.example.wanandroid.common.network.retrofit.LifecycleCallExtensionKtPath
import com.song.example.wanandroid.common.network.retrofit.NetworkError
import com.song.example.wanandroid.common.network.retrofit.awaitWithTimeout
import io.mockk.*
import kotlinx.coroutines.Dispatchers
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
import java.util.concurrent.TimeoutException
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * @author Listensong
 * @package com.song.example.wanandroid.app.main.home
 * @fileName HomeRepositoryTest
 * @date on 3/29/2020 4:51 PM
 * @desc: TODO
 * @email No
 */
class HomeRepositoryTest: BaseWanApiCallMock() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val testScope = TestCoroutineScope()

    private var homeRepository: HomeRepository? = null

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
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
        val mockBannerDataSource = mockk<BannerDAO>()
        every {
            mockBannerDataSource.getBanners()
        } returns mockk()
        homeRepository = HomeRepository(mockk(), mockBannerDataSource, mockk())
        homeRepository?.getBanners()
        verify(exactly = 1) {
            mockBannerDataSource.getBanners()
        }
    }

    @Test
    fun getBannerList_whenNormalResponseThenParseSuccessfully() = runBlocking {
        val mockResponseBody = getMockResponseBody("$BASE_PATH/BannerJson.json")
        mockkStatic(LifecycleCallExtensionKtPath)
        val mockApiService = configWanApiCallMock(
                wanServiceAction = {
                    getBannerList()
                },
                lifecycleCallActionMock = {
                    coEvery {
                        it.awaitWithTimeout(10000)
                    } returns HttpResult.Okay(
                            mockResponseBody,
                            mockk()
                    )
                }
        )

        val slotPOList = slot<List<BannerPO>>()
        val mockBannerDataSource = mockk<BannerDAO>()
        every {
            mockBannerDataSource.clearAndInsert(capture(slotPOList))
        } just Runs

        homeRepository = HomeRepository(mockApiService, mockBannerDataSource, mockk())
        homeRepository?.requestBanners()
        verify(exactly = 1) {
            mockBannerDataSource.clearAndInsert(any())
        }
        assertEquals(4, slotPOList.captured.size)

        val requestStatusValue = homeRepository?.requestStatus?.value
        assertTrue(requestStatusValue is RequestStatus.Complete)
        assertNull((requestStatusValue as RequestStatus.Complete).err)
    }

    @Test
    fun getBannerList_whenTimeoutThenReturnEmptyList() = runBlockingTest {
        mockkStatic(LifecycleCallExtensionKtPath)
        val timeoutMillis = 10000L
        val mockApiService = configWanApiCallMock(
                wanServiceAction = {
                    getBannerList()
                },
                lifecycleCallActionMock = {
                    coEvery {
                        it.awaitWithTimeout(timeoutMillis)
                    } returns HttpResult.Error(
                            NetworkError(
                                    0, "Timeout: no response within $timeoutMillis",
                                    TimeoutException("Timeout: no response within $timeoutMillis")
                            )
                    )
                }
        )

        homeRepository = HomeRepository(mockApiService, mockk(), mockk())
        homeRepository?.requestBanners()
        val requestStatusValue = homeRepository?.requestStatus?.value
        assertTrue(requestStatusValue is RequestStatus.Complete)
        assertTrue((requestStatusValue as RequestStatus.Complete).err is NetworkError)
        assertEquals(0, requestStatusValue.err?.errorCode)
        assertEquals("Timeout: no response within $timeoutMillis", requestStatusValue.err?.errorMessage)
    }


//    @Test
//    fun initArticlesPageList_whenInitZeroArticleCalledThenRequestArticlesCalled() {
////TODO
//    }


    @Test
    fun getArticles_whenRepositoryGetArticleThenArticleDaoGetArticlesCalled() {
        val mockArticleDataSource = mockk<ArticleDAO>()
        every {
            mockArticleDataSource.getArticles()
        } returns mockk()
        homeRepository = HomeRepository(mockk(), mockk(), mockArticleDataSource)
        homeRepository?.getArticles()
        verify(exactly = 1) {
            mockArticleDataSource.getArticles()
        }
    }


    @Test
    fun requestTopArticles_ThenParseDoClearRangeAndInsert() = runBlocking {
        val mockResponseBody = getMockResponseBody("$BASE_PATH/HomeTopArticle.json")
        mockkStatic(LifecycleCallExtensionKtPath)
        val mockApiCallImpl = configWanApiCallMock(
                wanServiceAction = {
                    getTopArticles()
                },
                lifecycleCallActionMock = {
                    coEvery {
                        it.awaitWithTimeout(10000)
                    } returns HttpResult.Okay(
                            mockResponseBody,
                            mockk()
                    )
                }
        )

        val slotPOList = slot<List<ArticlePO>>()
        val slotStartIndex = slot<Int>()
        val slotEndIndex = slot<Int>()
        val mockArticleDataSource = mockk<ArticleDAO>()
        every {
            mockArticleDataSource.clearRangeAndInsert(
                    capture(slotStartIndex),
                    capture(slotEndIndex),
                    capture(slotPOList))
        } just Runs

        homeRepository = HomeRepository(mockApiCallImpl, mockk(), mockArticleDataSource)
        homeRepository?.requestTopArticles()
        verify(exactly = 1) {
            mockArticleDataSource.clearRangeAndInsert(any(), any(), any())
        }
        assertEquals(5, slotPOList.captured.size)
        assertEquals(HomeConst.BASE_INDEX_TOP_ARTICLE, slotStartIndex.captured)
        assertEquals(HomeConst.BASE_INDEX_ARTICLE - 1, slotEndIndex.captured)

    }

    @Test
    fun requestArticles_whenPageNum0ForceRefreshThenDaoClearAboveAndInsert() = runBlocking {
        val mockResponseBody = getMockResponseBody("$BASE_PATH/HomeArticleJson.json")
        mockkStatic(LifecycleCallExtensionKtPath)
        val mockApiCallImpl = configWanApiCallMock(
                wanServiceAction = {
                    getArticleList(0)
                },
                lifecycleCallActionMock = {
                    coEvery {
                        it.awaitWithTimeout(10000)
                    } returns HttpResult.Okay(
                            mockResponseBody,
                            mockk()
                    )
                }
        )

        val slotPOList = slot<List<ArticlePO>>()
        val slotAboveIndexList = slot<Int>()
        val mockArticleDataSource = mockk<ArticleDAO>()
        every {
            mockArticleDataSource.clearAboveAndInsert(
                    capture(slotAboveIndexList),
                    capture(slotPOList)
            )
        } just Runs

        homeRepository = HomeRepository(mockApiCallImpl, mockk(), mockArticleDataSource)
        homeRepository?.requestArticles(0)
        verify(exactly = 1) {
            mockArticleDataSource.clearAboveAndInsert(any(), any())
        }
        assertEquals(HomeConst.BASE_INDEX_ARTICLE, slotAboveIndexList.captured)
        assertEquals(21, slotPOList.captured.size)
        assertEquals(HomeConst.BASE_INDEX_BANNER, slotPOList.captured[0]._index)
        assertEquals(HomeConst.ITEM_TYPE_BANNER, slotPOList.captured[0].itemType)
    }

    @Test
    fun requestArticles_whenPageNum1NormalResponseThenParseDoInsert() = runBlocking {
        val mockResponseBody = getMockResponseBody("$BASE_PATH/HomeArticleJson.json")
        mockkStatic(LifecycleCallExtensionKtPath)
        val mockApiCallImpl = configWanApiCallMock(
                wanServiceAction = {
                    getArticleList(1)
                },
                lifecycleCallActionMock = {
                    coEvery {
                        it.awaitWithTimeout(10000)
                    } returns HttpResult.Okay(
                            mockResponseBody,
                            mockk()
                    )
                }
        )

        val mockArticleDataSource = mockk<ArticleDAO>()
        every {
            mockArticleDataSource.insert(any())
        } just Runs

        homeRepository = HomeRepository(mockApiCallImpl, mockk(), mockArticleDataSource)
        homeRepository?.requestArticles(1)
        verify(exactly = 1) {
            mockArticleDataSource.insert(any())
        }
    }

    @Test
    fun requestArticles_whenTimeoutThenReturnEmptyList() = runBlockingTest {
        mockkStatic(LifecycleCallExtensionKtPath)
        val mockApiCallImpl = configWanApiCallMock(
                wanServiceAction = {
                    getArticleList()
                },
                lifecycleCallActionMock = {
                    coEvery {
                        it.awaitWithTimeout(10000)
                    } returns HttpResult.Error(
                            NetworkError(
                                    0, "Timeout: no response within 10000",
                                    TimeoutException("Timeout: no response within 10000")
                            )
                    )
                }
        )

        homeRepository = HomeRepository(mockApiCallImpl, mockk(), mockk())
        homeRepository?.requestArticles(0)
        val requestStatusValue = homeRepository?.requestStatus?.value
        assertTrue(requestStatusValue is RequestStatus.Complete)
        assertTrue((requestStatusValue as RequestStatus.Complete).err is NetworkError)
    }
}