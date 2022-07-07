package com.harry.spacex_api

import com.harry.spacex_api.model.Launch
import com.harry.spacex_api.model.LaunchResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal class KtorSpaceXApi : SpaceXApi {

    private fun getClient(): HttpClient {
        return HttpClient(CIO) {
            expectSuccess = true

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
            }
            install(HttpCache)
        }
    }

    override suspend fun getLaunches(): LaunchResponse {
        val client = getClient()

        return try {
            val response: String = client.get("$BASE_URL/launches").body()
            val decodedResponse = Json.decodeFromString<List<Launch>>(response)
            client.close()

            LaunchResponse.Success(decodedResponse)
        } catch (e: Exception) {
            client.close()
            LaunchResponse.Failure(e)
        }
    }

    companion object {
        private const val BASE_URL = "https://api.spacexdata.com/v4/"
    }
}
