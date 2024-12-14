package ir.developer.todo_compose.navigation.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.developer.todo_compose.ui.theme.screen.list.ListScreen
import ir.developer.todo_compose.ui.theme.viewmodel.SharedViewModel
import ir.developer.todo_compose.util.Constants.LIST_ARGUMENT_KEY
import ir.developer.todo_compose.util.Constants.LIST_SCREEN
import ir.developer.todo_compose.util.toAction

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit, sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackScreen ->

        val action = navBackStackScreen.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = action) {
            sharedViewModel.action.value = action
        }

        val dataBaseAction by sharedViewModel.action

        ListScreen(
            action = dataBaseAction,
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}