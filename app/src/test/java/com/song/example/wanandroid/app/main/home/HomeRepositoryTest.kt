package com.song.example.wanandroid.app.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.song.example.wanandroid.app.main.home.article.ArticleDAO
import com.song.example.wanandroid.app.main.home.banner.BannerDAO
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

        val mockBannerDataSource = mockk<BannerDAO>()
        every {
            mockBannerDataSource.insertAll(any())
        } just Runs

        homeRepository = HomeRepository(mockApiService, mockBannerDataSource, mockk())
        val bannerVOList = homeRepository?.requestBanners()
        verify(exactly = 1) {
            mockBannerDataSource.insertAll(any())
        }
        val requestStatusValue = homeRepository?.requestStatus?.value
        assertTrue(requestStatusValue is RequestStatus.Complete)
        assertNull((requestStatusValue as RequestStatus.Complete).err)
        assertTrue(bannerVOList?.size == 4)
        assertEquals(
                "https://www.wanandroid.com/blogimgs/c4e5f86d-857a-41b9-a21a-316145cf3103.png",
                bannerVOList?.get(0)?.imagePath
        )
        assertEquals(
                "https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",
                bannerVOList?.get(1)?.imagePath
        )
        assertEquals(
                "https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
                bannerVOList?.get(2)?.imagePath
        )
        assertEquals(
                "https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png",
                bannerVOList?.get(3)?.imagePath
        )
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
        val bannerVOList = homeRepository?.requestBanners()
        assertTrue(bannerVOList?.size == 0)
        val requestStatusValue = homeRepository?.requestStatus?.value
        assertTrue(requestStatusValue is RequestStatus.Complete)
        assertTrue((requestStatusValue as RequestStatus.Complete).err is NetworkError)
        assertEquals(0, requestStatusValue.err?.errorCode)
        assertEquals("Timeout: no response within $timeoutMillis", requestStatusValue.err?.errorMessage)
    }


    @Test
    fun initArticlesPageList_whenInitZeroArticleCalledThenRequestArticlesCalled() {
//TODO
    }


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
    fun getArticlesList_whenNormalResponseThenParseSuccessfully() = runBlocking {
        val mockResponseBody = getMockResponseBody("$BASE_PATH/HomeArticleJson.json")
        mockkStatic(LifecycleCallExtensionKtPath)
        val mockApiCallImpl = configWanApiCallMock(
                wanServiceAction = {
                    getArticleList()
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
            mockArticleDataSource.insertAll(any())
        } just Runs

        homeRepository = HomeRepository(mockApiCallImpl, mockk(), mockArticleDataSource)
        val articleVOList = homeRepository?.requestArticles()
        verify(exactly = 1) {
            mockArticleDataSource.insertAll(any())
        }
        assertEquals(20, articleVOList?.size)
        assertEquals("https://www.jianshu.com/p/756863740988", articleVOList?.get(0)?.link)
    }

    @Test
    fun getArticleList_whenTimeoutThenReturnEmptyList() = runBlockingTest {
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
        val articleVOList = homeRepository?.requestArticles()
        assertTrue(articleVOList?.size == 0)
    }
}