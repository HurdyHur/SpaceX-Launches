package com.harry.spacexlaunches.launches

import com.harry.launch_repository.LaunchRepository
import com.harry.launch_repository.model.Launch
import com.harry.launch_repository.model.Launches
import com.harry.spacexlaunches.launches.model.LaunchItem
import com.harry.spacexlaunches.launches.model.LaunchUi
import com.harry.spacexlaunches.util.fromUnixDate

interface LaunchesUseCase {
    suspend fun getLaunches(): LaunchUi
}

class LaunchesUseCaseImpl(private val launchRepository: LaunchRepository): LaunchesUseCase {

    override suspend fun getLaunches() = launchRepository.getLaunches().toLaunchUi()

    private fun Launches.toLaunchUi(): LaunchUi {
        return when (this) {
            is Launches.Failure -> LaunchUi.Failure
            is Launches.Success -> LaunchUi.Success(this.launches.map { it.toLaunchItem() })
        }
    }

    private fun Launch.toLaunchItem(): LaunchItem {
        val missionSuccessMark = if (missionSuccessful) {
            "\u2713"
        } else {
            "\u2715"
        }

        return LaunchItem(
            name = name,
            launchDate = fromUnixDate(launchDate),
            missionSuccessful = missionSuccessMark,
            patchImageUrl = patchImageUrl
        )
    }
}
