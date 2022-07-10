package com.harry.launch_repository

import com.harry.launch_repository.model.Launch
import com.harry.launch_repository.model.Launches
import com.harry.spacex_api.SpaceXApi
import com.harry.spacex_api.model.LaunchResponse

internal class LaunchRepositoryImpl(private val spacexApi: SpaceXApi = SpaceXApi.getClient()) :
    LaunchRepository {

    override suspend fun getLaunches(): Launches {
        val networkLaunchResponse = spacexApi.getLaunches()

        return networkLaunchResponse.toLaunches()
    }

    private fun LaunchResponse.toLaunches(): Launches {
        return when (this) {
            is LaunchResponse.Success -> {
                val mappedLaunches = launches.map {
                    Launch(
                        name = it.name, launchDate = it.date,
                        missionSuccessful = it.success ?: false,
                        patchImageUrl = it.links.patch?.small ?: ""
                    )
                }

                return Launches.Success(mappedLaunches)
            }
            is LaunchResponse.Failure -> Launches.Failure(error)
        }
    }
}
