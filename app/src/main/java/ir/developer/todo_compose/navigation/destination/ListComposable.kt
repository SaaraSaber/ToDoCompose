package ir.developer.todo_compose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ir.developer.todo_compose.ui.theme.screen.list.ListScreen
import ir.developer.todo_compose.ui.theme.viewmodel.SharedViewModel
import ir.developer.todo_compose.util.Constants.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit, sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
//        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
//            type = NavType.StringType
//        })
    ) {
        ListScreen(navigateToTaskScreen = navigateToTaskScreen, sharedViewModel)
    }
}