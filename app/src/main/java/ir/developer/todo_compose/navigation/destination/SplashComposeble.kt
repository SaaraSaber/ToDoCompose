package ir.developer.todo_compose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ir.developer.todo_compose.ui.theme.screen.splash.SplashScreen
import ir.developer.todo_compose.util.Constants.SPLASH_SCREEN

fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN
    ) {
        SplashScreen(navigateToListScreen)
    }
}