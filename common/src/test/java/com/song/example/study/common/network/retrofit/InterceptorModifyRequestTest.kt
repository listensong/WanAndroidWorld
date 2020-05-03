package com.song.example.study.common.network.retrofit

import com.nhaarman.mockitokotlin2.any
import okhttp3.*
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.util.concurrent.TimeUnit

/**
 * @author Listensong
 * @package com.song.example.study.common.network.retrofit
 * @fileName InterceptorModifyRequestTest
 * @date on 2/25/2020 11:44 AM
 * @desc: TODO
 * @email No
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(
        InterceptorModifyRequest::class,
        Interceptor.Chain::class)
class InterceptorModifyRequestTest {

    private var interceptorModifyRequest: InterceptorModifyRequest? = null

    @Before
    fun setUp() {
        interceptorModifyRequest = InterceptorModifyRequest()
    }

    @After
    fun tearDown() {
        interceptorModifyRequest = null
    }

    @Test
    fun `intercept 返回的Response应该包含插入的信息`() {
        val interceptorChain = object : Interceptor.Chain {
            override fun call(): Call {
                return Mockito.spy(Call::class.java)
            }

            override fun connectTimeoutMillis(): Int {
                return 1000
            }

            override fun connection(): Connection? {
                return Mockito.spy(Connection::class.java)
            }

            override fun proceed(request: Request): Response {
                return Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .message("hello")
                        .code(200).build()
            }

            override fun readTimeoutMillis(): Int {
                return 1000
            }

            override fun request(): Request {
                return Request.Builder().url("http://www.baidu.com").build()
            }

            override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
                return this
            }

            override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
                return this
            }

            override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
                return this
            }

            override fun writeTimeoutMillis(): Int {
                return 1000
            }
        }
        val spyInterceptorChain = PowerMockito.spy(interceptorChain)
        val response = interceptorModifyRequest?.intercept(spyInterceptorChain)
        response?.let {
            assertEquals(Protocol.HTTP_1_1, it.protocol)
            assertEquals(200, it.code)
            assertEquals("hello", it.message)
            assertEquals("www.baidu.com", it.request.url.host)

            it.request.headers.let { headers ->
                assertEquals(ApiCallConstant.CONTENT_TYPE_APPLICATION_JSON, headers[ApiCallConstant.CONTENT_TYPE])
                assertEquals(ApiCallConstant.AC_LAN_ZH_CN, headers[ApiCallConstant.ACCEPT_LANGUAGE])
                assertEquals(ApiCallConstant.CACHE_CONTROL_NO_CACHE, headers[ApiCallConstant.CACHE_CONTROL])
            }
        }
        verify(spyInterceptorChain).request()
        verify(spyInterceptorChain).proceed(any())
    }
}