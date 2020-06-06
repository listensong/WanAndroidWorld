package com.song.example.study.common.network.retrofit

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.reflect.Whitebox
import retrofit2.Call

/**
 * @author Listensong
 * @package com.song.example.study.common.network.retrofit
 * @fileName ErrorHandleCallAdapterTest
 * @date on 2/24/2020 8:12 PM
 * @desc: TODO
 * @email No
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(
        CoroutineLifecycleCallImpl::class,
        CoroutineCallEventHandleCallAdapter::class)
class CoroutineCallEventHandleCallAdapterTest {

    private var spyLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(Mockito.spy(LifecycleOwner::class.java))

    private var coroutineCallEventHandleCallAdapter: CoroutineCallEventHandleCallAdapter<String>? = null

    @Before
    fun setUp() {
        spyLifecycleRegistry = Mockito.spy(LifecycleRegistry(Mockito.mock(LifecycleOwner::class.java)))
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `responseType ErrorHandleCallAdapter传入类型为String，responseType的值也应该为String类型`() {
        coroutineCallEventHandleCallAdapter = CoroutineCallEventHandleCallAdapter(String::class.java, true)
        val type = coroutineCallEventHandleCallAdapter?.responseType()
        assertEquals(String::class.java, type)
    }
}