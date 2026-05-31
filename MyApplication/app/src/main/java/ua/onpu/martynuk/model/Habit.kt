package ua.onpu.martynuk.model

data class Habit(
    val id: Int,
    val icon: String,
    val title: String,
    val isDone: Boolean = false
)
