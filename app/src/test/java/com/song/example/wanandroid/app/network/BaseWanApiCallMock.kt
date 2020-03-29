package com.song.example.wanandroid.app.network

import com.song.example.wanandroid.BaseApplication
import com.song.example.wanandroid.basetest.MockAssets
import com.song.example.wanandroid.common.network.retrofit.HttpResult
import com.song.example.wanandroid.common.network.retrofit.ILifecycleCall
import com.song.example.wanandroid.common.network.retrofit.InterceptorModifyRequest
import com.song.example.wanandroid.common.network.retrofit.awaitWithTimeout
import io.mockk.*
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * @package com.song.example.wanandroid.app.network
 * @fileName BaseWanApiCallMock
 * @date on 3/29/2020 4:59 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
abstract class BaseWanApiCallMock {

    protected val BASE_PATH = "../app/src/test/java/com/song/example/wanandroid/app/mockdata"

    suspend fun configWanApiCallMock(
            wanServiceAction: WanService.() -> ILifecycleCall<ResponseBody>,
            lifecycleCallActionMock: (ILifecycleCall<ResponseBody>) -> Unit
    ): WanApiCallImpl {
        mockkObject(BaseApplication)
        mockkClass(InterceptorModifyRequest::class)
        mockkConstructor(InterceptorModifyRequest::class)
        every { BaseApplication.instance } returns mockk()

        val mockApiCallImpl = mockk<WanApiCallImpl>()
        val mockWanService = mockk<WanService>()
        val mockILifecycleCall = mockk<ILifecycleCall<ResponseBody>>()
        every {
            mockApiCallImpl.callWanApi(WanService::class.java)
        } returns mockWanService

        every {
            wanServiceAction(mockWanService)
        } returns mockILifecycleCall

        lifecycleCallActionMock(mockILifecycleCall)
        return mockApiCallImpl
    }

    protected fun getMockResponseBody(filePath: String): ResponseBody {
        val mockJson: String = MockAssets.readFile(filePath)
        return mockJson.toResponseBody()
    }
}