package com.song.example.study.common.network.retrofit

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call

/**
 * @author Listensong
 * @package com.song.example.study.common.network.retrofit
 * @fileName LifecycleCallImplTest
 * @date on 2/24/2020 7:42 PM
 * @desc: TODO
 * @email No
 */
@RunWith(MockitoJUnitRunner::class)
class CoroutineLifecycleCallImplTest {

    @Mock
    var mockCall: Call<String>? = null

    @Mock
    var mockCoroutineLifecycleCallback: CoroutineLifecycleCallback<String>? = null

    private var coroutineLifecycleCallImpl: CoroutineLifecycleCallImpl<String>? = null

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `cancel mockCall的cancel应该被调用`() {
        coroutineLifecycleCallImpl = CoroutineLifecycleCallImpl(mockCall!!, false)
        coroutineLifecycleCallImpl?.cancel()
        verify(mockCall)?.cancel()
    }

    @Test
    fun `enqueue mockCall的enqueue应该被调用`() {
        assertNotNull(mockCall)
        assertNotNull(mockCoroutineLifecycleCallback)
        coroutineLifecycleCallImpl = CoroutineLifecycleCallImpl(mockCall!!, false)
        coroutineLifecycleCallImpl?.enqueue(mockCoroutineLifecycleCallback!!)
        verify(mockCall)?.enqueue(ArgumentMatchers.any())
    }

    @Mock
    var mockCloneCall: Call<String>? = null
    @Test
    fun `clone LifecycleCallImpl的clone应该是复制自己`() {
        coroutineLifecycleCallImpl = CoroutineLifecycleCallImpl(mockCall!!, false)

        `when`(mockCall?.clone()).thenReturn(mockCloneCall)
        val cloneResult = coroutineLifecycleCallImpl?.clone()
        verify(mockCall)?.clone()
        assertEquals(coroutineLifecycleCallImpl?.enableCancel(), cloneResult?.enableCancel())
    }

    @Test
    fun `isCanceled mockCall的isCanceled应该被调用`() {
        coroutineLifecycleCallImpl = CoroutineLifecycleCallImpl(mockCall!!, false)
        coroutineLifecycleCallImpl?.isCanceled()
        verify(mockCall)?.isCanceled()
    }

    @Test
    fun `enableCancel mockCall的enableCancel应该被调用`() {
        coroutineLifecycleCallImpl = CoroutineLifecycleCallImpl(mockCall!!, false)
        val enableCancel = coroutineLifecycleCallImpl?.enableCancel()
        assertEquals(false, enableCancel)
    }
}