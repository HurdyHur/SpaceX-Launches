package com.harry.spacexlaunches.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harry.launch_repository.model.Launch
import com.harry.launch_repository.model.Launches
import com.harry.spacexlaunches.ui.model.LaunchItem
import com.harry.spacexlaunches.ui.model.LaunchUi
import com.harry.spacexlaunches.ui.theme.SpaceXLaunchesTheme

@Composable
fun LaunchesScreen(launches: LaunchUi) {
    when(launches) {
        is LaunchUi.Success -> LaunchList(launches = launches.launches)
        is LaunchUi.Failure -> {}
        is LaunchUi.Loading -> CircularProgressIndicator()
    }
}

@Composable
fun LaunchList(launches: List<LaunchItem>) {
    LazyColumn {
        items(launches) { launch ->
            LaunchItem(launch)
        }
    }
}

@Composable
fun LaunchItem(launch: LaunchItem) {
    Card(modifier = Modifier.padding(8.dp), shape = RoundedCornerShape(8.dp), backgroundColor = MaterialTheme.colors.surface) {

        Column {
            Text(text = launch.name)
            Text(text = launch.launchDate)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LaunchesScreenPreview() {
    SpaceXLaunchesTheme {
        val launch = LaunchItem(name = "Rocket Name",
            launchDate = "2022-02-10",
            missionSuccessful = true,
            patchImageUrl = ""
        )
        val launches = listOf(launch, launch, launch, launch)

        LaunchList(launches = launches)
    }
}
