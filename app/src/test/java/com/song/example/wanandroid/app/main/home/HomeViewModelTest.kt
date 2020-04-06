package com.song.example.wanandroid.app.main.home

import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Listensong
 * @package com.song.example.wanandroid.app.main.home
 * @fileName HomeViewModelTest
 * @date on 3/29/2020 6:45 PM
 * @desc: TODO
 * @email No
 */
class HomeViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val testScope = TestCoroutineScope()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
        testScope.cleanupTestCoroutines()
        unmockkAll()
    }

    @Test
    fun getBanners_thenHomeRepositoryGetBannerCalled() {
        val mockRepository = mockk<HomeRepository>()
        every {
            mockRepository.getBanners()
            mockRepository.getArticles()
            mockRepository.initArticlesPageList(any())
        } returns mockk()
        HomeViewModel(mockRepository)
        verify {
            mockRepository.getBanners()
            mockRepository.getArticles()
            mockRepository.initArticlesPageList(any())
        }
    }

    @Test
    fun loadBanner_thenHomeRepositoryRequestBannerCalled() = runBlockingTest {
        val mockRepository = mockk<HomeRepository>()
        every {
            mockRepository.getBanners()
            mockRepository.getArticles()
            mockRepository.initArticlesPageList(any())
        } returns mockk()
        val homeViewModel = HomeViewModel(mockRepository)

        coEvery {
            mockRepository.requestBanners()
        } returns emptyList()

        homeViewModel.loadBanner(testScope)
        coVerify(exactly = 1) {
            mockRepository.requestBanners()
        }
    }

    @Test
    fun loadArticle_thenHomeRepositoryRequestArticleCalled() = runBlockingTest {
        val mockRepository = mockk<HomeRepository>()
        every {
            mockRepository.getBanners()
            mockRepository.getArticles()
            mockRepository.initArticlesPageList(any())
        } returns mockk()
        val homeViewModel = HomeViewModel(mockRepository)

        coEvery {
            mockRepository.requestArticles()
        } returns emptyList()

        homeViewModel.loadArticle(testScope)
        coVerify(exactly = 1) {
            mockRepository.requestArticles()
        }
    }
}