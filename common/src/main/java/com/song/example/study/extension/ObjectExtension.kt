package com.song.example.study.extension

/**
 * @package com.song.example.study.extension
 * @fileName ObjectExtension
 * @date on 6/1/2020 10:44 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
inline fun <reified T: Any> new(): T {
    val clz = T::class.java
    val mCreate = clz.getDeclaredConstructor()
    mCreate. isAccessible = true
    return mCreate.newInstance()
}

inline fun <reified T: Any> new(vararg params: Any): T {
    val clz = T::class.java
    val paramTypes = params.map { it::class.java }.toTypedArray()
    val mCreate = clz.getDeclaredConstructor(*paramTypes)
    mCreate. isAccessible = true
    return mCreate.newInstance(* params)
}
