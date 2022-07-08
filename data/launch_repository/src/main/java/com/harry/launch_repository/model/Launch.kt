package com.harry.launch_repository.model

sealed class Launches {
    data class Success(val launches: List<Launch>): Launches()
    data class Failure(val error: Throwable): Launches()
}

data class Launch(val name: String,
                  val launchDate: Long,
                  val missionSuccessful: Boolean,
                  val patchImageUrl: String)
