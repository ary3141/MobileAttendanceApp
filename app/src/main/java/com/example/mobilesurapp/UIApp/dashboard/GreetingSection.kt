package com.example.mobilesurapp.UIApp.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme

@Composable
fun GreetingSection(
    greeting: String = "Good Morning,",
    username: String = "Admin 👋",
    role: String = "Administrator",
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(horizontal = 24.dp)
    ) {
        Text(
            text = greeting,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.85f)
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = username,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = role,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.75f)
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF5B3DF5
)
@Composable
private fun GreetingSectionPreview() {
    MobileSurAppTheme {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5B3DF5))
                .padding(vertical = 48.dp)
        ) {
            GreetingSection()
        }

    }
}