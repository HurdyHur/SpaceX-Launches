package com.harry.launch_repository

import com.harry.launch_repository.model.Launch

interface LaunchRepository {
    suspend fun getLaunches(): List<Launch>
}
