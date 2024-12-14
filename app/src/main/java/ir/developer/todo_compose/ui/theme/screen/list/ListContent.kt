package ir.developer.todo_compose.ui.theme.screen.list

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ir.developer.todo_compose.data.models.Priority
import ir.developer.todo_compose.data.models.ToDoTask
import ir.developer.todo_compose.ui.theme.HighPriorityColor
import ir.developer.todo_compose.ui.theme.LARGEST_PADDING
import ir.developer.todo_compose.ui.theme.LARGE_PADDING
import ir.developer.todo_compose.ui.theme.PRIORITY_INDICATOR_SIZE
import ir.developer.todo_compose.ui.theme.TASK_ITEM_ELEVATION
import ir.developer.todo_compose.ui.theme.taskItemBackgroundColor
import ir.developer.todo_compose.ui.theme.taskItemTextColor
import ir.developer.todo_compose.util.Action
import ir.developer.todo_compose.util.RequestState
import ir.developer.todo_compose.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ListContent(
    modifier: Modifier,
    allTasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {

    if (sortState is RequestState.Success) {

        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandlerListContent(
                        modifier = modifier,
                        tasks = searchedTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigationToTaskScreen = navigationToTaskScreen
                    )
                }
            }

            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandlerListContent(
                        modifier = modifier,
                        tasks = allTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigationToTaskScreen = navigationToTaskScreen
                    )
                }
            }

            sortState.data == Priority.LOW -> {
                HandlerListContent(
                    modifier = modifier,
                    tasks = lowPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigationToTaskScreen = navigationToTaskScreen
                )
            }

            sortState.data == Priority.HIGH -> {
                HandlerListContent(
                    modifier = modifier,
                    tasks = highPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigationToTaskScreen = navigationToTaskScreen
                )
            }
        }

    }

}

@Composable
fun HandlerListContent(
    modifier: Modifier,
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            modifier = modifier,
            tasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigationToTaskScreen = navigationToTaskScreen
        )
    }
}

@Composable
fun DisplayTasks(
    modifier: Modifier,
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = tasks,
            key = { task ->
                task.id
            }) { task ->

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    when (it) {
                        SwipeToDismissBoxValue.EndToStart -> {
                            scope.launch{
                                delay(timeMillis = 300)
                                onSwipeToDelete(Action.DELETE, task)
                            }
                        }

                        else -> {
                            Log.i("DisplayTasks", "DisplayTasks: else")
                        }
                    }
                    return@rememberSwipeToDismissBoxState true
                },
                positionalThreshold = { it * .30f }
            )

            val degrees by animateFloatAsState(
                targetValue =
                if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) -45f else 0f,
                label = ""
            )
            var itemAppeared by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = { RedBackground(degrees = degrees) },
                    enableDismissFromStartToEnd = false,
                    enableDismissFromEndToStart = true,
                    content = {
                        TaskItem(toDoTask = task, navigationToTaskScreen = navigationToTaskScreen)
                    }
                )
            }

        }
    }
}
 @Composable
 private fun s (onSwipeToDelete: (Action, ToDoTask) -> Unit){


 }

@Composable
fun RedBackground(
    degrees: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = "delete icon",
            tint = Color.White
        )
    }
}

@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.taskItemBackgroundColor,
        shape = RectangleShape,
        shadowElevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigationToTaskScreen(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    color = MaterialTheme.colorScheme.taskItemTextColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = toDoTask.priority.color
                        )
                    }

                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                color = MaterialTheme.colorScheme.taskItemTextColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@Composable
@Preview
private fun listContentPreview() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 0,
            title = "Title",
            description = "some random text",
            priority = Priority.MEDIUM
        ), navigationToTaskScreen = {}
    )
}