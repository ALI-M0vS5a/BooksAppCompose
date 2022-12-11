package com.example.booksappcompose.presentation

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.booksappcompose.MainActivity
import com.example.booksappcompose.R
import com.example.booksappcompose.presentation.components.BottomSheetContent
import com.example.booksappcompose.presentation.components.NavigationBar
import com.example.booksappcompose.ui.theme.BooksAppComposeTheme
import com.example.booksappcompose.util.Navigation
import com.example.booksappcompose.util.Screen
import com.example.booksappcompose.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import kotlin.concurrent.schedule


@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@HiltAndroidTest
class BooksEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()



    private lateinit var search: String
    private lateinit var searchBackArrow: String
    private lateinit var favouriteScreen: String
    private lateinit var alreadySaved: String
    private lateinit var recommended: String
    private lateinit var saveToLibraryText: String


    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            BooksAppComposeTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val route = navBackStackEntry?.destination?.route
                val isFullScreen = Screen.isFullScreen(route)
                val scaffoldState = rememberScaffoldState()
                val sheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    confirmStateChange = {
                        it != ModalBottomSheetValue.HalfExpanded
                    }
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ModalBottomSheetLayout(
                        sheetState = sheetState,
                        sheetContent = {
                            BottomSheetContent(
                                bottomSheetState = sheetState,
                                scaffoldState = scaffoldState,
                                navController = navController
                            )
                        },
                        sheetShape = RoundedCornerShape(25.dp),
                        sheetBackgroundColor = Color(0xFFE5C69B),
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Scaffold(
                            bottomBar = {
                                if (!isFullScreen) {
                                    if (route != null) {
                                        NavigationBar(route) { target ->
                                            navController.apply {
                                                if (target == Screen.Home.route) {
                                                    navigate("$target/2022/3/false") {
                                                        restoreState = true
                                                        launchSingleTop = true
                                                        popUpTo(route = Screen.Home.route + "/2022/3/false") {
                                                            saveState = true
                                                        }
                                                    }
                                                } else {
                                                    navigate(target) {
                                                        restoreState = true
                                                        launchSingleTop = true
                                                        popUpTo(route = Screen.Home.route + "/2022/3/false") {
                                                            saveState = true
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            floatingActionButton = {
                                if (!isFullScreen) {
                                    FloatingActionButton(
                                        onClick = { /*TODO*/ },
                                        shape = CircleShape,
                                        backgroundColor = Color(0xFFE5C69B),
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .offset(
                                                x = 0.dp,
                                                y = 10.dp
                                            )
                                            .testTag("Floating action button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.ShoppingCart,
                                            contentDescription = stringResource(id = R.string.shopping),
                                            tint = Color.White
                                        )
                                    }
                                }
                            },
                            floatingActionButtonPosition = FabPosition.Center,
                            isFloatingActionButtonDocked = true,
                            scaffoldState = scaffoldState
                        ) {
                            Navigation(
                                navController = navController,
                                paddingValues = it,
                                scaffoldState = scaffoldState,
                                sheetState = sheetState
                            )
                        }
                    }
                }
            }

            search = stringResource(id = R.string.search)
            searchBackArrow = stringResource(id = R.string.arrow_back)
            favouriteScreen = stringResource(id = R.string.favourites_screen)
            alreadySaved = stringResource(id = R.string.this_book_already_saved)
            recommended = stringResource(id = R.string.recommended)
            saveToLibraryText = stringResource(id = R.string.save_to_library)


        }
    }


    @Test
    fun searchForBooks_saveBook_ViewBookInLibrary_UnSaveBook() {
        composeRule.apply {
            onNodeWithTag(TestTags.GET_STARTED).performClick()

            onNodeWithContentDescription(search).performClick()


            onNodeWithTag(TestTags.SEARCH_TEXT_FIELD).performTextInput("The Alchemist")
            asyncTimer()
            onNodeWithText("The Strange Case of the Alchemist's Daughter").assertIsDisplayed()
            onNodeWithText("The Strange Case of the Alchemist's Daughter").performClick()
            asyncTimer()
            onNodeWithText(saveToLibraryText).assertIsDisplayed()
            onNodeWithTag(TestTags.SAVE_BOOK).performClick()
            asyncTimer()
            onNodeWithContentDescription(searchBackArrow).performClick()

            onNodeWithText(recommended).assertIsDisplayed()
            onNodeWithContentDescription(favouriteScreen).performClick()

            onNodeWithText("The Strange Case of the Alchemist's Daughter (The Extraordinary Adventures of the Athena Club, #1)").assertIsDisplayed()
            onAllNodesWithTag(TestTags.SAVED_BOOK_UNSAVED)[3].performClick()
        }
    }

    private fun asyncTimer (delay: Long = 10000) {
        AsyncTimer.start (delay)
        composeRule.waitUntil (
            condition = {AsyncTimer.expired},
            timeoutMillis = delay + 1000
        )
    }

    object AsyncTimer {
        var expired = false
        fun start(delay: Long = 1000){
            expired = false
            Timer().schedule(delay) {
                expired = true
            }
        }
    }
}