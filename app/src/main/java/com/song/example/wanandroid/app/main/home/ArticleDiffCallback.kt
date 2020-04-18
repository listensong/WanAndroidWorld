package com.song.example.wanandroid.app.main.home

import androidx.recyclerview.widget.DiffUtil
import com.song.example.wanandroid.app.main.home.article.ArticleVO
import java.lang.IndexOutOfBoundsException

/**
 * @package com.song.example.wanandroid.app.main.home
 * @fileName ArticleDiffCallback
 * @date on 4/15/2020 9:43 PM
 * @author Listensong
 * @desc
 * @email No
 */
class ArticleDiffCallback(
        private var oldList: List<ArticleVO>?,
        private var newList: List<ArticleVO>?
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPos: Int, newItemPos: Int): Boolean {
        return try {
            oldList?.get(oldItemPos)?.id == newList?.get(newItemPos)?.id
        } catch (e: IndexOutOfBoundsException) {
            false
        }
    }

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areContentsTheSame(oldItemPos: Int,
                                    newItemPos: Int): Boolean {
        return try {
            (oldList?.get(oldItemPos)?.id == newList?.get(newItemPos)?.id)
                    && (oldList?.get(oldItemPos)?.title == newList?.get(newItemPos)?.title)
                    && (oldList?.get(oldItemPos)?.link == newList?.get(newItemPos)?.link)
        } catch (e: IndexOutOfBoundsException) {
            false
        }
    }

}