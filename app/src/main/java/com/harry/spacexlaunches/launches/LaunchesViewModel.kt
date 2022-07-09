package com.harry.spacexlaunches.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harry.launch_repository.LaunchRepository
import com.harry.launch_repository.model.Launch
import com.harry.launch_repository.model.Launches
import com.harry.spacexlaunches.launches.model.LaunchItem
import com.harry.spacexlaunches.launches.model.LaunchUi
import com.harry.spacexlaunches.util.fromUnixDate
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat

class LaunchesViewModel(
    private val launchesUseCase: LaunchesUseCase = LaunchesUseCaseImpl()
) : ViewModel() {

    private val launchesPublisher: MutableLiveData<LaunchUi> = MutableLiveData()

    val launches: LiveData<LaunchUi> = launchesPublisher

    fun getLaunches() {
        launchesPublisher.postValue(LaunchUi.Loading)
        viewModelScope.launch {
            launchesPublisher.postValue(launchesUseCase.getLaunches())
        }
    }
}