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
        LifecycleCallImpl::class,
        ErrorHandleCallAdapter::class)
class ErrorHandleCallAdapterTest {

    @Mock
    var mockCall: Call<String>? = null

    @Mock
    var mockLifecycleCallImpl: LifecycleCallImpl<String>? = null

    private var spyLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(Mockito.spy(LifecycleOwner::class.java))

    private var errorHandleCallAdapter: ErrorHandleCallAdapter<String>? = null

    @Before
    fun setUp() {
        spyLifecycleRegistry = Mockito.spy(LifecycleRegistry(Mockito.mock(LifecycleOwner::class.java)))
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `adapt lifecycleRegistry的状态非DESTROYED时，LifecycleRegistry的addObserver会被调用`() {
        errorHandleCallAdapter = ErrorHandleCallAdapter(String::class.java, spyLifecycleRegistry, true)
        val adapt = errorHandleCallAdapter?.adapt(mockCall!!)
        assert(adapt is LifecycleCallImpl)
        verify(spyLifecycleRegistry).addObserver(adapt!!)
    }

    @Test
    fun `adapt lifecycleRegistry的状态为DESTROYED时，adapt的onDestroy会被调用`() {
        PowerMockito.whenNew(LifecycleCallImpl::class.java)
                .withAnyArguments()
                .thenReturn(mockLifecycleCallImpl)
        Whitebox.setInternalState(spyLifecycleRegistry, "mState", Lifecycle.State.DESTROYED)
        assertEquals(spyLifecycleRegistry.currentState, Lifecycle.State.DESTROYED)

        errorHandleCallAdapter = ErrorHandleCallAdapter(String::class.java, spyLifecycleRegistry, true)
        val adapt = errorHandleCallAdapter?.adapt(mockCall!!)

        assert(adapt is LifecycleCallImpl)
        assertEquals(mockLifecycleCallImpl, adapt)
        verify(adapt)?.onDestroy()
    }

    @Test
    fun `responseType ErrorHandleCallAdapter传入类型为String，responseType的值也应该为String类型`() {
        errorHandleCallAdapter = ErrorHandleCallAdapter(String::class.java, spyLifecycleRegistry, true)
        val type = errorHandleCallAdapter?.responseType()
        assertEquals(String::class.java, type)
    }
}