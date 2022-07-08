package com.harry.spacexlaunches

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.harry.spacexlaunches.ui.LaunchesViewModel
import com.harry.spacexlaunches.ui.model.LaunchUi
import com.harry.spacexlaunches.ui.screens.LaunchesScreen
import com.harry.spacexlaunches.ui.theme.SpaceXLaunchesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launchesViewModel by viewModels<LaunchesViewModel>()

        setContent {
            SpaceXLaunchesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    launchesViewModel.getLaunches()
                    LaunchesScreen(launchesViewModel.launches.observeAsState().value ?: LaunchUi.Loading
                    ) {
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