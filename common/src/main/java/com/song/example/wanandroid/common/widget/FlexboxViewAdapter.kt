package com.song.example.wanandroid.common.widget

import android.view.View
import android.view.ViewGroup

/**
 * @author: Listensong
 * Time: 19-12-2 下午3:17
 * Desc: com.song.example.wanandroid.common.widget.FlexboxViewAdapter
 */
abstract class FlexboxViewAdapter<T: Any>(
        private var dataList: List<T> = emptyList()
) {

    interface OnDataCacheChangeListener {
        fun onDataChanged()
    }

    /**
     *
     */
    interface OnItemClickedListener<DATA: Any> {
        fun onItemClicked(position: Int, data: DATA)
    }
    private var itemClickListener: OnItemClickedListener<T>? = null
    fun setItemClickedListener(clickListener: OnItemClickedListener<T>?) {
        itemClickListener = clickListener
    }

    fun onItemClick(position: Int) {
        itemClickListener?.onItemClicked(position, dataList[position])
    }

    /**
     *
     */
    interface OnItemSelectedListener<DATA: Any> {
        fun onItemSelected(position: Int, data: DATA)
    }
    private var itemSelectedListener: OnItemSelectedListener<T>? = null
    fun setItemSelectedListener(selectedListener: OnItemSelectedListener<T>?) {
        itemSelectedListener = selectedListener
    }

    fun onItemSelected(position: Int) {
        itemSelectedListener?.onItemSelected(position, dataList[position])
    }


    private var onDataCacheChangeListener: OnDataCacheChangeListener? = null
    fun setOnDataCacheChangeListener(dataCacheChangeListener: OnDataCacheChangeListener?) {
        this.onDataCacheChangeListener = dataCacheChangeListener
    }

    open fun notifyDataChanged() {
        onDataCacheChangeListener?.onDataChanged()
    }

    fun setDataList(list: List<T>) {
        if (list.isEmpty()) {
            return
        }
        this.dataList = list
        notifyDataChanged()
    }

    fun getDataList(): List<T> =  this.dataList
    fun getItemAt(pos: Int): T =  this.dataList[pos]
    fun getItemCount(): Int = this.dataList.size

    fun viewDelegate(parent: ViewGroup, position: Int): View {
        return onViewDelegate(parent, position, this.dataList[position])
    }

    abstract fun onViewDelegate(parent: ViewGroup, position: Int, data: T): View

    fun clear() {
        this.dataList = emptyList()
    }

    fun isEmpty(): Boolean {
        return this.dataList.isEmpty()
    }
}