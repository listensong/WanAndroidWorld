package com.song.example.wanandroid.base.job

import androidx.lifecycle.MutableLiveData
import com.song.example.wanandroid.common.network.RequestStatus


/**
 * @author: Listensong
 * @time: 19-10-8 下午4:40
 * @desc: com.song.example.wanandroid.base.job.BaseRepository
 */
abstract class BaseRepository {
    var requestStatus: MutableLiveData<RequestStatus> = MutableLiveData(RequestStatus.initial())
}