package com.song.example.study.wanndroid.main.home

import androidx.test.platform.app.InstrumentationRegistry
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @package com.song.example.study.app.main.home
 * @fileName TestUtils
 * @date on 4/5/2020 6:00 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
object TestUtils {

    fun readFile(file: String): String {
        var resultString = ""
        try {
            val inputStream = InstrumentationRegistry.getInstrumentation()
                    .context.resources.assets.open(file)
            resultString = ""
            BufferedReader(InputStreamReader(inputStream)).use {
                resultString = it.readText()
            }
        } catch (e: Exception) {
            println("TestUtils $file -> $e")
        }
        return resultString
    }
}