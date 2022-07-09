package com.harry.spacexlaunches

import com.harry.launch_repository.LaunchRepository
import com.harry.launch_repository.model.Launches
import com.harry.spacexlaunches.launches.LaunchesUseCase
import com.harry.spacexlaunches.launches.LaunchesUseCaseImpl
import com.harry.spacexlaunches.launches.model.LaunchUi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LaunchUseCaseTest {

    private val repository: LaunchRepository = mockk()
    private val useCase = LaunchesUseCaseImpl(repository)

    @Test
    fun `test failure correctly mapped`() {
        coEvery { repository.getLaunches() } returns Launches.Failure(IllegalStateException())

        runTest {
            assert(useCase.getLaunches() is LaunchUi.Failure)
        }
    }

    @Test
    fun `test Success correctly mapped`() {
        coEvery { repository.getLaunches() } returns Launches.Success(listOf())

        runTest {
            assert(useCase.getLaunches() is LaunchUi.Success)
        }
    }
}
