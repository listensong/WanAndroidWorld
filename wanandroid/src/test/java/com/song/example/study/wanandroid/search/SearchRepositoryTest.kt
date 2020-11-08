package com.song.example.study.wanandroid.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.song.example.study.common.network.retrofit.CoroutineLifecycleCallExtensionKtPath
import com.song.example.study.common.network.retrofit.HttpResult
import com.song.example.study.common.network.retrofit.suspendAwaitTimeout
import com.song.example.study.wanandroid.WanAppTestUtils
import com.song.example.study.wanandroid.data.wanAppDbModule
import com.song.example.study.wanandroid.network.WanService
import com.song.example.study.wanandroid.search.article.ArticleDataSourceFactory
import com.song.example.study.wanandroid.search.word.HotWordDAO
import com.song.example.study.wanandroid.search.word.HotWordPO
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.kodein.di.*
import kotlin.test.assertNull

/**
 * @author Listensong
 * @package com.song.example.study.wanandroid.search
 * @fileName SearchRepositoryTest
 * @date on 6/7/2020 7:48 PM
 * @desc TODO
 * @email No
 */
class SearchRepositoryTest : DIAware {

    companion object {
        const val SEARCH_KEY_WORD = "HelloWorld"
        const val SEARCH_KEY_WORD_EMPTY = ""
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val testScope = TestCoroutineScope()

    private val apiService: WanService = mockk()
    private val hotwordDAO: HotWordDAO = mockk()
    private val repository: SearchRepository by instance()
    private val articleDataSourceFactory: ArticleDataSourceFactory = mockk()

    override val di = DI.lazy {
        import(wanAppDbModule)
        import(wanSearchKodeinModule)
        bind<WanService>() with singleton { apiService }
        bind<HotWordDAO>(overrides = true) with singleton { hotwordDAO }
    }


    @Before
    fun setUp() {
        repository.setDataSourceFactory(articleDataSourceFactory)
        Dispatchers.setMain(mainThreadSurrogate)
        mockkStatic(CoroutineLifecycleCallExtensionKtPath)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
        testScope.cleanupTestCoroutines()
        unmockkAll()
    }

    @Test
    fun getHotWord() {
        every { hotwordDAO.getHotWord() } returns mockk()
        repository.getHotWord()
        verify(exactly = 1) { hotwordDAO.getHotWord() }
    }

    @Test
    fun requestHotWord() = runBlocking {
        val slotPOList = prepareHotWordMockData()

        repository.requestHotWord()
        assertEquals(9, slotPOList.captured.size)
    }

    private fun prepareHotWordMockData(): CapturingSlot<List<HotWordPO>> {
        val mockResponseBody = WanAppTestUtils.generateMockResponseBody(SearchTestConst.WAN_HOT_WORD_FILE)
        coEvery {
            apiService.getSearchHotKey().suspendAwaitTimeout(10000)
        } returns HttpResult.Okay(mockResponseBody, mockk())

        val slotPOList = slot<List<HotWordPO>>()
        justRun { hotwordDAO.clearAndInsert(capture(slotPOList)) }
        return slotPOList
    }

    @Test
    fun refreshDataSourceAndUpdateKeyword() {
        justRun { articleDataSourceFactory.updateDataSourceKeyword(SEARCH_KEY_WORD_EMPTY) }
        repository.refreshDataSourceAndUpdateKeyword(SEARCH_KEY_WORD_EMPTY)
        verify(exactly = 0) { articleDataSourceFactory.updateDataSourceKeyword(SEARCH_KEY_WORD_EMPTY) }
    }

    @Test
    fun refreshDataSource() {
        justRun { articleDataSourceFactory.invalidate() }
        repository.refreshDataSource()
        verify(exactly = 1) { articleDataSourceFactory.invalidate() }
    }

    @Test
    fun searchResultPagedList() {
        justRun { articleDataSourceFactory.updateDataSourceKeyword(SEARCH_KEY_WORD) }
        repository.searchResultPagedList(SEARCH_KEY_WORD)
        verify { articleDataSourceFactory.updateDataSourceKeyword(SEARCH_KEY_WORD) }
    }

    @Test
    fun searchResultPagedList_emptyWordThenReturnEmptyList() {
        val list = repository.searchResultPagedList(SEARCH_KEY_WORD_EMPTY)
        assertNull(list.value)
    }
}