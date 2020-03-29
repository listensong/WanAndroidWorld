package com.song.example.wanandroid.common.network.retrofit

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
 * @package com.song.example.wanandroid.common.network.retrofit
 * @fileName LifecycleCallImplTest
 * @date on 2/24/2020 7:42 PM
 * @desc: TODO
 * @email No
 */
@RunWith(MockitoJUnitRunner::class)
class LifecycleCallImplTest {

    @Mock
    var mockCall: Call<String>? = null

    @Mock
    var mockILifecycleCallback: ILifecycleCallback<String>? = null

    private var lifecycleCallImpl: LifecycleCallImpl<String>? = null

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `cancel mockCall的cancel应该被调用`() {
        lifecycleCallImpl = LifecycleCallImpl(mockCall!!, false)
        lifecycleCallImpl?.cancel()
        verify(mockCall)?.cancel()
    }

    @Test
    fun `enqueue mockCall的enqueue应该被调用`() {
        assertNotNull(mockCall)
        assertNotNull(mockILifecycleCallback)
        lifecycleCallImpl = LifecycleCallImpl(mockCall!!, false)
        lifecycleCallImpl?.enqueue(mockILifecycleCallback!!)
        verify(mockCall)?.enqueue(ArgumentMatchers.any())
    }

    @Mock
    var mockCloneCall: Call<String>? = null
    @Test
    fun `clone LifecycleCallImpl的clone应该是复制自己`() {
        lifecycleCallImpl = LifecycleCallImpl(mockCall!!, false)

        `when`(mockCall?.clone()).thenReturn(mockCloneCall)
        val cloneResult = lifecycleCallImpl?.clone()
        verify(mockCall)?.clone()
        assertEquals(lifecycleCallImpl?.enableCancel(), cloneResult?.enableCancel())
    }

    @Test
    fun `isCanceled mockCall的isCanceled应该被调用`() {
        lifecycleCallImpl = LifecycleCallImpl(mockCall!!, false)
        lifecycleCallImpl?.isCanceled()
        verify(mockCall)?.isCanceled()
    }

    @Test
    fun `enableCancel mockCall的enableCancel应该被调用`() {
        lifecycleCallImpl = LifecycleCallImpl(mockCall!!, false)
        val enableCancel = lifecycleCallImpl?.enableCancel()
        assertEquals(false, enableCancel)
    }
}