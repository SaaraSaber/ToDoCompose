package ir.developer.todo_compose.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    if (openDialog) {
        AlertDialog(title = {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        },
            text = {
                Text(text = message, fontSize = 15.sp, fontWeight = FontWeight.Normal)
            },
            onDismissRequest = { closeDialog() },
            confirmButton = {
                Button(onClick = {
                    onYesClicked()
                    closeDialog()
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    closeDialog()
                }) {
                    Text(text = "No")
                }
            }
        )
    }
}