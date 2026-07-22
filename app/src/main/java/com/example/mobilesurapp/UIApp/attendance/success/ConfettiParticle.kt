package com.example.mobilesurapp.UIApp.attendance.success

import androidx.compose.ui.graphics.Color

data class ConfettiParticle(

    var x: Float,
    var y: Float,

    val size: Float,

    val color: Color,

    var speed: Float,

    var drift: Float,

    var rotation: Float,

    var rotationSpeed: Float

)