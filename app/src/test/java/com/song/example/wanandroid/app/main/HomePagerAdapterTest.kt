package com.song.example.wanandroid.app.main

import androidx.fragment.app.Fragment
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Listensong
 * @package com.song.example.wanandroid.app.main
 * @fileName HomePagerAdapterTest
 * @date on 4/19/2020 6:34 PM
 * @desc TODO
 * @email No
 */
class HomePagerAdapterTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testGetItem_returnFragmentListItem() {
        val fragment = mock<Fragment>()
        val adapter = HomePagerAdapter(mock())
        adapter.addFragment(fragment, "")
        assertEquals(fragment, adapter.getItem(0))
    }

    @Test
    fun testGetCount_returnFragmentListSize() {
        val adapter = HomePagerAdapter(mock())
        assertEquals(0, adapter.count)

        adapter.addFragment(mock(), "title")
        assertEquals(1, adapter.count)
    }

    @Test
    fun testAddFragment() {
        val adapter = HomePagerAdapter(mock())
        assertEquals(0, adapter.count)

        adapter.addFragment(mock(), "title")
        assertEquals(1, adapter.count)
    }

    @Test
    fun testGetPageTitle_givenValidPosThenReturnCorrespondingString() {
        val adapter = HomePagerAdapter(mock())
        adapter.addFragment(mock(), "title")
        assertEquals("title", adapter.getPageTitle(0))
    }

    @Test
    fun testGetPageTitle_whenOutOfBoundsThenReturnEmptyString() {
        val adapter = HomePagerAdapter(mock())
        assertEquals("", adapter.getPageTitle(1))
    }
}