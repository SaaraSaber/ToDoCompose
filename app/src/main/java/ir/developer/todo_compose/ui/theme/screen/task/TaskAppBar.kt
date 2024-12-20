package ir.developer.todo_compose.ui.theme.screen.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ir.developer.todo_compose.R
import ir.developer.todo_compose.components.DisplayAlertDialog
import ir.developer.todo_compose.data.models.Priority
import ir.developer.todo_compose.data.models.ToDoTask
import ir.developer.todo_compose.ui.theme.topAppBarBackgroundColor
import ir.developer.todo_compose.ui.theme.topAppBarContentColor
import ir.developer.todo_compose.util.Action

@Composable
fun TaskAppBar(
    selectedTask: ToDoTask?,
    navigationToListScreen: (Action) -> Unit
) {
    if (selectedTask == null) {
        NewTaskAppBar(navigationToListScreen = navigationToListScreen)
    } else {
        ExistingTaskAppBar(
            selectedTask = selectedTask,
            navigationToListScreen = navigationToListScreen
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(
    navigationToListScreen: (Action) -> Unit
) {
    TopAppBar(navigationIcon = {
        BackAction(onBackAction = navigationToListScreen)
    },
        title = {
            Text(
                text = "Add Task",
                color = MaterialTheme.colorScheme.topAppBarContentColor
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor
        ),
        actions = {
            AddAction(onAddClick = navigationToListScreen)
        }
    )
}


@Composable
fun BackAction(
    onBackAction: (Action) -> Unit
) {
    IconButton(onClick = { onBackAction(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back Arrow",
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )

    }
}

@Composable
fun AddAction(
    onAddClick: (Action) -> Unit
) {
    IconButton(onClick = { onAddClick(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Add task",
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingTaskAppBar(
    selectedTask: ToDoTask,
    navigationToListScreen: (Action) -> Unit
) {
    TopAppBar(navigationIcon = {
        CloseAction(onCloseAction = navigationToListScreen)
    },
        title = {
            Text(
                text = selectedTask.title,
                color = MaterialTheme.colorScheme.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor
        ),
        actions = {
            ExistingTaskAppBarActions(
                selectedTask = selectedTask, navigationToListScreen = navigationToListScreen
            )
        }
    )
}

@Composable
fun CloseAction(
    onCloseAction: (Action) -> Unit
) {
    IconButton(onClick = { onCloseAction(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close Icon",
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )

    }
}

@Composable
fun ExistingTaskAppBarActions(
    selectedTask: ToDoTask,
    navigationToListScreen: (Action) -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }

    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_task, selectedTask.title),
        message = stringResource(id = R.string.delete_task_confirmation, selectedTask.title),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = {
            navigationToListScreen(Action.DELETE)
        }
    )

    DeleteAction(onDeleteClicked = {
        openDialog = true
    })
    UpdateAction(onUpdateClicked = navigationToListScreen)
}

@Composable
fun DeleteAction(
    onDeleteClicked: () -> Unit
) {
    IconButton(onClick = { onDeleteClicked() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Icon",
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )

    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(onClick = { onUpdateClicked(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Close Icon",
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )

    }
}

@Composable
@Preview
private fun TaskAppBarPreview() {
    NewTaskAppBar(navigationToListScreen = {})
}

@Composable
@Preview
private fun ExistingAppBarPreview() {
    ExistingTaskAppBar(selectedTask = ToDoTask(
        id = 0,
        title = "Check Email",
        description = "Some random text",
        priority = Priority.LOW
    ), navigationToListScreen = {})
}