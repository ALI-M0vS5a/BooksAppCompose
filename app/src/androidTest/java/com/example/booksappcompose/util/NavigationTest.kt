package com.example.booksappcompose.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalMaterialApi
class NavigationTest {


    @get:Rule
    val composeTestRule = createComposeRule()


    lateinit var navController: TestNavHostController
    private lateinit var scaffoldState: ScaffoldState
    private lateinit var sheetState: ModalBottomSheetState
    private lateinit var paddingValues: PaddingValues

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            sheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                confirmStateChange = {
                    it != ModalBottomSheetValue.HalfExpanded
                }
            )
            scaffoldState = rememberScaffoldState()
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            paddingValues = PaddingValues()
            Navigation(
                navController = navController,
                paddingValues = paddingValues,
                scaffoldState = scaffoldState,
                sheetState = sheetState
            )
        }
    }


    @Test
    fun navigation_verifyStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Home page cover")
            .assertIsDisplayed()
    }

    @Test
    fun clickGetStartedButton_navigateToHomeScreen() {
        composeTestRule
            .onNodeWithTag(TestTags.GET_STARTED)
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route

        Truth.assertThat(route).isEqualTo("ROUTE_HOME/{year}/{month}/{shouldFetchFromRemote}")

    }

}