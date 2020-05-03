package com.song.example.study.wanandroid.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

/**
 * @author Listensong
 * @package com.song.example.study.app.main
 * @fileName WelcomeViewModelTest
 * @date on 5/1/2020 12:43 PM
 * @desc TODO
 * @email No
 */
class WelcomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val viewModel = WelcomeViewModel()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun isDrawerOpen() {
        val isDrawerOpen = WelcomeViewModel().isDrawerOpen()
        assertEquals(false, isDrawerOpen.value)
    }

    @Test
    fun setDrawerOpenState() {
        viewModel.setDrawerOpenState(false)
        assertEquals(false, viewModel.isDrawerOpen().value)

        viewModel.setDrawerOpenState(true)
        assertEquals(true, viewModel.isDrawerOpen().value)
    }
}