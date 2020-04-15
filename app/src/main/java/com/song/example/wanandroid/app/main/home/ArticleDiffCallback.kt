package com.song.example.wanandroid.app.main.home

import androidx.recyclerview.widget.DiffUtil
import com.song.example.wanandroid.app.main.home.article.ArticleVO

/**
 * @package com.song.example.wanandroid.app.main.home
 * @fileName ArticleDiffCallback
 * @date on 4/15/2020 9:43 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
class ArticleDiffCallback(
        private var oldList: List<ArticleVO>?,
        private var newList: List<ArticleVO>?
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition)?.id == newList?.get(newItemPosition)?.id
    }

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList?.get(oldItemPosition)?.id == newList?.get(newItemPosition)?.id)
                && (oldList?.get(oldItemPosition)?.title == newList?.get(newItemPosition)?.title)
                && (oldList?.get(oldItemPosition)?.link == newList?.get(newItemPosition)?.link)
    }

}