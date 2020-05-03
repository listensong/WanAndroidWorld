package com.song.example.study.common.network

import com.song.example.study.common.network.retrofit.NetworkError

/**
 * @package com.song.example.study.common.network
 * @fileName RequestStatus
 * @date on 4/15/2020 10:09 PM
 * @author Listensong
 * @desc
 * @email No
 */
sealed class RequestStatus {

    object Idle : RequestStatus()
    object Working : RequestStatus()
    class Complete(
            val err: NetworkError? = null
    ): RequestStatus()

    companion object {
        fun initial(): RequestStatus {
            return Idle
        }
    }
}
