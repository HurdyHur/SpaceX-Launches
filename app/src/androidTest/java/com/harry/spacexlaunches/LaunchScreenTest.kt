package com.harry.spacexlaunches

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.harry.spacexlaunches.launches.model.LaunchItem
import com.harry.spacexlaunches.launches.model.LaunchUi
import com.harry.spacexlaunches.launches.ui.LaunchesScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingSpinnerDisplayed() {
        composeTestRule.setContent {
            LaunchesScreen(launches = MutableLiveData(LaunchUi.Loading)) { }
        }

        composeTestRule.onNodeWithContentDescription("Loading").assertIsDisplayed()
    }

    @Test
    fun testFailureIsDisplayed() {
        composeTestRule.setContent {
            LaunchesScreen(launches = MutableLiveData(LaunchUi.Failure)) { }
        }

        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun testSuccessShowsListOfLaunches() {
        composeTestRule.setContent {
            val launchItem: LaunchItem = LaunchItem(name = "Test Item", "Date", "Mission Success", "")

            LaunchesScreen(
                launches = MutableLiveData(
                    LaunchUi.Success(
                        listOf(
                            launchItem,
                            launchItem,
                            launchItem
                        )
                    )
                )
            ) { }
        }

        composeTestRule.onAllNodesWithText("Test Item").assertCountEquals(3)
    }
}
