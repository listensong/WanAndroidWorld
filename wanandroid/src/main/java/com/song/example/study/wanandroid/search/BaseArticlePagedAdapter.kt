package com.song.example.study.wanandroid.search

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.song.example.study.wanandroid.main.home.article.ArticleVO

/**
 * @package com.song.example.study.wanandroid.search
 * @fileName BaseArticlePagedAdapter
 * @date on 6/7/2020 4:46 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
class BaseArticlePagedAdapter(
        diffCallback: DiffUtil.ItemCallback<ArticleVO>,
        private val variableId: Int,
        private val viewDataBinding: (parent: ViewGroup, viewType: Int) -> ViewDataBinding
) : PagedListAdapter<ArticleVO, BaseArticlePagedAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = viewDataBinding(parent, viewType)
        val holder = ViewHolder(binding.root)
        holder.setHolderBinding(binding)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.handleBindView(variableId, getItem(position))
    }

    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        private lateinit var holderBinding: ViewDataBinding

        fun setHolderBinding(binding: ViewDataBinding) {
            holderBinding = binding
        }

        fun getHolderBinding(): ViewDataBinding = holderBinding

        fun handleBindView(variableId: Int, any: ArticleVO?) {
            holderBinding.setVariable(variableId, any)
        }
    }
}