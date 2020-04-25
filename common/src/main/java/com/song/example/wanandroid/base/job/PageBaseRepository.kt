package com.song.example.wanandroid.base.job

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

/**
 * @author: Listensong
 * @time 19-10-8 下午4:40
 * @desc com.song.example.wanandroid.base.job.BaseRepository
 */
abstract class PageBaseRepository: BaseRepository() {

    companion object {
        const val BASE_INIT_PAGE_SIZE = 20
        const val BASE_LOAD_PAGE_SIZE = 20
        const val BASE_ENABLE_PLACEHOLDER = false
        const val BASE_PREFETCH_DISTANCE = 3
    }

    protected fun <KEY: Any, VALUE: Any> queryPagedList(
            dataSourceFactory: DataSource.Factory<KEY, VALUE>,
            pageSize: Int = BASE_LOAD_PAGE_SIZE,
            initialLoadSize: Int = BASE_INIT_PAGE_SIZE,
            boundaryCallback: PagedList.BoundaryCallback<VALUE>? =null,
            config: PagedList.Config? = null
    ): LiveData<PagedList<VALUE>>  {
        return LivePagedListBuilder(
                dataSourceFactory,
                config ?: PagedList.Config
                        .Builder()
                        .setPageSize(pageSize)
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(initialLoadSize)
                        .setPrefetchDistance(BASE_PREFETCH_DISTANCE)
                        .build())
                .setBoundaryCallback(boundaryCallback)
                .build()
    }

}