package com.song.example.study.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * @package com.song.example.study.extension
 * @fileName LifecycleExtension
 * @date on 6/7/2020 6:18 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
fun <T> LifecycleOwner.observer(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(this, observer)
}