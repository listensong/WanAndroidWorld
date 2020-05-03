package com.song.example.study.common.network.retrofit

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Listensong
 * @package com.song.example.study.common.network.retrofit
 * @fileName ApiCallImplTest
 * @date on 2/24/2020 6:27 PM
 * @desc: TODO
 * @email No
 */
@RunWith(MockitoJUnitRunner::class)
class ApiCallImplTest {

    private var apiCallImpl: ApiCallImpl = ApiCallImpl.getInstance()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `getbaseCallUrl 确认默认url`() {
        assertEquals("https://www.baidu.com/", apiCallImpl.baseCallUrl)
    }

    private fun mockLifecycleCallAdapterFactoryCreator() {
        assertNotNull(apiCallImpl)

        mockkObject(LifecycleCallAdapterFactoryCreator)
        every {
            LifecycleCallAdapterFactoryCreator.create()
        } returns mockk()
    }

    @Test
    fun `create callApi返回retrofit，创建TestService`() {
        mockLifecycleCallAdapterFactoryCreator()

        val retrofitImpl = apiCallImpl.callApi(InterceptorModifyRequest())
        retrofitImpl.also { retrofit ->
            assertTrue(retrofit.baseUrl().toString() == apiCallImpl.baseCallUrl)
            assertTrue(retrofit.converterFactories().any { it is GsonConverterFactory })
            assertTrue(retrofit.callAdapterFactories().any { it is LifecycleCallAdapterFactory })

            val apiClz = retrofit.create(TestService::class.java)
            assertTrue(apiClz is TestService)
        }
    }

    @Test
    fun `callApi 传入WanService，返回WanService实例`() {
        mockLifecycleCallAdapterFactoryCreator()

        val wanService = apiCallImpl.callApi(TestService::class.java)
        assertTrue(wanService is TestService)
    }

    @Test
    fun `callApi 传入WanService、InterceptorModifyRequest，返回WanService实例`() {
        mockLifecycleCallAdapterFactoryCreator()

        val wanService = apiCallImpl.callApi(
                TestService::class.java,
                InterceptorModifyRequest())
        assertTrue(wanService is TestService)
    }

    @Test
    fun `getInstance ApiCallImpl应该是单例`() {
        assertNotNull(apiCallImpl)
        val apiCallImpl2 = ApiCallImpl.getInstance()
        assertEquals(apiCallImpl, apiCallImpl2)
    }
}