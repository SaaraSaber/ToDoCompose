package ir.developer.todo_compose.data.models

import androidx.compose.ui.graphics.Color
import ir.developer.todo_compose.ui.theme.HighPriorityColor
import ir.developer.todo_compose.ui.theme.LowPriorityColor
import ir.developer.todo_compose.ui.theme.MediumPriorityColor
import ir.developer.todo_compose.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}