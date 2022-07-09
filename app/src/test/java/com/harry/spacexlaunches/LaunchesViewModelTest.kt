package com.harry.spacexlaunches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harry.launch_repository.LaunchRepository
import com.harry.launch_repository.model.Launches
import com.harry.spacexlaunches.launches.LaunchesViewModel
import com.harry.spacexlaunches.launches.model.LaunchUi
import com.harry.spacexlaunches.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val launchesRepository: LaunchRepository = mockk()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: LaunchesViewModel

    @Before
    fun setup() {
        viewModel = LaunchesViewModel(launchesRepository)
    }

    @Test
    fun `test when getLaunches called Loading Ui is shown`() {
        viewModel.getLaunches()

        assert(viewModel.launches.value is LaunchUi.Loading)
    }

    @Test
    fun `test when getLaunches retrieves launches from repository`() {
        coEvery { launchesRepository.getLaunches() } returns Launches.Failure(IllegalStateException())
        runTest {
            viewModel.getLaunches()
        }

        coVerify { launchesRepository.getLaunches() }
    }

    @Test
    fun `test error retrieving from repository displays failure`() {
        coEvery { launchesRepository.getLaunches() } returns Launches.Failure(IllegalStateException())

        runTest {
            viewModel.getLaunches()
        }

        assert(viewModel.launches.value is LaunchUi.Failure)
    }
}
