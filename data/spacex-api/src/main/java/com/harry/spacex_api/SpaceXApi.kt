package com.harry.spacex_api

import com.harry.spacex_api.model.LaunchResponse

interface SpaceXApi {
    suspend fun getLaunches(): LaunchResponse

    companion object {
        fun getClient(): SpaceXApi = KtorSpaceXApi()
    }
}
