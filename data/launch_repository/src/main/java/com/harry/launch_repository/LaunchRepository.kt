package com.harry.launch_repository

import com.harry.launch_repository.model.Launches

interface LaunchRepository {
    suspend fun getLaunches(): Launches

    companion object {
        fun getLaunchRepository(): LaunchRepository = LaunchRepositoryImpl()
    }
}
