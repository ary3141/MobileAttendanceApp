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
import com.example.mobilesurapp.UIApp.dashboard.model.ConnectionState
import com.example.mobilesurapp.UIApp.dashboard.model.SystemStatus
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme

@Composable
fun StatusCard(
    status: SystemStatus,
    modifier: Modifier = Modifier
) {

    val allOnline =
        status.camera == ConnectionState.ONLINE &&
                status.server == ConnectionState.ONLINE &&
                status.firebase == ConnectionState.ONLINE

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

                StatusChip(
                    status = if (allOnline)
                        ConnectionState.ONLINE
                    else
                        ConnectionState.ERROR
                )

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
                    state = status.camera
                )

                VerticalDivider(
                    modifier = Modifier.height(72.dp)
                )

                StatusItem(
                    modifier = Modifier.weight(1f),
                    title = "Server",
                    state = status.server
                )

                VerticalDivider(
                    modifier = Modifier.height(72.dp)
                )

                StatusItem(
                    modifier = Modifier.weight(1f),
                    title = "Firebase",
                    state = status.firebase
                )
            }
        }
    }
}

@Composable
private fun StatusChip(
    status: ConnectionState
) {

    val color = when (status) {
        ConnectionState.ONLINE -> Color(0xFFE8F8F1)
        ConnectionState.CONNECTING -> Color(0xFFFFF7E0)
        ConnectionState.OFFLINE,
        ConnectionState.ERROR -> Color(0xFFFFECEC)
    }

    val textColor = when (status) {
        ConnectionState.ONLINE -> Color(0xFF26A269)
        ConnectionState.CONNECTING -> Color(0xFFF9A825)
        ConnectionState.OFFLINE,
        ConnectionState.ERROR -> Color(0xFFD32F2F)
    }

    val text = when (status) {
        ConnectionState.ONLINE -> "Online"
        ConnectionState.CONNECTING -> "Connecting"
        ConnectionState.OFFLINE -> "Offline"
        ConnectionState.ERROR -> "Error"
    }

    Surface(
        shape = RoundedCornerShape(50),
        color = color
    ) {

        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 8.dp
            ),
            color = textColor,
            fontWeight = FontWeight.SemiBold
        )

    }

}

@Composable
private fun StatusItem(
    modifier: Modifier = Modifier,
    title: String,
    state: ConnectionState
) {

    val color = when (state) {
        ConnectionState.ONLINE -> Color(0xFF26A269)
        ConnectionState.CONNECTING -> Color(0xFFF9A825)
        ConnectionState.OFFLINE -> Color(0xFFD32F2F)
        ConnectionState.ERROR -> Color(0xFFD32F2F)
    }

    val text = when (state) {
        ConnectionState.ONLINE -> "Online"
        ConnectionState.CONNECTING -> "Connecting"
        ConnectionState.OFFLINE -> "Offline"
        ConnectionState.ERROR -> "Error"
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = color
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
            StatusCard(
                status = SystemStatus(
                    camera = ConnectionState.ONLINE,
                    server = ConnectionState.ONLINE,
                    firebase = ConnectionState.CONNECTING
                )
            )
        }
    }
}