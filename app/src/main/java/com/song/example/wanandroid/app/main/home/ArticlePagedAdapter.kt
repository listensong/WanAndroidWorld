package com.song.example.wanandroid.app.main.home

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.song.example.wanandroid.app.main.home.article.ArticleVO

/**
 * @author: Listensong
 * Time: 19-10-25 下午8:43
 * Desc: com.song.example.wanandroid.app.main.home.BaseArticlePagedAdapter
 */
@Suppress("unused")
class ArticlePagedAdapter(
        diffCallback: DiffUtil.ItemCallback<ArticleVO>,
        private val variableId: Int,
        private val viewDataBinding: (parent: ViewGroup, viewType: Int) -> ViewDataBinding
) : PagedListAdapter<ArticleVO, ArticlePagedAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = viewDataBinding(parent, viewType)
        val holder = ViewHolder(binding.root)
        holder.setHolderBinding(binding)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.handleBindView(variableId, getItem(position))
    }

    inner class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        private lateinit var holderBinding: ViewDataBinding

        fun setHolderBinding(binding: ViewDataBinding) {
            holderBinding = binding
        }

        fun getHolderBinding(): ViewDataBinding = holderBinding

        fun handleBindView(variableId: Int, vo: ArticleVO?) {
            holderBinding.setVariable(variableId, vo)
        }
    }
}