package com.harry.spacexlaunches.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harry.spacexlaunches.launches.model.LaunchUi
import kotlinx.coroutines.launch

class LaunchesViewModel(private val launchesUseCase: LaunchesUseCase) : ViewModel() {

    private val launchesPublisher: MutableLiveData<LaunchUi> = MutableLiveData()

    val launches: LiveData<LaunchUi> = launchesPublisher

    fun getLaunches() {
        viewModelScope.launch {
            launchesPublisher.postValue(LaunchUi.Loading)
            launchesPublisher.postValue(launchesUseCase.getLaunches())
        }
    }
}
