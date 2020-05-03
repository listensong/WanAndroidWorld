package com.song.example.study.basetest

import java.io.File
import java.nio.charset.Charset

/**
 * @package com.song.example.study.basetest
 * @fileName MockAssest
 * @date on 3/22/2020 12:48 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
object MockAssets {

    fun readFile(path: String): String {
        return file2String(File(path))
    }

    fun file2String(f: File, charset: Charset = Charsets.UTF_8): String {
        return f.readText(charset)
    }
}