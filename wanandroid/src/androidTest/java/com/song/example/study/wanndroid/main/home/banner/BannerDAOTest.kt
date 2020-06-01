package com.song.example.study.wanndroid.main.home.banner


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.song.example.study.wanndroid.main.home.TestUtils
import com.song.example.study.extension.moshi
import com.song.example.study.wanandroid.data.WanDataBase
import com.song.example.study.wanandroid.main.home.banner.BannerDAO
import com.song.example.study.wanandroid.main.home.banner.BannerDataDTO
import com.song.example.study.wanandroid.main.home.banner.BannerPO
import com.song.example.study.wanandroid.main.home.banner.toPOList
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
 * @package com.song.example.study.app.main.home
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
    private lateinit var db: WanDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, WanDataBase::class.java).build()
        bannerDao = db.homeBannersDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    private fun getBannerPOList(): List<BannerPO> {
        val json = TestUtils.readFile("WanHomeBanner.json")
        val list = json.moshi(BannerDataDTO::class.java)
        return list.toPOList()
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

        bannerDao.insert(bannerPOList)
        bannerSavedPOs = bannerDao.getBanners().blockingObserver()
        assertNotNull(bannerSavedPOs)
        assertEquals(4, bannerSavedPOs?.size)
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