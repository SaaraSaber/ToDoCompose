@file:OptIn(ExperimentalAnimationApi::class)

package ir.developer.todo_compose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ir.developer.todo_compose.navigation.destination.listComposable
import ir.developer.todo_compose.navigation.destination.splashComposable
import ir.developer.todo_compose.navigation.destination.taskComposable
import ir.developer.todo_compose.ui.theme.viewmodel.SharedViewModel
import ir.developer.todo_compose.util.Constants.SPLASH_SCREEN

@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(
            navigateToListScreen = screen.splash
        )
        listComposable(
            navigateToTaskScreen = screen.list,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
    }
}