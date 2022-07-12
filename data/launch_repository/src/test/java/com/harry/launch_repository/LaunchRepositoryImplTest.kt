package com.harry.launch_repository

import com.harry.launch_repository.model.Launches
import com.harry.spacex_api.SpaceXApi
import com.harry.spacex_api.model.LaunchResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class LaunchRepositoryImplTest {

    private val spaceXApi: SpaceXApi = mock()
    private val launchRepository: LaunchRepository = LaunchRepositoryImpl(spaceXApi)

    @Test
    fun `test getLaunches calls spaceXApi getLaunches`() {
        runBlocking {
            given(spaceXApi.getLaunches()).willReturn(LaunchResponse.Failure(IllegalStateException()))
            launchRepository.getLaunches()

            verify(spaceXApi).getLaunches()
        }
    }

    @Test
    fun `test network failure is mapped to Launches Failure`() {
        val expectedThrowable = IllegalStateException()
        val expectedLaunches = Launches.Failure(expectedThrowable)

        runBlocking {
            given(spaceXApi.getLaunches()).willReturn(LaunchResponse.Failure(expectedThrowable))
            val actualLaunches = launchRepository.getLaunches()

            assertEquals(expectedLaunches, actualLaunches)
        }
    }

    @Test
    fun `test launches successfully fetched returns Launches`() {
        val expectedThrowable = IllegalStateException()
        val expectedLaunches = Launches.Failure(expectedThrowable)

        runBlocking {
            given(spaceXApi.getLaunches()).willReturn(LaunchResponse.Failure(expectedThrowable))
            val actualLaunches = launchRepository.getLaunches()

            assertEquals(expectedLaunches, actualLaunches)
        }
    }
}
