package com.example.mobilesurapp.UIApp.attendance.success

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme
import kotlin.random.Random

private const val PARTICLE_COUNT = 160

@Composable
fun ConfettiBackground(
    modifier: Modifier = Modifier
) {

    var canvasWidth by remember { mutableFloatStateOf(0f) }
    var canvasHeight by remember { mutableFloatStateOf(0f) }

    val particles = remember {

        mutableStateListOf<ConfettiParticle>()

    }

    LaunchedEffect(canvasWidth, canvasHeight) {

        if (canvasWidth == 0f || canvasHeight == 0f) return@LaunchedEffect

        if (particles.isEmpty()) {

            repeat(PARTICLE_COUNT) {

                particles += randomParticle(
                    canvasWidth,
                    canvasHeight
                )

            }

        }

    }

    LaunchedEffect(Unit) {

        while (true) {

            withInfiniteAnimationFrameMillis {

                particles.forEach {

                    it.y += it.speed

                    it.x += it.drift

                    it.rotation += it.rotationSpeed

                    if (it.y > canvasHeight + 40f) {

                        it.y = -40f

                        it.x = Random.nextFloat() * canvasWidth

                    }

                    if (it.x < -40f)
                        it.x = canvasWidth + 40f

                    if (it.x > canvasWidth + 40f)
                        it.x = -40f

                }

            }

        }

    }

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        canvasWidth = size.width
        canvasHeight = size.height

        particles.forEach { particle ->

            rotate(
                particle.rotation,
                Offset(particle.x, particle.y)
            ) {

                drawRect(

                    color = particle.color,

                    topLeft = Offset(
                        particle.x,
                        particle.y
                    ),

                    size = androidx.compose.ui.geometry.Size(
                        particle.size,
                        particle.size * 0.6f
                    )

                )

            }

        }

    }

}

private fun randomParticle(

    width: Float,

    height: Float

): ConfettiParticle {

    return ConfettiParticle(

        x = Random.nextFloat() * width,

        y = Random.nextFloat() * height,

        size = Random.nextInt(8,18).toFloat(),

        color = SuccessColors.ConfettiColors.random(),

        speed = Random.nextFloat() * 5f + 3f,

        drift = Random.nextFloat() * 2f - 1f,

        rotation = Random.nextFloat() * 360f,

        rotationSpeed = Random.nextFloat() * 6f - 3f

    )

}

@Preview(showBackground = true)
@Composable
private fun ConfettiPreview() {

    MobileSurAppTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SuccessColors.Background)
        ) {

            ConfettiBackground()

        }

    }

}