package ir.developer.todo_compose.navigation

import androidx.navigation.NavHostController
import ir.developer.todo_compose.util.Action
import ir.developer.todo_compose.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {
//    val splash: () -> Unit = {
//        navController.navigate(route = "list/${Action.NO_ACTION}") {
//            popUpTo(SPLASH_SCREEN) { inclusive = true }
//        }
//    }
    val list: (Int) -> Unit = { taskId ->
        navController.navigate("task/${taskId}")
    }
    val task: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
}