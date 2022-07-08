package com.harry.spacexlaunches.ui.model

sealed class LaunchUi {

    object Loading: LaunchUi()
    data class Success(val launches: List<LaunchItem>): LaunchUi()
    object Failure: LaunchUi()

}

data class LaunchItem(
    val name: String,
    val launchDate: String,
    val missionSuccessful: String,
    val patchImageUrl: String
)
