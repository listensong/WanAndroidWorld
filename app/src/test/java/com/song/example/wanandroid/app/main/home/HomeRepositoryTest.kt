package com.song.example.wanandroid.app.main.home

import com.song.example.wanandroid.BaseApplication
import com.song.example.wanandroid.app.network.BaseWanApiCallMock
import com.song.example.wanandroid.app.network.WanApiCallImpl
import com.song.example.wanandroid.app.network.WanService
import com.song.example.wanandroid.basetest.MockAssets
import com.song.example.wanandroid.common.network.retrofit.*
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import java.util.concurrent.TimeoutException

/**
 * @author Listensong
 * @package com.song.example.wanandroid.app.main.home
 * @fileName HomeRepositoryTest
 * @date on 3/29/2020 4:51 PM
 * @desc: TODO
 * @email No
 */
class HomeRepositoryTest: BaseWanApiCallMock() {

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
    }

    @Test
    fun getBannerList_whenNormalResponseThenParseSuccessfully() = runBlockingTest {
        val mockResponseBody = getMockResponseBody("$BASE_PATH/BannerJson.json")
        mockkStatic(LifecycleCallExtensionKtPath)
        val mockApiCallImpl = configWanApiCallMock(
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

        homeRepository = HomeRepository(mockApiCallImpl)
        val bannerData = homeRepository?.requestBanners()
        assertTrue(bannerData?.size == 4)
        assertEquals(
                "https://www.wanandroid.com/blogimgs/c4e5f86d-857a-41b9-a21a-316145cf3103.png",
                bannerData?.get(0)?.imageUrl
        )
        assertEquals(
                "https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",
                bannerData?.get(1)?.imageUrl
        )
        assertEquals(
                "https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
                bannerData?.get(2)?.imageUrl
        )
        assertEquals(
                "https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png",
                bannerData?.get(3)?.imageUrl
        )
    }

    @Test
    fun getBannerList_whenTimeoutThenReturnEmptyList() = runBlockingTest {
        mockkStatic(LifecycleCallExtensionKtPath)
        val mockApiCallImpl = configWanApiCallMock(
                wanServiceAction = {
                    getBannerList()
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

        homeRepository = HomeRepository(mockApiCallImpl)
        val bannerData = homeRepository?.requestBanners()
        assertTrue(bannerData?.size == 0)
    }
}