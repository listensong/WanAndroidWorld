package com.song.example.wanandroid.app.main.home.article


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.song.example.wanandroid.app.data.AppDataBase
import com.song.example.wanandroid.app.main.home.HomeConst
import com.song.example.wanandroid.app.main.home.TestUtils.readFile
import com.song.example.wanandroid.extension.moshi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * @author Listensong
 * @package com.song.example.wanandroid.app.main.home
 * @fileName ArticleDAOTest
 * @date on 4/1/2020 10:45 PM
 * @desc: TODO
 * @email No
 */
@RunWith(AndroidJUnit4::class)
class ArticleDAOTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var articleDao: ArticleDAO
    private lateinit var db: AppDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, AppDataBase::class.java).build()
        articleDao = db.homeArticleDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {

        db.close()
    }

    private fun getArticlePOList(): List<ArticlePO> {
        val json = readFile("HomeArticleJson.json")
        val list = json.moshi(ArticleDataDTO::class.java)
        return list.toPOList(0, 0)
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
    fun testArticleDAO_insertAndGetArticleAndClear() {
        val articlePOList: List<ArticlePO> = getArticlePOList()

        var articleSavedPOs = articleDao.getArticles().blockingObserver()
        assertEquals(0, articleSavedPOs?.size)

        articleDao.insert(articlePOList)
        articleSavedPOs = articleDao.getArticles().blockingObserver()
        assertNotNull(articleSavedPOs)
        assertEquals(articlePOList.size, articleSavedPOs?.size)
        articleSavedPOs?.forEachIndexed { index, articleVO ->
            assertEquals(articlePOList[index].title, articleVO.title)
            assertEquals(articlePOList[index].type, articleVO.type)
            assertEquals(articlePOList[index].link, articleVO.link)
            assertEquals(articlePOList[index].chapterName, articleVO.chapterName)
        }

        articleDao.clear()
        articleSavedPOs = articleDao.getArticles().blockingObserver()
        assertEquals(0, articleSavedPOs?.size)

        articleDao.clearAndInsert(
                listOf(
                        createMaskArticlePO(
                                HomeConst.BASE_INDEX_BANNER,  0,
                                HomeConst.ITEM_TYPE_BANNER, "BANNER_TITLE", "BANNER_LINK")
                )
        )
        articleSavedPOs = articleDao.getArticles().blockingObserver()
        assertNotNull(articleSavedPOs)
        articleSavedPOs?.let {
            assertEquals(1, it.size)
            assertEquals(HomeConst.ITEM_TYPE_BANNER, it[0].itemType)
        }
    }

}