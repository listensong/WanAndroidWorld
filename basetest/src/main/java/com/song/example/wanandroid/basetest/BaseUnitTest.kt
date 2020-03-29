package com.song.example.wanandroid.basetest

import android.content.Context
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.mockito.Mock

/**
 * @package com.song.example.wanandroid.basetest
 * @fileName BaseUnitTest
 * @date on 3/29/2020 12:10 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
abstract class BaseUnitTest {
    @Rule
    @JvmField
    var exception: ExpectedException = ExpectedException.none()


    @Mock
    var baseMockContext: Context? = null
}