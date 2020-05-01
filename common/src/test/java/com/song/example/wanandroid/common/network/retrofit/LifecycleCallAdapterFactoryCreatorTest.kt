package com.song.example.wanandroid.common.network.retrofit

import com.song.example.wanandroid.BaseApplication
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Listensong
 * @package com.song.example.wanandroid.common.network.retrofit
 * @fileName LifecycleCallAdapterFactoryCreatorTest
 * @date on 3/22/2020 7:16 PM
 * @desc: TODO
 * @email No
 */
class LifecycleCallAdapterFactoryCreatorTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `create 成功创建LifecycleCallAdapterFactory`() {
        mockkObject(BaseApplication)
        val mockApplicationCtx = mockk<BaseApplication>()
        every { BaseApplication.instance } returns mockApplicationCtx
        val adapter = LifecycleCallAdapterFactoryCreator.create()
        assertTrue(adapter is LifecycleCallAdapterFactory)
        //assertTrue(adapter.getEnableCancel())
    }
}