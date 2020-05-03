package com.song.example.study.common.widget

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * @package com.song.example.study.common.widget
 * @fileName LoadMoreScrollListener
 * @date on 4/15/2020 9:37 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
open class LoadMoreScrollListener(
        private val callback: (lastPos: Int) -> Unit
) : RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerView.layoutManager?.let {
                val lastItemPos = findLastItemPos(it)
                if (it.childCount > 0 && lastItemPos >= (it.itemCount - 1)) {
                    callback.invoke(lastItemPos)
                }
            }
        }
    }

    private fun findLastItemPos(manager: RecyclerView.LayoutManager): Int {
        return when (manager) {
            is LinearLayoutManager -> {
                manager.findLastVisibleItemPosition()
            }
            is GridLayoutManager -> {
                manager.findLastVisibleItemPosition()
            }
            is StaggeredGridLayoutManager -> {
                val first = IntArray(manager.spanCount)
                manager.findLastVisibleItemPositions(first)
                first.max() ?: 0
            }
            else -> 0
        }
    }
}