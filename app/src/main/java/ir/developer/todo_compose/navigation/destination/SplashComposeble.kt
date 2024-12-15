//package ir.developer.todo_compose.navigation.destination
//
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.slideOutVertically
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.composable
//import ir.developer.todo_compose.ui.theme.screen.splash.SplashScreen
//import ir.developer.todo_compose.util.Constants.SPLASH_SCREEN
//
//fun NavGraphBuilder.splashComposable(
//    navigateToListScreen: () -> Unit
//) {
//    composable(
//        route = SPLASH_SCREEN,
//        exitTransition = {
//            slideOutVertically(
//                targetOffsetY = { -it },
//                animationSpec = tween(300)
//            )
//        }
//    ) {
//        SplashScreen(navigateToListScreen)
//    }
//}