package com.song.example.wanandroid.app

import com.song.example.wanandroid.basetest.MockAssets
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * @package com.song.example.wanandroid.app
 * @fileName AppTestConst
 * @date on 4/24/2020 10:51 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
object AppTestUtils {
    const val BASE_PATH = "../app/src/androidTest/assets"

    fun getMockResponseBody(filePath: String): ResponseBody {
        val mockJson: String = MockAssets.readFile(filePath)
        return mockJson.toResponseBody()
    }

    fun generateMockResponseBody(fileName: String): ResponseBody {
        val mockJson: String = readLocalJsonFile(fileName)
        return mockJson.toResponseBody()
    }

    fun readLocalJsonFile(fileName: String): String {
        return MockAssets.readFile("$BASE_PATH/$fileName")
    }
}