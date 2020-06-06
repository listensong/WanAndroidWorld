package com.song.example.study.common.network.retrofit

import android.content.Context
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author Listensong
 * @package com.song.example.study.common.network.retrofit
 * @fileName LifecycleCallAdapterFactoryTest
 * @date on 2/24/2020 7:04 PM
 * @desc: TODO
 * @email No
 */
@RunWith(MockitoJUnitRunner::class)
class CoroutineLifecycleCallAdapterFactoryTest {

    @Rule
    @JvmField
    var exception: ExpectedException = ExpectedException.none()

    private var coroutineLifecycleCallAdapterFactory: CoroutineLifecycleCallAdapterFactory? = null

    @Before
    fun setUp() {
        coroutineLifecycleCallAdapterFactory = CoroutineLifecycleCallAdapterFactory()
    }

    @After
    fun tearDown() {
        coroutineLifecycleCallAdapterFactory = null
    }

    @Test
    fun `getEnableCancel 默认值为true`() {
        val factory = CoroutineLifecycleCallAdapterFactory()
        assertTrue(factory.getEnableCancel())
    }

    @Test
    fun `getEnableCancel 传入false时，应该返回false`() {
        val factory = CoroutineLifecycleCallAdapterFactory(false)
        assertFalse(factory.getEnableCancel())
    }

    @Test
    fun `get 未实现ILifecycleCall，应该返回null或者IllegalStateException`() {
        assertNotNull(coroutineLifecycleCallAdapterFactory)

        val getResult = coroutineLifecycleCallAdapterFactory?.get(String::class.java, null, null)
        assertTrue(getResult == null)

        exception.expect(IllegalStateException::class.java)
        coroutineLifecycleCallAdapterFactory?.get(CoroutineLifecycleCall::class.java, null, null)
    }
}