package com.song.example.wanandroid.app.main.home


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.song.example.wanandroid.app.data.AppDataBase
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * @author Listensong
 * @package com.song.example.wanandroid.app.main.home
 * @fileName BannerVOTest
 * @date on 4/1/2020 10:45 PM
 * @desc: TODO
 * @email No
 */
@RunWith(AndroidJUnit4::class)
class BannerDAOTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var bannerDao: BannerDAO
    private lateinit var db: AppDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, AppDataBase::class.java).build()
        bannerDao = db.homeBannersDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    private fun getBannerPOList(): List<BannerPO> {
        return listOf(
                BannerPO(
                        id = 0,
                        desc = "desc0",
                        imagePath = "imagePath0",
                        isVisible = 0,
                        order =  0,
                        title = "title0",
                        type = 0,
                        url = "url"
                ),
                BannerPO(
                        id = 1,
                        desc = "desc1",
                        imagePath = "imagePath1",
                        isVisible = 1,
                        order =  1,
                        title = "title1",
                        type = 1,
                        url = "url1"
                ),
                BannerPO(
                        id = 2,
                        desc = "desc2",
                        imagePath = "imagePath2",
                        isVisible = 2,
                        order =  2,
                        title = "title2",
                        type = 2,
                        url = "url2"
                )
        )
    }

    private fun <T> LiveData<T>.blockingObserver(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)
        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }
        observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        return value
    }

    @Test
    @Throws(Exception::class)
    fun testBannerDAO_insertAndGetBannerAndClear() {
        val bannerPOList: List<BannerPO> = getBannerPOList()

        var bannerSavedPOs = bannerDao.getBanners().blockingObserver()
        assertEquals(0, bannerSavedPOs?.size)

        bannerDao.insertAll(bannerPOList)
        bannerSavedPOs = bannerDao.getBanners().blockingObserver()
        assertNotNull(bannerSavedPOs)
        assertEquals(3, bannerSavedPOs?.size)
        bannerSavedPOs?.forEachIndexed { index, bannerVO ->
            assertEquals(bannerVO.title, bannerPOList[index].title)
            assertEquals(bannerVO.type, bannerPOList[index].type)
            assertEquals(bannerVO.imagePath, bannerPOList[index].imagePath)
            assertEquals(bannerVO.url, bannerPOList[index].url)
        }

        bannerDao.clear()
        bannerSavedPOs = bannerDao.getBanners().blockingObserver()
        assertEquals(0, bannerSavedPOs?.size)
    }

}