package com.song.example.wanandroid.app.network

import com.song.example.wanandroid.common.network.retrofit.InterceptorModifyRequest
import com.song.example.wanandroid.common.network.retrofit.LifecycleCallAdapterFactory
import com.song.example.wanandroid.common.network.retrofit.LifecycleCallAdapterFactoryCreator
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import junit.framework.Assert
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Listensong
 * @package com.song.example.wanandroid.app.network
 * @fileName WanApiCallImplTest
 * @date on 3/24/2020 8:59 PM
 * @desc: TODO
 * @email No
 */
@RunWith(MockitoJUnitRunner::class)
class WanApiCallImplTest {

    private var wanApiCallImpl: WanApiCallImpl = WanApiCallImpl.getInstance()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `getWanBaseUrl 确认默认url`() {
        assertEquals("https://www.wanandroid.com/", wanApiCallImpl.wanBaseUrl)
    }

    private fun mockLifecycleCallAdapterFactoryCreator() {
        assertNotNull(wanApiCallImpl)

        mockkObject(LifecycleCallAdapterFactoryCreator)
        every {
            LifecycleCallAdapterFactoryCreator.create()
        } returns mockk()
    }

    @Test
    fun `callWanApi 传入interceptor，返回retrofit，创建WanService`() {
        mockLifecycleCallAdapterFactoryCreator()

        val retrofitImpl = wanApiCallImpl.callWanApi(InterceptorModifyRequest())
        retrofitImpl.also { retrofit ->
            assertTrue(retrofit.baseUrl().toString() == wanApiCallImpl.wanBaseUrl)
            assertTrue(retrofit.converterFactories()
                    .any { it is GsonConverterFactory })
            assertTrue(retrofit.callAdapterFactories()
                    .any { it is LifecycleCallAdapterFactory })

            val apiClz = retrofit.create(WanService::class.java)
            assertTrue(apiClz is WanService)
        }
    }

    @Test
    fun `callWanApi 传入WanService，返回WanService实例`() {
        mockLifecycleCallAdapterFactoryCreator()

        val wanService = wanApiCallImpl.callWanApi(WanService::class.java)
        assertTrue(wanService is WanService)
    }

    @Test
    fun `callWanApi 传入WanService、InterceptorModifyRequest，返回WanService实例`() {
        mockLifecycleCallAdapterFactoryCreator()

        val wanService = wanApiCallImpl.callWanApi(
                WanService::class.java,
                InterceptorModifyRequest())
        assertTrue(wanService is WanService)
    }

    @Test
    fun `getInstance WanApiCallImpl应该是单例`() {
        assertNotNull(wanApiCallImpl)
        val wanApiCallImpl2 = WanApiCallImpl.getInstance()
        assertEquals(wanApiCallImpl, wanApiCallImpl2)
    }
}