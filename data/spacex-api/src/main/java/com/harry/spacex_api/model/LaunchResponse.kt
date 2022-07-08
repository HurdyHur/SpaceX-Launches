package com.harry.spacex_api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable as Serializable

sealed class LaunchResponse {
    data class Success(val launches: List<Launch>): LaunchResponse()
    data class Failure(val error: Throwable): LaunchResponse()
}

@Serializable
data class Launch(
    val name: String,
    @SerialName("date_unix")
    val date: Long,
    val rocket: String,
    val success: Boolean?,
    val links: LinksResponse
)

@Serializable
data class LinksResponse(
    val patch: PatchResponse?,
)

@Serializable
data class PatchResponse(
    val small: String?,
    val large: String?
)
