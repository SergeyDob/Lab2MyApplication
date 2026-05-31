package ua.onpu.martynuk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import ua.onpu.martynuk.model.Habit

class HabitViewModel : ViewModel() {
    private val initialHabits = listOf(
        Habit(id = 1, icon = "🏃", title = "Пробіжка", isDone = true),
        Habit(id = 2, icon = "💧", title = "Випити 2л води", isDone = true),
        Habit(id = 3, icon = "📖", title = "Читання 20 хв", isDone = true),
        Habit(id = 4, icon = "🧘", title = "Медитація", isDone = true),
        Habit(id = 5, icon = "✏️", title = "Щоденник"),
        Habit(id = 6, icon = "🎸", title = "Гітара 15 хв")
    )

    private val _habits = MutableStateFlow(initialHabits)
    val habits: StateFlow<List<Habit>> = _habits.asStateFlow()

    val completedCount: StateFlow<Int> = habits
        .map { list -> list.count { h -> h.isDone } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = initialHabits.count { it.isDone }
        )

    fun toggleHabit(habitId: Int) {
        _habits.update { list ->
            list.map { habit ->
                if (habit.id == habitId) {
                    habit.copy(isDone = !habit.isDone)
                } else {
                    habit
                }
            }
        }
    }

    fun resetDay() {
        _habits.value = initialHabits.map { habit ->
            habit.copy(isDone = false)
        }
    }
}
