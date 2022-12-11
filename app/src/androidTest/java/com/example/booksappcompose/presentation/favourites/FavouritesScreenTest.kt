package com.example.booksappcompose.presentation.favourites

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booksappcompose.MainActivity
import com.example.booksappcompose.R
import com.example.booksappcompose.ui.theme.BooksAppComposeTheme
import com.example.booksappcompose.util.Screen
import com.example.booksappcompose.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalMaterialApi
@HiltAndroidTest
class FavouritesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController
    private lateinit var scaffoldState: ScaffoldState

    private lateinit var sort: String

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            navController = rememberNavController()
            sort = stringResource(id = R.string.sort)
            scaffoldState = rememberScaffoldState()
            BooksAppComposeTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Favourites.route
                ) {
                    composable(route = Screen.Favourites.route) {
                        FavouritesScreen(scaffoldState = scaffoldState)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.apply {
            onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
            onNodeWithContentDescription(sort).performClick()
            onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
        }
    }

    @Test
    fun radioButtonsAscendingTitle_PerformClick() {
       composeRule.apply {
           onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
           onNodeWithContentDescription(sort).performClick()
           onNodeWithTag(TestTags.ASCENDING_RADIO_BUTTON).performClick()
           onNodeWithTag(TestTags.TITLE_RADIO_BUTTON).performClick()
           onNodeWithContentDescription(sort).performClick()
       }
    }
}