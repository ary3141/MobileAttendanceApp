package com.example.mobilesurapp.UIApp.attendance.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cameraswitch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme

@Composable
fun AttendanceActionBar(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onSwitchCameraClick: () -> Unit
) {

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp),

        enabled = enabled,
        onClick = onSwitchCameraClick,

        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1B1B1F),
            contentColor = Color.White
        )

    ) {

        Icon(
            imageVector = Icons.Rounded.Cameraswitch,
            contentDescription = null
        )

        Text(
            text = "Switch Camera",
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )

    }

}

//@Preview(showBackground = true)
//@Composable
//private fun AttendanceActionBarPreview() {
//
//    MobileSurAppTheme {
//
//        AttendanceActionBar(
//            onSwitchCameraClick = {},
//            modifier = TODO(),
//            enabled = TODO()
//        )
//
//    }
//
//}