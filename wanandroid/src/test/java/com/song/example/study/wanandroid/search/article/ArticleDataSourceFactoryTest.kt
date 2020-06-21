package com.song.example.study.wanandroid.search.article

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.song.example.study.wanandroid.network.WanService
import com.song.example.study.wanandroid.search.word.HotWordDAO
import io.mockk.*
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

/**
 * @author Listensong
 * @package com.song.example.study.wanandroid.search.article
 * @fileName ArticleDataSourceFactoryTest
 * @date on 6/7/2020 9:46 PM
 * @desc TODO
 * @email No
 */
class ArticleDataSourceFactoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testScope = TestCoroutineScope()

    private val apiService: WanService = mockk()
    private val pageKeyedDataSource: ArticlePageKeyedDataSource = mockk()
    private val articleDataSourceFactory: ArticleDataSourceFactory = spyk(
            ArticleDataSourceFactory(apiService, testScope, ""), recordPrivateCalls = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun invalidateThenDataSourceInvalidateCalled() {
        prepareArticlePageKeyedDataSource()
        articleDataSourceFactory.invalidate()
        verify(exactly = 1) { pageKeyedDataSource.invalidate() }
    }

    private fun prepareArticlePageKeyedDataSource() {
        every {
            articleDataSourceFactory["getSourceDataSourceValue"]()
        } returns pageKeyedDataSource
        justRun { pageKeyedDataSource.invalidate() }
        justRun { pageKeyedDataSource.setKeyword(any()) }
    }

    @Test
    fun updateDataSourceKeywordThenDataSourceNotCalled() {
        prepareArticlePageKeyedDataSource()
        articleDataSourceFactory.updateDataSourceKeyword("")
        assertThatPageKeyedDataSourceShouldBeNotCalled()
    }

    private fun assertThatPageKeyedDataSourceShouldBeNotCalled() {
        verify(exactly = 0) { pageKeyedDataSource.invalidate() }
        verify(exactly = 0) { pageKeyedDataSource.setKeyword("") }
    }

    @Test
    fun updateDataSourceKeywordThenDataSourceShouldCalled() {
        prepareArticlePageKeyedDataSource()
        articleDataSourceFactory.updateDataSourceKeyword("HelloWorld")
        assertThatPageKeyedDataSourceShouldBeCalledWithKeyword()
    }

    private fun assertThatPageKeyedDataSourceShouldBeCalledWithKeyword() {
        verify(exactly = 1) { pageKeyedDataSource.invalidate() }
        verify(exactly = 1) { pageKeyedDataSource.setKeyword("HelloWorld") }
    }

    @Test
    fun create() {
        val dataSource = articleDataSourceFactory.create()
        assertTrue(dataSource is ArticlePageKeyedDataSource)
    }
}