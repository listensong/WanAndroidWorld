package com.song.example.wanandroid.common.base

import com.song.example.wanandroid.base.job.CompositeJob
import kotlinx.coroutines.Job
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.powermock.modules.junit4.PowerMockRunner

/**
 * @author: Listensong
 * Time: 19-10-23 上午8:55
 * Desc: com.song.example.wanandroid.common.base.CompositeJobTest
 */
@RunWith(PowerMockRunner::class)
class CompositeJobTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `add 添加job之后，size应该加1`() {
        val compositeJob = CompositeJob()
        val beforeSize = compositeJob.size()
        compositeJob.add(Job())
        val afterSize = compositeJob.size()
        assertTrue(afterSize - beforeSize == 1)
    }

    @Test
    fun `clear 清理之后，size应该为0`() {
        val compositeJob = CompositeJob()
        compositeJob.add(Job())
        compositeJob.add(Job())
        compositeJob.add(Job())
        compositeJob.add(Job())
        compositeJob.add(Job())
        assertTrue(compositeJob.size() > 0)
        compositeJob.clear()
        assertTrue(compositeJob.size() == 0)
    }

    @Test
    fun isDisposed() {

    }

    @Test
    fun `dispose 中断所有job之后，size应该为0`() {
        val compositeJob = CompositeJob()
        compositeJob.add(Job())
        compositeJob.add(Job())
        compositeJob.add(Job())
        compositeJob.add(Job())
        compositeJob.add(Job())
        assertTrue(compositeJob.size() > 0)
        compositeJob.dispose()
        assertTrue(compositeJob.size() == 0)
    }

    @Test
    fun `remove 移除某个job之后，size应该减1`() {
        val compositeJob = CompositeJob()
        val compositeSize = compositeJob.size()
        val job = Job()
        compositeJob.add(Job())
        compositeJob.add(Job())
        compositeJob.add(job)
        assertTrue(compositeJob.size() - compositeSize == 3)
        compositeJob.remove(job)
        assertTrue(compositeJob.size() - compositeSize == 2)
    }

    @Test
    fun `delete 删除某个job之后，size应该减1`() {
        val compositeJob = CompositeJob()
        val compositeSize = compositeJob.size()
        val job = Job()
        compositeJob.add(Job())
        compositeJob.add(Job())
        compositeJob.add(job)
        assertTrue(compositeJob.size() - compositeSize == 3)
        compositeJob.delete(job)
        assertTrue(compositeJob.size() - compositeSize == 2)
        //assertTrue(job.isActive)
    }

    @Test
    fun `size 返回CompositeJob的大小`() {
        val compositeJob = CompositeJob()
        val compositeSize = compositeJob.size()
        compositeJob.add(Job())
        compositeJob.add(Job())
        compositeJob.add(Job())
        assertTrue(compositeJob.size() - compositeSize == 3)
    }
}