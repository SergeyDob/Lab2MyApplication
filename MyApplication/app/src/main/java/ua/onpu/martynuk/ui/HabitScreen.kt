package ua.onpu.martynuk.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ua.onpu.martynuk.model.Habit
import ua.onpu.martynuk.ui.theme.MyApplicationTheme
import ua.onpu.martynuk.viewmodel.HabitViewModel

private val ScreenBackground = Color(0xFFF2F2F8)
private val AccentGreen = Color(0xFF31C85C)
private val ResetRed = Color(0xFFFF3B35)
private val HeaderText = Color(0xFF090909)
private val CounterText = Color(0xFF96969E)

@Composable
fun HabitScreen(
    modifier: Modifier = Modifier,
    viewModel: HabitViewModel = viewModel()
) {
    val habits by viewModel.habits.collectAsState()
    val completedCount by viewModel.completedCount.collectAsState()

    HabitContent(
        habits = habits,
        completedCount = completedCount,
        onHabitClick = viewModel::toggleHabit,
        onResetClick = viewModel::resetDay,
        modifier = modifier
    )
}

@Composable
private fun HabitContent(
    habits: List<Habit>,
    completedCount: Int,
    onHabitClick: (Int) -> Unit,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(start = 34.dp, top = 30.dp, end = 34.dp, bottom = 26.dp)
        ) {
            Text(
                text = "🌱 Звички",
                color = HeaderText,
                fontSize = 32.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = buildAnnotatedString {
                    append("Виконано сьогодні: ")
                    withStyle(
                        SpanStyle(
                            color = AccentGreen,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("$completedCount з ${habits.size}")
                    }
                },
                color = CounterText,
                fontSize = 23.sp,
                lineHeight = 28.sp
            )
        }

        HorizontalDivider(color = Color(0xFFE0E1EA))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = habits,
                key = { habit -> habit.id }
            ) { habit ->
                HabitCard(
                    habit = habit,
                    onToggle = { onHabitClick(habit.id) }
                )
            }
        }

        Button(
            onClick = onResetClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, bottom = 26.dp)
                .height(58.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ResetRed,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(horizontal = 18.dp)
        ) {
            Text(
                text = "Скинути день",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HabitScreenPreview() {
    MyApplicationTheme(dynamicColor = false) {
        HabitContent(
            habits = listOf(
                Habit(id = 1, icon = "🏃", title = "Пробіжка", isDone = true),
                Habit(id = 2, icon = "💧", title = "Випити 2л води", isDone = true),
                Habit(id = 3, icon = "📖", title = "Читання 20 хв", isDone = true),
                Habit(id = 4, icon = "🧘", title = "Медитація", isDone = true),
                Habit(id = 5, icon = "✏️", title = "Щоденник"),
                Habit(id = 6, icon = "🎸", title = "Гітара 15 хв")
            ),
            completedCount = 4,
            onHabitClick = {},
            onResetClick = {}
        )
    }
}
