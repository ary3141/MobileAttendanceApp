package com.example.mobilesurapp.UIApp.attendance.success

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically

private val SuccessGreen = Color(0xFF16C47F)
private val GreetingPurple = Color(0xFFEAE4FF)

@Composable
fun SuccessCard(
    employeeName: String,
    employeeCode: String,
    attendanceTime: String,
    attendanceDate: String,
    modifier: Modifier = Modifier
) {
    var animate by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        animate = true
    }

    val scale by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "checkScale"
    )
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            visible = animate,
            enter = fadeIn() +
                    slideInVertically {
                        it / 3
                    }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 38.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 54.dp,
                        bottom = 24.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = employeeName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = employeeCode,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = attendanceTime,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = attendanceDate,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        color = GreetingPurple,
                        tonalElevation = 0.dp
                    ) {

                        Text(
                            text = "Have a great day! 👋",
                            modifier = Modifier.padding(
                                vertical = 18.dp,
                                horizontal = 16.dp
                            ),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                    }

                }

            }

            Surface(
                modifier = Modifier
                    .shadow(
                        elevation = 12.dp,
                        shape = CircleShape
                    )
                    .offset(y = 0.dp),
                shape = CircleShape,
                color = SuccessGreen,
                contentColor = Color.White
            ) {

                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    modifier = Modifier.padding(18.dp)
                )

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
private fun SuccessCardPreview() {

    MobileSurAppTheme {

        SuccessCard(
            employeeName = "Arya Erlangga",
            employeeCode = "EMP001",
            attendanceTime = "08:00 AM",
            attendanceDate = "Monday, 20 May 2026"
        )

    }

}