package com.song.example.study.wanandroid.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
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
import org.junit.*
import org.junit.Assert.assertEquals
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * @author Listensong
 * @package com.song.example.study.wanandroid.search
 * @fileName SearchRepositoryTest
 * @date on 6/7/2020 7:48 PM
 * @desc TODO
 * @email No
 */
class SearchRepositoryTest: KodeinAware {

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

    override val kodein = Kodein.lazy {
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
        verify(exactly = 1) { hotwordDAO.getHotWord()}
    }

    @Test
    fun requestHotWord() = runBlocking {
        val mockResponseBody = WanAppTestUtils.generateMockResponseBody(SearchTestConst.WAN_HOT_WORD_FILE)
        coEvery {
            apiService.getSearchHotKey().suspendAwaitTimeout(10000)
        } returns HttpResult.Okay(mockResponseBody, mockk())

        val slotPOList = slot<List<HotWordPO>>()
        every { hotwordDAO.clearAndInsert(capture(slotPOList)) } just Runs

        repository.requestHotWord()
        assertEquals(9, slotPOList.captured.size)
    }

    @Test
    fun refreshDataSourceAndUpdateKeyword() {
        every { articleDataSourceFactory.updateDataSourceKeyword(SEARCH_KEY_WORD_EMPTY) } just Runs
        repository.refreshDataSourceAndUpdateKeyword(SEARCH_KEY_WORD_EMPTY)
        verify(exactly = 0) { articleDataSourceFactory.updateDataSourceKeyword(SEARCH_KEY_WORD_EMPTY) }
    }

    @Test
    fun refreshDataSource() {
        every { articleDataSourceFactory.invalidate() } just Runs
        repository.refreshDataSource()
        verify(exactly = 1) { articleDataSourceFactory.invalidate() }
    }

    @Test
    fun searchResultPagedList() {
        every { articleDataSourceFactory.updateDataSourceKeyword(SEARCH_KEY_WORD) } just Runs
        repository.searchResultPagedList(SEARCH_KEY_WORD)
        verify { articleDataSourceFactory.updateDataSourceKeyword(SEARCH_KEY_WORD) }
    }

    @Test
    fun searchResultPagedList_emptyWordThenReturnEmptyList() {
        val list = repository.searchResultPagedList(SEARCH_KEY_WORD_EMPTY)
        assertNull(list.value)
    }
}