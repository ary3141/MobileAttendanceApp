package com.example.mobilesurapp.UIApp.dashboard.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme

@Composable
fun DashboardPanel(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            content = content
        )
    }
}

@Preview(
    name = "Dashboard Panel",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DashboardPanelPreview() {

    MobileSurAppTheme {

        DashboardPanel(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Attendance Card"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Employee Card"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Settings Card"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "System Status"
            )

        }

    }

}