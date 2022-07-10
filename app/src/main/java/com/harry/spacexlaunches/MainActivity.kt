package com.harry.spacexlaunches

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.harry.spacexlaunches.launches.LaunchesViewModel
import com.harry.spacexlaunches.launches.ui.LaunchesScreen
import com.harry.spacexlaunches.ui.theme.SpaceXLaunchesTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launchesViewModel: LaunchesViewModel by viewModel()

        setContent {
            SpaceXLaunchesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    launchesViewModel.getLaunches()
                    LaunchesScreen(launchesViewModel.launches) {
                        launchesViewModel.getLaunches()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceXLaunchesTheme {

    }
}