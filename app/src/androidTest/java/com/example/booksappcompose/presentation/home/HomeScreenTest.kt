package com.example.booksappcompose.presentation.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso
import com.example.booksappcompose.MainActivity
import com.example.booksappcompose.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalMaterialApi
@HiltAndroidTest

class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
    }


    @Test
    fun firstScreen_HomeScreen() {
        composeRule.apply {
            onNodeWithTag(TestTags.GET_STARTED).performClick()
            onNodeWithContentDescription("Home screen").assertIsSelected()
        }
    }

    @Test
    fun navigationBar_backFromAnyDestination_returnsToHomeScreen() {
        composeRule.apply {
            onNodeWithTag(TestTags.GET_STARTED).performClick()
            onNodeWithContentDescription("Favourites screen").performClick()
            Espresso.pressBack()
            onNodeWithContentDescription("Home screen").assertExists()
        }
    }

    @Test
    fun navigationBar_multipleBackStackInterests() {
        composeRule.apply {
            onNodeWithTag(TestTags.GET_STARTED).performClick()
            onNodeWithText("Recommended").assertIsDisplayed()
            onNodeWithContentDescription("Favourites screen").performClick()
            onNodeWithContentDescription("Home screen").performClick()
            onNodeWithText("The Help").assertDoesNotExist()
        }
    }
}


