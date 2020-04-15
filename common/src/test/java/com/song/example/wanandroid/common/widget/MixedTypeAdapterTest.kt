package com.song.example.wanandroid.common.widget

import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Listensong
 * @package com.song.example.wanandroid.common.widget
 * @fileName MixedTypeAdapterTest
 * @date on 4/15/2020 7:20 PM
 * @desc: TODO
 * @email No
 */
class MixedTypeAdapterTest {

    private lateinit var adapter: MixedTypeAdapter<String>

    private var variableId: Int = 101
    private var testViewType: Int = 3
    private var testSpanSize: Int = 6

    @Before
    fun setUp() {
        adapter = MixedTypeAdapter(
                mutableListOf(),
                variableId,
                viewType = { testViewType },
                spanSize = { testSpanSize },
                viewDataBinding = { _, _ ->
                    mockk()
                }
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun onBindViewHolder() {
        val mockViewHolder = mockk<MixedTypeAdapter<String>.ViewHolder>()
        every {
            mockViewHolder.handleBindView(variableId, any())
        } just Runs
        adapter.setDataList(mutableListOf("1-1"))
        adapter.onBindViewHolder(mockViewHolder, 0)
        verify(exactly = 1) {
            mockViewHolder.handleBindView(variableId, any())
        }
    }

    @Test
    fun getDataList_returnEmptyList() {
        val dataList = adapter.getDataList()
        assertNotNull(dataList)
        assertEquals(0, dataList?.size)
    }

    @Test
    fun setDataList_givenValidListThenReturnList() {
        val beforeSize = adapter.getDataList()?.size!!
        adapter.setDataList(mutableListOf("1", "2"))
        val afterSize = adapter.getDataList()?.size!!
        assertEquals(2, afterSize - beforeSize)
    }

    @Test
    fun diffUpdate() {
        //TODO UT
    }

    @Test
    fun append_given3SizeListThenCheckDataList() {
        val beforeSize = adapter.getDataList()?.size!!
        adapter.setDataList(mutableListOf("1", "2", "___23424___"))
        val afterSize = adapter.getDataList()?.size!!
        assertEquals(3, afterSize - beforeSize)
        assertEquals("___23424___", adapter.getDataList()?.get(afterSize - 1))
    }


    @Test
    fun getSpanSize_whenPositionIsOutOfDataListBoundsThenReturn0() {
        val spanSize = adapter.getSpanSize(10)
        assertEquals(0, spanSize)
    }

    @Test
    fun getSpanSize_whenPosition1WithDataListIsSize2ThenReturnTestSpanSize() {
        adapter.setDataList(mutableListOf("1", "-2-"))
        val spanSize = adapter.getSpanSize(1)
        assertEquals(testSpanSize, spanSize)
    }

    @Test
    fun getItem_whenPositionIsOutOfDataListBoundsThenReturnNull() {
        val item = adapter.getItem(1)
        assertNull(item)
    }

    @Test
    fun getItem_whenPosition1WithDataListIsSize2ThenReturnString() {
        adapter.setDataList(mutableListOf("1", "-2-"))
        val item = adapter.getItem(1)
        assertNotNull(item)
        assertEquals("-2-", item)
    }

    @Test
    fun getItemCount_returnAdapterItemCount() {
        adapter.setDataList(mutableListOf())
        assertEquals(0, adapter.itemCount)
        adapter.setDataList(mutableListOf("1", "2"))
        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun getItemViewType_whenIndexOutOfBoundsThenReturn0() {
        assertEquals(0,  adapter.getItemViewType(-1))
        assertEquals(0,  adapter.getItemViewType(1111))
    }

    @Test
    fun getItemViewType_whenIndexIsValidThenReturnTestViewType() {
        adapter.setDataList(mutableListOf("1", "2"))
        assertEquals(testViewType,  adapter.getItemViewType(1))
    }
}