package ir.developer.todo_compose.ui.theme.screen.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.developer.todo_compose.R
import ir.developer.todo_compose.components.DisplayAlertDialog
import ir.developer.todo_compose.components.PriorityItem
import ir.developer.todo_compose.data.models.Priority
import ir.developer.todo_compose.ui.theme.LARGE_PADDING
import ir.developer.todo_compose.ui.theme.TOP_APP_BAR_HEIGHT
import ir.developer.todo_compose.ui.theme.topAppBarBackgroundColor
import ir.developer.todo_compose.ui.theme.topAppBarContentColor
import ir.developer.todo_compose.ui.theme.viewmodel.SharedViewModel
import ir.developer.todo_compose.util.Action
import ir.developer.todo_compose.util.SearchAppBarState

@ExperimentalMaterial3Api
@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = { sharedViewModel.persistSortState(it) },
                onDeleteAllConfirmed = {
                    sharedViewModel.action.value = Action.DELETE_ALL
                }
            )
        }

        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { newText ->
                    sharedViewModel.searchTextState.value = newText
                },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {
                    sharedViewModel.searchDatabase(searchQuery = it)
                }
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirmed: () -> Unit

) {
    TopAppBar(
        title = {
            Text(
                text = "Task",
                color = MaterialTheme.colorScheme.topAppBarContentColor
            )
        },
        actions = {
            ListAppBarAction(onSearchClicked, onSortClicked, onDeleteAllConfirmed)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor
        )
    )
}


@Composable
fun ListAppBarAction(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }

    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_all_tasks),
        message = stringResource(id = R.string.delete_all_task_confirmation),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = {
            onDeleteAllConfirmed()
        })

    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteAllConfirmed = {
        openDialog = true
    })
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = { onSearchClicked() }) {

        Icon(
            imageVector = Icons.Filled.Search, contentDescription = "Search Task",
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )

    }
}


@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.filter_list),
            contentDescription = "filter_list",
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            Priority.values().slice(setOf(0, 2, 3)).forEach { priority ->
                DropdownMenuItem(
                    text = { PriorityItem(priority = priority) },
                    onClick = {
                        expanded = false
                        onSortClicked(priority)
                    }
                )
            }
        }
    }

}


@Composable
fun DeleteAllAction(onDeleteAllConfirmed: () -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.vertival_menu),
            contentDescription = "filter_list",
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.delete_all),
                        modifier = Modifier.padding(start = LARGE_PADDING)
                    )
                },
                onClick = {
                    expanded = false
                    onDeleteAllConfirmed()
                }
            )

        }
    }
}


@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {


    Surface(
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.topAppBarBackgroundColor
    ) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(.8f), text = "Search",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.topAppBarContentColor,
                fontSize = 12.sp
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(modifier = Modifier.alpha(.5f), onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Search, contentDescription = "Search icon",
                        tint = MaterialTheme.colorScheme.topAppBarContentColor
                    )

                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    })
                {
                    Icon(
                        imageVector = Icons.Filled.Close, contentDescription = "Close icon",
                        tint = MaterialTheme.colorScheme.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldColors(
                focusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                errorTextColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Black,
                cursorColor = MaterialTheme.colorScheme.topAppBarContentColor,
                errorCursorColor = Color.Black,
                textSelectionColors = TextSelectionColors(Color.Black, Color.Black),
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Black,
                focusedLeadingIconColor = Color.Black,
                unfocusedLeadingIconColor = Color.Black,
                disabledLeadingIconColor = Color.Black,
                errorLeadingIconColor = Color.Black,
                focusedTrailingIconColor = Color.Black,
                unfocusedTrailingIconColor = Color.Black,
                disabledTrailingIconColor = Color.Black,
                errorTrailingIconColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                disabledLabelColor = Color.Black,
                errorLabelColor = Color.Black,
                focusedPlaceholderColor = Color.Black,
                unfocusedPlaceholderColor = Color.Black,
                disabledPlaceholderColor = Color.Black,
                errorPlaceholderColor = Color.Black,
                focusedSupportingTextColor = Color.Black,
                unfocusedSupportingTextColor = Color.Black,
                disabledSupportingTextColor = Color.Black,
                errorSupportingTextColor = Color.Black,
                focusedPrefixColor = Color.Black,
                unfocusedPrefixColor = Color.Black,
                disabledPrefixColor = Color.Black,
                errorPrefixColor = Color.Black,
                focusedSuffixColor = Color.Black,
                unfocusedSuffixColor = Color.Black,
                disabledSuffixColor = Color.Black,
                errorSuffixColor = Color.Black
            )
        )
    }
}


@ExperimentalMaterial3Api
@Composable
@Preview
private fun LstAppBarPreview() {
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteAllConfirmed = {})
}

@ExperimentalMaterial3Api
@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(text = "", onTextChange = {}, onCloseClicked = { }, onSearchClicked = {})
}