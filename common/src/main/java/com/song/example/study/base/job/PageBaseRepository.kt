package com.song.example.study.base.job

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.song.example.study.base.job.PageBaseRepository.Companion.BASE_ENABLE_PLACEHOLDER

/**
 * @author: Listensong
 * @time 19-10-8 下午4:40
 * @desc com.song.example.study.base.job.BaseRepository
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
                        .setEnablePlaceholders(BASE_ENABLE_PLACEHOLDER)
                        .setInitialLoadSizeHint(initialLoadSize)
                        .setPrefetchDistance(BASE_PREFETCH_DISTANCE)
                        .build())
                .setBoundaryCallback(boundaryCallback)
                .build()
    }

}


fun <Key, Value> DataSource.Factory<Key, Value>.toPagedListLiveData(
        pageSize: Int = PageBaseRepository.BASE_LOAD_PAGE_SIZE,
        initialLoadKey: Key? = null,
        boundaryCallback: PagedList.BoundaryCallback<Value>? =null,
        config: PagedList.Config? = null
): LiveData<PagedList<Value>>  {
    return LivePagedListBuilder(
            this,
            config ?: PagedListConfig(pageSize))
            .setInitialLoadKey(initialLoadKey)
            .setBoundaryCallback(boundaryCallback)
            .build()
}

@Suppress("FunctionName")
fun PagedListConfig(
        pageSize: Int,
        prefetchDistance: Int = pageSize,
        enablePlaceholder: Boolean = BASE_ENABLE_PLACEHOLDER,
        initialLoadSizeHint: Int = pageSize * 2,
        maxSize: Int = PagedList.Config.MAX_SIZE_UNBOUNDED
): PagedList.Config {
    return PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setPrefetchDistance(prefetchDistance)
            .setEnablePlaceholders(enablePlaceholder)
            .setInitialLoadSizeHint(initialLoadSizeHint)
            .setMaxSize(maxSize)
            .build()
}