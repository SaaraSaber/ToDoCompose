package ir.developer.todo_compose.ui.theme.screen.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ir.developer.todo_compose.util.RequestState
import ir.developer.todo_compose.util.SearchAppBarState


@Composable
fun ListContent(
    modifier: Modifier,
    allTasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {

    if (sortState is RequestState.Success) {

        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandlerListContent(
                        modifier = modifier,
                        tasks = searchedTasks.data,
                        navigationToTaskScreen = navigationToTaskScreen
                    )
                }
            }

            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandlerListContent(
                        modifier = modifier,
                        tasks = allTasks.data,
                        navigationToTaskScreen = navigationToTaskScreen
                    )
                }
            }

            sortState.data == Priority.LOW -> {
                HandlerListContent(
                    modifier = modifier,
                    tasks = lowPriorityTasks,
                    navigationToTaskScreen = navigationToTaskScreen
                )
            }

            sortState.data == Priority.HIGH -> {
                HandlerListContent(
                    modifier = modifier,
                    tasks = highPriorityTasks,
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
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            modifier = modifier,
            tasks = tasks,
            navigationToTaskScreen = navigationToTaskScreen
        )
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DisplayTasks(
    modifier: Modifier,
    tasks: List<ToDoTask>,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = tasks,
            key = { task ->
                task.id
            }) { task ->

            TaskItem(toDoTask = task, navigationToTaskScreen = navigationToTaskScreen)
//            val state = remember {
//                AnchoredDraggableState(
//                    // 2
//                    initialValue = DragAnchors.Start,
//                    // 3
//                    positionalThreshold = { distance: Float -> distance * 0.5f },
//                    // 4
//                    velocityThreshold = { with(density) { 100.dp.toPx() } },
//                    // 5
//                    animationSpec = tween(),
//                ).apply {
//                    // 6
//                    updateAnchors(
//                        // 7
//                        DraggableAnchors {
//                            DragAnchors.Start at 0f
//                            DragAnchors.End at 400f
//                        }
//                    )
//                }
//            }
//
//
//            Modifier.anchoredDraggable(state =state)
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
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


@OptIn(ExperimentalMaterial3Api::class)
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