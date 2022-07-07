package com.harry.spacexlaunches.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harry.launch_repository.LaunchRepository
import com.harry.launch_repository.model.Launch
import com.harry.launch_repository.model.Launches
import com.harry.spacexlaunches.ui.model.LaunchItem
import com.harry.spacexlaunches.ui.model.LaunchUi
import kotlinx.coroutines.launch

class LaunchesViewModel(private val launchesRepository: LaunchRepository = LaunchRepository.getLaunchRepository()) :
    ViewModel() {

    private val launchesPublisher: MutableLiveData<LaunchUi> = MutableLiveData()

    val launches: LiveData<LaunchUi> = launchesPublisher

    fun getLaunches() {
        viewModelScope.launch {
            val launches = launchesRepository.getLaunches()
            launchesPublisher.postValue(launches.toLaunchUi())
        }
    }

    private fun Launches.toLaunchUi(): LaunchUi {
        return when (this) {
            is Launches.Failure -> LaunchUi.Failure
            is Launches.Success -> LaunchUi.Success(this.launches.map { it.toLaunchItem() })
        }
    }

    private fun Launch.toLaunchItem(): LaunchItem {
        return LaunchItem(
            name = name,
            launchDate = launchDate,
            missionSuccessful = missionSuccessful,
            patchImageUrl = patchImageUrl
        )
    }
}
