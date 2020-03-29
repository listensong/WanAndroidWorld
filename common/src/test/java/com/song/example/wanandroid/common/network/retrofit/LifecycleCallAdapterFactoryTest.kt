package com.song.example.wanandroid.common.network.retrofit

import android.content.Context
import okhttp3.Request
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Listensong
 * @package com.song.example.wanandroid.common.network.retrofit
 * @fileName LifecycleCallAdapterFactoryTest
 * @date on 2/24/2020 7:04 PM
 * @desc: TODO
 * @email No
 */
@RunWith(MockitoJUnitRunner::class)
class LifecycleCallAdapterFactoryTest {

    @Rule
    @JvmField
    var exception: ExpectedException = ExpectedException.none()

    private var lifecycleCallAdapterFactory: LifecycleCallAdapterFactory? = null

    @Before
    fun setUp() {
        lifecycleCallAdapterFactory = LifecycleCallAdapterFactory(Mockito.mock(Context::class.java))
    }

    @After
    fun tearDown() {
        lifecycleCallAdapterFactory = null
    }

    @Test
    fun `getEnableCancel 默认值为true`() {
        val factory = LifecycleCallAdapterFactory(Mockito.mock(Context::class.java))
        assertTrue(factory.getEnableCancel())
    }

    @Test
    fun `getEnableCancel 传入false时，应该返回false`() {
        val factory = LifecycleCallAdapterFactory(Mockito.mock(Context::class.java), false)
        assertFalse(factory.getEnableCancel())
    }

    @Test
    fun `get 未实现ILifecycleCall，应该返回null或者IllegalStateException`() {
        assertNotNull(lifecycleCallAdapterFactory)

        val getResult = lifecycleCallAdapterFactory?.get(String::class.java, null, null)
        assertTrue(getResult == null)

        exception.expect(IllegalStateException::class.java)
        lifecycleCallAdapterFactory?.get(ILifecycleCall::class.java, null, null)
    }
}