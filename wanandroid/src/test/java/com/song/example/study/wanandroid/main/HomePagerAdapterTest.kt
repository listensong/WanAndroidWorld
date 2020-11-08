package com.song.example.study.wanandroid.main

import androidx.fragment.app.Fragment
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Listensong
 * @package com.song.example.study.app.main
 * @fileName HomePagerAdapterTest
 * @date on 4/19/2020 6:34 PM
 * @desc TODO
 * @email No
 */
class HomePagerAdapterTest {

    private lateinit var adapter: HomePagerAdapter

    @Before
    fun setUp() {
        adapter = HomePagerAdapter(mock())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testGetItem_returnFragmentListItem() {
        val fragment = mock<Fragment>()
        adapter.addFragment(fragment, "")
        assertEquals(fragment, adapter.getItem(0))
    }

    @Test
    fun testGetCount_returnFragmentListSize() {
        assertEquals(0, adapter.count)

        adapter.addFragment(mock(), "title")

        assertEquals(1, adapter.count)
    }

    @Test
    fun testRelease() {
        adapter.addFragment(mock(), "title1")
        adapter.addFragment(mock(), "title2")
        adapter.addFragment(mock(), "title3")
        assertEquals(3, adapter.count)

        adapter.release()

        assertEquals(0, adapter.count)
    }

    @Test
    fun testAddFragment() {
        assertEquals(0, adapter.count)

        adapter.addFragment(mock(), "title")

        assertEquals(1, adapter.count)
    }

    @Test
    fun testGetPageTitle_givenValidPosThenReturnCorrespondingString() {
        adapter.addFragment(mock(), "title")
        assertEquals("title", adapter.getPageTitle(0))
    }

    @Test
    fun testGetPageTitle_whenOutOfBoundsThenReturnEmptyString() {
        assertEquals("", adapter.getPageTitle(1))
    }
}