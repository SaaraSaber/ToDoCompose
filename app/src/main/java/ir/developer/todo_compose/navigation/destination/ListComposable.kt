package ir.developer.todo_compose.navigation.destination

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ir.developer.todo_compose.ui.theme.screen.list.ListScreen
import ir.developer.todo_compose.ui.theme.viewmodel.SharedViewModel
import ir.developer.todo_compose.util.Constants.LIST_ARGUMENT_KEY
import ir.developer.todo_compose.util.Constants.LIST_SCREEN
import ir.developer.todo_compose.util.toAction

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit, sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
//        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
//            type = NavType.StringType
//        })
    ) { navBackStackScreen ->

        val action = navBackStackScreen.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = action) {
            sharedViewModel.action.value = action
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}