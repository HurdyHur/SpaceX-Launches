package com.harry.spacexlaunches.di

import com.harry.launch_repository.LaunchRepository
import com.harry.spacexlaunches.launches.LaunchesUseCase
import com.harry.spacexlaunches.launches.LaunchesUseCaseImpl
import com.harry.spacexlaunches.launches.LaunchesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val launchesModule = module {

    single<LaunchRepository> { LaunchRepository.getLaunchRepository() }

    factory<LaunchesUseCase> { LaunchesUseCaseImpl(get()) }

    viewModel { LaunchesViewModel(get()) }

}


