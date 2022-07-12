package com.harry.spacexlaunches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harry.launch_repository.model.Launches
import com.harry.spacexlaunches.launches.LaunchesUseCase
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

    private val launchesUseCase: LaunchesUseCase = mockk()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: LaunchesViewModel

    @Before
    fun setup() {
        viewModel = LaunchesViewModel(launchesUseCase)
    }

    @Test
    fun `test when getLaunches called Loading Ui is shown`() {
        runTest {
            viewModel.getLaunches()
        }

        assert(viewModel.launches.value is LaunchUi.Loading)
    }

    @Test
    fun `test when getLaunches retrieves launches from use case`() {
        coEvery { launchesUseCase.getLaunches() } returns LaunchUi.Failure
        runTest {
            viewModel.getLaunches()
        }

        coVerify { launchesUseCase.getLaunches() }
    }

    @Test
    fun `test error retrieving from use case displays failure`() {
        coEvery { launchesUseCase.getLaunches() } returns LaunchUi.Failure

        runTest {
            viewModel.getLaunches()
        }

        assert(viewModel.launches.value is LaunchUi.Failure)
    }

    @Test
    fun `test success retrieving from use case displays success`() {
        coEvery { launchesUseCase.getLaunches() } returns LaunchUi.Success(mockk())

        runTest {
            viewModel.getLaunches()
        }

        assert(viewModel.launches.value is LaunchUi.Success)
    }
}
