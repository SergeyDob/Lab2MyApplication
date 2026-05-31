package ua.onpu.martynuk.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.onpu.martynuk.model.Habit

private val DoneGreen = Color(0xFF31C85C)
private val DonePill = Color(0xFFE8F8ED)
private val MissedPill = Color(0xFFF0F0F6)
private val BorderGray = Color(0xFFE1E2EA)
private val MutedGray = Color(0xFF9A9AA4)

@Composable
fun HabitCard(
    habit: Habit,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 104.dp)
            .clickable(role = Role.Checkbox, onClick = onToggle),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, BorderGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompletionCircle(isDone = habit.isDone)
            Spacer(modifier = Modifier.width(22.dp))
            Text(
                text = "${habit.icon} ${habit.title}",
                modifier = Modifier.weight(1f),
                color = Color(0xFF070707),
                fontSize = 27.sp,
                lineHeight = 33.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatusBadge(isDone = habit.isDone)
        }
    }
}

@Composable
private fun CompletionCircle(isDone: Boolean) {
    val background = if (isDone) DoneGreen else Color.Transparent
    val border = if (isDone) Color.Transparent else Color(0xFFD1D2DA)

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(background)
            .border(4.dp, border, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (isDone) {
            Text(
                text = "✓",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun StatusBadge(isDone: Boolean) {
    val background = if (isDone) DonePill else MissedPill
    val textColor = if (isDone) Color(0xFF007A31) else MutedGray
    val text = if (isDone) "✓ готово" else "пропущено"

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(background)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
