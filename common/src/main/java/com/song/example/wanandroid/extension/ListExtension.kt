package com.song.example.wanandroid.extension

/**
 * @author: Listensong
 * @time 19-11-25 下午2:44
 * @desc com.song.example.wanandroid.extension.ListExtension
 */
inline fun <T: Any> List<T>?.ifValid(action: (List<T>) -> Unit) {
    if (this != null && this.isNotEmpty()) {
        action(this)
    }
}