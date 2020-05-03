package com.song.example.study.base

import androidx.databinding.ListChangeRegistry
import androidx.databinding.ObservableList

/**
 * @author song
 * @time 19-8-22 上午10:59
 * @desc com.song.example.study.base.CoverObservableArrayList
 */
class CoverObservableArrayList<T> : ArrayList<T>(), ObservableList<T> {
    @Transient
    private var mListeners: ListChangeRegistry? = ListChangeRegistry()

    override fun addOnListChangedCallback(listener: ObservableList.OnListChangedCallback<out ObservableList<T>>?) {
        if (mListeners == null) {
            mListeners = ListChangeRegistry()
        }
        mListeners?.add(listener)
    }

    override fun removeOnListChangedCallback(listener: ObservableList.OnListChangedCallback<out ObservableList<T>>?) {
        mListeners?.remove(listener)
    }

    override fun add(element: T): Boolean {
        super.add(element)
        notifyAdd(size - 1, 1)
        return true
    }

    override fun add(index: Int, element: T) {
        super.add(index, element)
        notifyAdd(index, 1)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val oldSize = size
        val added = super.addAll(elements)
        if (added) {
            notifyAdd(oldSize, size - oldSize)
        }
        return added
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        val added = super.addAll(index, elements)
        if (added) {
            notifyAdd(index, elements.size)
        }
        return added
    }

    override fun clear() {
        val oldSize = size
        super.clear()
        if (oldSize != 0) {
            notifyRemove(0, oldSize)
        }
    }

    /**
     * 新增的update方法，每次都清空 array list 之后再插入新集合，特殊用途
     */
    fun update(collection: Collection<T>?) {
        collection?.let {
            super.clear()
            super.addAll(it)
            if (it.isNotEmpty()) {
                mListeners?.notifyChanged(this, 0, it.size)
            }
        }
    }

    override fun removeAt(index: Int): T {
        val value: T = super.removeAt(index)
        notifyRemove(index, 1)
        return value
    }

    override fun remove(element: T): Boolean {
        val index = indexOf(element)
        return if (index >= 0) {
            removeAt(index)
            true
        } else {
            false
        }
    }

    override fun set(index: Int, element: T): T {
        val ve = super.set(index, element)
        mListeners?.notifyChanged(this, index, 1)
        return ve
    }

    override fun removeRange(fromIndex: Int, toIndex: Int) {
        super.removeRange(fromIndex, toIndex)
        notifyRemove(fromIndex, toIndex - fromIndex)
    }

    private fun notifyAdd(start: Int, count: Int) {
        mListeners?.notifyInserted(this, start, count)
    }

    private fun notifyRemove(start: Int, count: Int) {
        mListeners?.notifyRemoved(this, start, count)
    }
}