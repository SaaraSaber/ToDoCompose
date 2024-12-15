@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package ir.developer.todo_compose.ui.theme.screen.task

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ir.developer.todo_compose.data.models.Priority
import ir.developer.todo_compose.data.models.ToDoTask
import ir.developer.todo_compose.ui.theme.viewmodel.SharedViewModel
import ir.developer.todo_compose.util.Action

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel,
    navigationToListScreen: (Action) -> Unit
) {

    val title: String = sharedViewModel.title
    val description: String = sharedViewModel.description
    val priority: Priority = sharedViewModel.priority
    val context = LocalContext.current

    BackHandler {
        navigationToListScreen(Action.NO_ACTION)
    }

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigationToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigationToListScreen(action)
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigationToListScreen(action)
                        } else {
                            displayToast(context = context)
                        }
                    }

                }
            )
        },
        content = {
            TaskContent(
                modifier = Modifier.padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ),
                title = title,
                onTitleChange = { title ->
                    sharedViewModel.updateTitle(title)
                },
                description = description,
                onDescriptionChange = { description ->
                    sharedViewModel.updateDescription(newDescription = description)
                },
                priority = priority,
                onPriorityChange = { priority ->
                    sharedViewModel.updatePriority(newPriority = priority)
                }
            )
        }
    )
}

fun displayToast(context: Context) {
    Toast.makeText(context, "Fields Empty", Toast.LENGTH_SHORT).show()
}