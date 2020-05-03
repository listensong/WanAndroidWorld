package com.song.example.study.common.widget

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @package com.song.example.study.common.widget
 * @fileName MixedTypeAdapter
 * @date on 4/15/2020 7:17 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
@Suppress("unused")
class MixedTypeAdapter<T>(
        private var dataList: MutableList<T>?,
        private val variableId: Int,
        private val viewType: (data: T) -> Int,
        private val spanSize: (data: T) -> Int,
        private val viewDataBinding: (parent: ViewGroup, viewType: Int) -> ViewDataBinding
) : RecyclerView.Adapter<MixedTypeAdapter<T>.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = viewDataBinding(parent, viewType)
        val holder = ViewHolder(binding.root)
        holder.setHolderBinding(binding)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.handleBindView(variableId, dataList?.get(position))
    }

    fun getDataList(): List<T>? {
        return dataList
    }

    fun release() {
        dataList = null
    }

    fun setDataList(dataList: MutableList<T>?) {
        if (dataList == null) {
            return
        }

        this.dataList = dataList
    }

    fun diffUpdate(lifecycleScope: LifecycleCoroutineScope,
                   newList: MutableList<T>?,
                   diffCallback: DiffUtil.Callback) {
        if (newList == null) {
            return
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                DiffUtil.calculateDiff(diffCallback, true)
            }.dispatchUpdatesTo(this@MixedTypeAdapter)
            dataList = newList
        }
    }

    fun append(list: List<T>?) {
        if (list == null || list.isEmpty()) {
            return
        }
        val size = dataList?.size ?: 1
        dataList?.addAll(list)
        notifyItemRangeInserted(size - 1, list.size)
    }

    fun getSpanSize(position: Int): Int {
        return try {
             dataList?.get(position)?.let {
                spanSize(it)
            } ?: 0
        } catch (e: IndexOutOfBoundsException) {
            0
        }
    }

    fun getItem(position: Int): T? {
        return try {
            dataList?.get(position)
        } catch (e: Exception) {
            null
        }
    }

    override fun getItemCount(): Int = dataList?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return try {
            dataList?.get(position)?.let {
                viewType(it)
            } ?: 0
        } catch (e: Exception) {
            0
        }
    }

    inner class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        private lateinit var holderBinding: ViewDataBinding

        fun setHolderBinding(binding: ViewDataBinding) {
            holderBinding = binding
        }

        fun getHolderBinding(): ViewDataBinding = holderBinding

        fun handleBindView(variableId: Int, any: T?) {
            holderBinding.setVariable(variableId, any)
        }
    }
}