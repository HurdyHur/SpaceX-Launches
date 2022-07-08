package com.harry.spacexlaunches.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.harry.spacexlaunches.ui.model.LaunchItem
import com.harry.spacexlaunches.ui.model.LaunchUi
import com.harry.spacexlaunches.ui.theme.SpaceXLaunchesTheme
import java.util.*

@Composable
fun LaunchesScreen(launches: LaunchUi) {
    Column {
        TopAppBar {
            Text(text = "Falcon 9 Launches")
        }

        when (launches) {
            is LaunchUi.Success -> LaunchList(launches = launches.launches)
            is LaunchUi.Failure -> {}
            is LaunchUi.Loading -> CircularProgressIndicator()
        }
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
    val request =
        ImageRequest.Builder(LocalContext.current).data(launch.patchImageUrl).build()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {

        ConstraintLayout {
            val (badge, name, launchDate, missionSuccess) = createRefs()
            AsyncImage(model = request, contentDescription = "patch for ${launch.name}",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.2f)
                    .constrainAs(badge) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })


            Text(text = launch.name,
                Modifier
                    .padding(8.dp)
                    .constrainAs(name) {
                        start.linkTo(badge.end)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    })

            Text(text = launch.launchDate.toString(),
                Modifier
                    .padding(8.dp)
                    .constrainAs(launchDate) {
                        start.linkTo(badge.end)
                        top.linkTo(name.bottom)
                        end.linkTo(parent.end)
                    })
            Text(text = launch.missionSuccessful,
                Modifier
                    .padding(8.dp)
                    .constrainAs(missionSuccess) {
                        start.linkTo(badge.end)
                        top.linkTo(launchDate.bottom)
                        end.linkTo(parent.end)
                    })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LaunchesScreenPreview() {
    SpaceXLaunchesTheme {
        val launch = LaunchItem(
            name = "Rocket Name",
            launchDate = Date().toString(),
            missionSuccessful = "\u2715",
            patchImageUrl = ""
        )
        val launches = listOf(launch, launch, launch, launch)

        LaunchesScreen(launches = LaunchUi.Success(launches))
    }
}
