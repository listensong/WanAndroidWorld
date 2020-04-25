package com.song.example.wanandroid.common.network.retrofit

import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Listensong
 * @package com.song.example.wanandroid.common.network.retrofit
 * @fileName HttpLoggingInterceptorConfigurationTest
 * @date on 3/22/2020 7:16 PM
 * @desc: TODO
 * @email No
 */
class HttpLoggingInterceptorCreatorTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `createLoggingInterceptor 当DebugConfig为true的时候，返回BODY`() {
        //mockkObject(HttpLoggingInterceptorConfiguration)
        val interceptor = HttpLoggingInterceptorCreator.create(true)
        assertEquals(HttpLoggingInterceptor.Level.BODY, interceptor.level)
    }

    @Test
    fun `createLoggingInterceptor 当DebugConfig为false的时候，返回BASIC`() {
        //mockkObject(HttpLoggingInterceptorConfiguration)
        val interceptor = HttpLoggingInterceptorCreator.create(false)
        assertEquals(HttpLoggingInterceptor.Level.BASIC, interceptor.level)
    }
}