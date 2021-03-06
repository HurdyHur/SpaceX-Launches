package com.harry.spacexlaunches.launches.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.harry.spacexlaunches.R
import com.harry.spacexlaunches.launches.model.LaunchItem
import com.harry.spacexlaunches.launches.model.LaunchUi
import com.harry.spacexlaunches.ui.theme.SpaceXLaunchesTheme
import java.util.*

@Composable
fun LaunchesScreen(launches: LiveData<LaunchUi>, onRetryClicked: () -> Unit) {
    val launchList = launches.observeAsState().value
    Column {
        TopAppBar {
            Text(
                text = stringResource(id = R.string.launch_title),
                style = TextStyle(fontSize = dimensionResource(id = R.dimen.top_bar_text_size).value.sp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))
            )
        }

        when (launchList) {
            is LaunchUi.Success -> LaunchList(launches = launchList.launches)
            is LaunchUi.Failure -> FailedView(onRetryClicked)
            else -> LoadingView()
        }
    }
}

@Composable
fun FailedView(onRetryClicked: () -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (errorMessage, retryButton) = createRefs()

        Text(
            text = stringResource(id = R.string.error_message),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .constrainAs(errorMessage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })

        Button(onClick = onRetryClicked, modifier = Modifier.constrainAs(retryButton) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(errorMessage.bottom)
        }) {
            Text(text = stringResource(id = R.string.retry_button))
        }
    }
}

@Composable
fun LoadingView() {
    Box(contentAlignment = Center, modifier = Modifier.fillMaxSize()) {
        val spinnerContentDescription = stringResource(id = R.string.content_description_loading_spinner)
        CircularProgressIndicator(Modifier.semantics {
            contentDescription = spinnerContentDescription
        })
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
        ImageRequest.Builder(LocalContext.current)
            .data(launch.patchImageUrl)
            .fallback(R.drawable.fallback_badge)
            .error(R.drawable.fallback_badge)
            .build()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {

        ConstraintLayout(Modifier.padding(8.dp)) {
            val (badge, name, launchDate, missionSuccess) = createRefs()
            AsyncImage(model = request, contentDescription = "patch for ${launch.name}",
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .padding(dimensionResource(id = R.dimen.default_padding))
                    .constrainAs(badge) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })


            Text(text = launch.name,
                style = TextStyle(
                    fontSize =
                    dimensionResource(id = R.dimen.launch_item_title_text_size).value.sp
                ),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .padding(dimensionResource(id = R.dimen.default_padding))
                    .constrainAs(name) {
                        start.linkTo(badge.end)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    })

            Text(text = stringResource(id = R.string.launch_item_date, launch.launchDate),
                Modifier
                    .fillMaxWidth(0.65f)
                    .padding(dimensionResource(id = R.dimen.default_padding))
                    .constrainAs(launchDate) {
                        start.linkTo(badge.end)
                        top.linkTo(name.bottom)
                        end.linkTo(parent.end)
                    })
            Text(text = stringResource(
                id = R.string.launch_item_mission_success,
                launch.missionSuccessful
            ),
                Modifier
                    .fillMaxWidth(0.65f)
                    .padding(dimensionResource(id = R.dimen.default_padding))
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

        LaunchList(launches = launches)
    }
}
