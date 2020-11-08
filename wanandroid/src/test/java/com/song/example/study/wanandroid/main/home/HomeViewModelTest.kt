package com.song.example.study.wanandroid.main.home

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

/**
 * @author Listensong
 * @package com.song.example.study.app.main.home
 * @fileName HomeViewModelTest
 * @date on 3/29/2020 6:45 PM
 * @desc HomeViewModel Unit test
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
            mockRepository.requestStatus
        } returns mockk()

        HomeViewModel(mockRepository).banners

        verify { mockRepository.getBanners() }
    }

    @Test
    fun requestStatus_thenHomeRepositoryRequestStatusCalled() {
        val mockRepository = mockk<HomeRepository>()
        every {
            mockRepository.getBanners()
            mockRepository.getArticles()
            mockRepository.requestStatus
        } returns mockk()
        HomeViewModel(mockRepository).requestState
        verify { mockRepository.requestStatus }
    }

    @Test
    fun loadBanner_thenHomeRepositoryRequestBannerCalled() = runBlockingTest {
        val mockRepository = mockk<HomeRepository>()
        every {
            mockRepository.getBanners()
            mockRepository.getArticles()
            mockRepository.requestStatus
        } returns mockk()
        coJustRun { mockRepository.requestBanners() }
        val homeViewModel = HomeViewModel(mockRepository)

        homeViewModel.loadBanner(testScope)

        coVerify(exactly = 1) { mockRepository.requestBanners() }
    }

    @Test
    fun loadArticle_thenHomeRepositoryRequestArticleCalled() = runBlockingTest {
        val mockRepository = mockk<HomeRepository>()
        every {
            mockRepository.getBanners()
            mockRepository.getArticles()
            mockRepository.requestStatus
        } returns mockk()
        val homeViewModel = HomeViewModel(mockRepository)

        coJustRun { mockRepository.requestTopArticles() }
        coJustRun { mockRepository.requestArticles(any()) }

        homeViewModel.loadArticle(testScope)
        coVerify(exactly = 1) {
            mockRepository.requestTopArticles()
            mockRepository.requestArticles(any())
        }
    }
}