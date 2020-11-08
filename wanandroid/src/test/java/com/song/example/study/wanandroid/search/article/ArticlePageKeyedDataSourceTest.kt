package com.song.example.study.wanandroid.search.article

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PageKeyedDataSource
import com.song.example.study.common.network.retrofit.CoroutineLifecycleCallExtensionKtPath
import com.song.example.study.wanandroid.main.home.article.ArticleVO
import com.song.example.study.wanandroid.network.WanService
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

/**
 * @author Listensong
 * @package com.song.example.study.wanandroid.search.article
 * @fileName ArticlePageKeyedDataSourceTest
 * @date on 6/7/2020 9:47 PM
 * @desc TODO
 * @email No
 */
class ArticlePageKeyedDataSourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val testScope = TestCoroutineScope()

    private val apiService: WanService = mockk()
    private val keyword: String = ""
    private val pageKeyedDataSource: ArticlePageKeyedDataSource = spyk(
            ArticlePageKeyedDataSource(apiService, testScope, keyword),
            recordPrivateCalls = true
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        mockkStatic(CoroutineLifecycleCallExtensionKtPath)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun loadInitialThenOnResultNextPageKey1() = runBlockingTest {
        val mockCallback: PageKeyedDataSource.LoadInitialCallback<Int, ArticleVO> = prepareLoadInitialCallbackMock()

        pageKeyedDataSource.loadInitial(mockk(), mockCallback)

        verify(exactly = 1) { mockCallback.onResult(any(),0, 1) }
    }

    private fun prepareLoadInitialCallbackMock(): PageKeyedDataSource.LoadInitialCallback<Int, ArticleVO> {
        every { pageKeyedDataSource["getSearchResult"](0, "") } returns emptyList<ArticleVO>()
        val mockCallback: PageKeyedDataSource.LoadInitialCallback<Int, ArticleVO> = mockk()
        justRun { mockCallback.onResult(any(), any(), any()) }
        return mockCallback
    }

    @Test
    fun loadAfterThenOnResultWithLoadParamsKeyPlus1() = runBlockingTest {
        val (loadParams, mockCallback: PageKeyedDataSource.LoadCallback<Int, ArticleVO>) = prepareLoadCallbackMock()

        pageKeyedDataSource.loadAfter(loadParams, mockCallback)

        verify(exactly = 1) { mockCallback.onResult(any(), loadParams.key + 1) }
    }

    private fun prepareLoadCallbackMock():
            Pair<PageKeyedDataSource.LoadParams<Int>, PageKeyedDataSource.LoadCallback<Int, ArticleVO>> {
        val loadParams = PageKeyedDataSource.LoadParams(1, 1)
        every { pageKeyedDataSource["getSearchResult"](loadParams.key, "") } returns emptyList<ArticleVO>()
        val mockCallback: PageKeyedDataSource.LoadCallback<Int, ArticleVO> = mockk()
        justRun { mockCallback.onResult(any(), any()) }
        return Pair(loadParams, mockCallback)
    }

    @Test
    fun loadBeforeThenDoNothing() {
        val mockCallback: PageKeyedDataSource.LoadCallback<Int, ArticleVO> = mockk()

        pageKeyedDataSource.loadBefore(mockk(), mockCallback)

        verify(exactly = 0) { mockCallback.onResult(any(), any()) }
    }
}