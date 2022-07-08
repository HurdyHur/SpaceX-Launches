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
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        val formattedDate = try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(launchDate * 1000)
            sdf.format(netDate)
        } catch (e : Exception) {
            ""
        }

        val missionSuccessMark = if (missionSuccessful) {
            "\u2713"
        } else {
            "\u2715"
        }

        return LaunchItem(
            name = name,
            launchDate = formattedDate,
            missionSuccessful = missionSuccessMark,
            patchImageUrl = patchImageUrl
        )
    }
}
