package com.song.example.wanandroid.extend

/**
 * @author: Listensong
 * Time: 19-11-25 下午2:44
 * Desc: com.song.example.wanandroid.extend.ListExtend
 */
inline fun <T: Any> List<T>?.ifValid(action: (List<T>) -> Unit) {
    if (this != null && this.isNotEmpty()) {
        action(this)
    }
}