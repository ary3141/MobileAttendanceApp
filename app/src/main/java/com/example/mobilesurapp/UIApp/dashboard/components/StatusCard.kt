package com.example.mobilesurapp.UIApp.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme

@Composable
fun StatusCard(
    cameraOnline: Boolean = true,
    serverOnline: Boolean = true,
    databaseOnline: Boolean = true,
    modifier: Modifier = Modifier
) {

    val allOnline = cameraOnline && serverOnline && databaseOnline

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {

        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "System Status",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = if (allOnline)
                            "All systems operational"
                        else
                            "Some services unavailable",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                StatusChip(allOnline)

            }

            Spacer(Modifier.height(20.dp))

            HorizontalDivider()

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                StatusItem(
                    modifier = Modifier.weight(1f),
                    title = "Camera",
                    online = cameraOnline
                )

                VerticalDivider(
                    modifier = Modifier.height(72.dp)
                )

                StatusItem(
                    modifier = Modifier.weight(1f),
                    title = "Server",
                    online = serverOnline
                )

                VerticalDivider(
                    modifier = Modifier.height(72.dp)
                )

                StatusItem(
                    modifier = Modifier.weight(1f),
                    title = "Database",
                    online = databaseOnline
                )
            }
        }
    }
}

@Composable
private fun StatusChip(
    online: Boolean
) {

    Surface(
        shape = RoundedCornerShape(50),
        color = if (online)
            Color(0xFFE8F8F1)
        else
            Color(0xFFFFECEC)
    ) {

        Text(
            text = if (online) "Online" else "Offline",
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 8.dp
            ),
            color = if (online)
                Color(0xFF26A269)
            else
                Color(0xFFD32F2F),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun StatusItem(
    modifier: Modifier = Modifier,
    title: String,
    online: Boolean
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = if (online) "online" else "offline",
            style = MaterialTheme.typography.bodyLarge,
            color = if (online)
                Color(0xFF26A269)
            else
                Color(0xFFD32F2F)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StatusCardPreview() {
    MobileSurAppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            StatusCard()
        }
    }
}