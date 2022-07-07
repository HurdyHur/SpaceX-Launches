package com.harry.launch_repository.model

data class Launch(val name: String,
                  val launchDate: String,
                  val missionSuccessful: Boolean,
                  val patchImageUrl: String)
