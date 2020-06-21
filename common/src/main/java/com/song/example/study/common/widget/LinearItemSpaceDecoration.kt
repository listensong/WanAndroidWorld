package com.song.example.study.common.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @package com.song.example.study.common.widget
 * @fileName LinearItemSpaceDecoration
 * @date on 6/7/2020 5:24 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
class LinearItemSpaceDecoration(
        private val space: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
    }
}