package com.example.mobilesurapp.UIApp.attendance.success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme
import com.example.mobilesurapp.UIApp.attendance.success.SuccessCard
import com.example.mobilesurapp.UIApp.attendance.success.CountdownCard
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

private val SuccessBackground = Color(0xFF18241D)
private val SuccessGreen = Color(0xFF16C47F)

@Composable
fun AttendanceSuccessScreen(

    employeeName: String,

    employeeCode: String,

    attendanceTime: String,

    attendanceDate: String,

    modifier: Modifier = Modifier,

    onFinished: () -> Unit = {}

) {
    var secondsRemaining by remember {
        mutableIntStateOf(3)
    }

    LaunchedEffect(Unit) {

        repeat(3) {

            delay(1000)

            secondsRemaining--

        }

        onFinished()

    }
    val progress by animateFloatAsState(

        targetValue = secondsRemaining / 3f,

        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        ),

        label = "countdown"

    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SuccessBackground)
            .systemBarsPadding()
    ) {

        // We'll replace this with animated confetti later
        ConfettiBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp,
                    vertical = 32.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(
                    animationSpec = tween(700)
                )
            ) {

                Text(
                    text = "Attendance Recorded!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = SuccessColors.SuccessGreen,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(modifier = Modifier.height(28.dp))

            SuccessCard(

                employeeName = employeeName,

                employeeCode = employeeCode,

                attendanceTime = attendanceTime,

                attendanceDate = attendanceDate

            )

            Spacer(modifier = Modifier.height(36.dp))

            CountdownCard(
                secondsRemaining = secondsRemaining,
                progress = progress
            )

        }

    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AttendanceSuccessPreview() {

    MobileSurAppTheme {

        AttendanceSuccessScreen(

            employeeName = "Arya Erlangga",

            employeeCode = "EMP001",

            attendanceTime = "08:00 AM",

            attendanceDate = "Monday, 20 May 2026"

        )

    }

}