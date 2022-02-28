package com.example.canvastictactoegame

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToe()
        }
    }
}

@Composable
fun TicTacToe(

) {
    val context = LocalContext.current
    var center by remember {
        mutableStateOf(Offset.Unspecified)
    }


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 8.dp,
                end = 8.dp
            )
            .pointerInput(true) {
                detectTapGestures {
                    when {
                        it.x < size.width / 3f && (it.y < size.height * 2 / 5f && it.y > size.height / 5f) -> {
                            Toast
                                .makeText(context, "Top left", Toast.LENGTH_SHORT)
                                .show()
                        }
                        (it.x > size.width / 3f && it.x < size.width * 2 / 3f) && (it.y < size.height * 2 / 5f && it.y > size.height / 5f) -> {
                            Toast
                                .makeText(context, "Top mid", Toast.LENGTH_SHORT)
                                .show()
                        }
                        it.x > size.width * 2 / 3f && (it.y < size.height * 2 / 5f && it.y > size.height / 5f) -> {
                            Toast
                                .makeText(context, "Top right", Toast.LENGTH_SHORT)
                                .show()
                        }
                        it.x < size.width / 3f && (it.y > size.height * 2 / 5f && it.y < size.height * 3 / 5f) -> {
                            Toast
                                .makeText(context, "Mid left", Toast.LENGTH_SHORT)
                                .show()
                        }
                        (it.x > size.width / 3f && it.x < size.width * 2 / 3f) && (it.y > size.height * 2 / 5f && it.y < size.height * 3 / 5f) -> {
                            Toast
                                .makeText(context, "Middle", Toast.LENGTH_SHORT)
                                .show()
                        }
                        it.x > size.width * 2 / 3f && (it.y > size.height * 2 / 5f && it.y < size.height * 3 / 5f) -> {
                            Toast
                                .makeText(context, "Mid right", Toast.LENGTH_SHORT)
                                .show()
                        }
                        it.x < size.width / 3f && (it.y > size.height * 3 / 5f && it.y < size.height * 4 / 5f) -> {
                            Toast
                                .makeText(context, "Bottom left", Toast.LENGTH_SHORT)
                                .show()
                        }
                        (it.x > size.width / 3f && it.x < size.width * 2 / 3f) && (it.y > size.height * 3 / 5f && it.y < size.height * 4 / 5f) -> {
                            Toast
                                .makeText(context, "Bottom mid", Toast.LENGTH_SHORT)
                                .show()
                        }
                        it.x > size.width * 2 / 3f && (it.y > size.height * 3 / 5f && it.y < size.height * 4 / 5f) -> {
                            Toast
                                .makeText(context, "Bottom right", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
    ) {
        center = this.center

        /* DRAW THE PLAYGROUND */

        drawLine(
            color = Color.Black,
            start = Offset(
                x = size.width / 3f,
                y = size.height / 5f
            ),
            end = Offset(
                x = size.width / 3f,
                y = size.height - size.height / 5f
            ),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color.Black,
            start = Offset(
                x = size.width - size.width / 3f,
                y = size.height / 5f
            ),
            end = Offset(
                x = size.width - size.width / 3f,
                y = size.height - size.height / 5f
            ),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color.Black,
            start = Offset(
                x = 0f,
                y = size.height * 2 / 5f
            ),
            end = Offset(
                x = size.width,
                y = size.height * 2 / 5f
            ),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color.Black,
            start = Offset(
                x = 0f,
                y = size.height * 3 / 5f
            ),
            end = Offset(
                x = size.width,
                y = size.height * 3 / 5f
            ),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )


    }
}

