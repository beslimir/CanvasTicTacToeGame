package com.example.canvastictactoegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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

    var symbolPosition by remember {
        mutableStateOf(setNewGame())
    }
    var currentPlayer by remember {
        mutableStateOf<Player>(Player.X)
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
                            if (symbolPosition[0][0] == '?') {
                                symbolPosition = updateGameState(
                                    symbolPosition, 0, 0, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        (it.x > size.width / 3f && it.x < size.width * 2 / 3f) && (it.y < size.height * 2 / 5f && it.y > size.height / 5f) -> {
                            if (symbolPosition[0][1] == '?') {
                                symbolPosition = updateGameState(
                                    symbolPosition, 0, 1, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        it.x > size.width * 2 / 3f && (it.y < size.height * 2 / 5f && it.y > size.height / 5f) -> {
                            if (symbolPosition[0][2] == '?') {
                                symbolPosition = updateGameState(
                                    symbolPosition, 0, 2, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        it.x < size.width / 3f && (it.y > size.height * 2 / 5f && it.y < size.height * 3 / 5f) -> {
                            if (symbolPosition[1][0] == '?') {
                                symbolPosition = updateGameState(
                                    symbolPosition, 1, 0, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        (it.x > size.width / 3f && it.x < size.width * 2 / 3f) && (it.y > size.height * 2 / 5f && it.y < size.height * 3 / 5f) -> {
                            if (symbolPosition[1][1] == '?') {
                                symbolPosition = updateGameState(
                                    symbolPosition, 1, 1, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        it.x > size.width * 2 / 3f && (it.y > size.height * 2 / 5f && it.y < size.height * 3 / 5f) -> {
                            if (symbolPosition[1][2] == '?') {
                                symbolPosition = updateGameState(
                                    symbolPosition, 1, 2, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        it.x < size.width / 3f && (it.y > size.height * 3 / 5f && it.y < size.height * 4 / 5f) -> {
                            if (symbolPosition[2][0] == '?') {
                                symbolPosition = updateGameState(
                                    symbolPosition, 2, 0, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        (it.x > size.width / 3f && it.x < size.width * 2 / 3f) && (it.y > size.height * 3 / 5f && it.y < size.height * 4 / 5f) -> {
                            if (symbolPosition[2][1] == '?') {
                                symbolPosition = updateGameState(
                                    symbolPosition, 2, 1, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        it.x > size.width * 2 / 3f && (it.y > size.height * 3 / 5f && it.y < size.height * 4 / 5f) -> {
                            if (symbolPosition[2][2] == '?') {
                                symbolPosition = updateGameState(
                                    symbolPosition, 2, 2, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                    }
                }
            }
    ) {
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

        val rectHeight = size.height / 5f
        val rectWidth = size.width / 3f

        symbolPosition.forEachIndexed { i, row ->
            row.forEachIndexed { j, symbol ->
                if (symbolPosition[i][j] != '?') {
                    if (symbol == Player.X.symbol) {
                        val path = Path().apply {
                            moveTo(
                                x = (size.width / 3f) / 5f + size.width * j / 3f,
                                y = size.height * (i + 1) / 5f + (size.height / 5f) / 5f
                            )
                            lineTo(
                                x = ((size.width / 3f) - (size.width / 3f) / 5f) + size.width * j / 3f,
                                y = ((size.height / 5f) - (size.height / 5f) / 5f) + size.height * (i + 1) / 5f
                            )
                            moveTo(
                                x = ((size.width / 3f) - (size.width / 3f) / 5f) + size.width * j / 3f,
                                y = size.height * (i + 1) / 5f + (size.height / 5f) / 5f
                            )
                            lineTo(
                                x = (size.width / 3f) / 5f + size.width * j / 3f,
                                y = ((size.height / 5f) - (size.height / 5f) / 5f) + size.height * (i + 1) / 5f
                            )
                        }

                        drawPath(
                            path = path,
                            color = Color.Red,
                            style = Stroke(
                                width = 4.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )
                    } else {
                        drawArc(
                            color = Color.Green,
                            startAngle = 0f,
                            sweepAngle = 360f,
                            useCenter = false,
                            size = Size(
                                width = rectHeight * 3 / 5f,
                                height = rectWidth * 3 / 5f
                            ),
                            style = Stroke(
                                width = 4.dp.toPx(),
                                cap = StrokeCap.Round
                            ),
                            topLeft = Offset(
                                x = (size.width / 3f) / 5f + size.width * j / 3f,
                                y = size.height * (i + 1) / 5f + (size.height / 5f) / 5f
                            )
                        )
                    }
                }
            }
        }


    }
}

private fun setNewGame(): Array<CharArray> {
    return arrayOf(
        charArrayOf('?', '?', '?'),
        charArrayOf('?', '?', '?'),
        charArrayOf('?', '?', '?')
    )
}

private fun updateGameState(
    currentGameState: Array<CharArray>,
    i: Int,
    j: Int,
    symbol: Char,
): Array<CharArray> {
    val gameStateCopy = currentGameState.copyOf()
    gameStateCopy[i][j] = symbol
    return gameStateCopy
}

sealed class Player(val symbol: Char) {
    object X : Player('x')
    object O : Player('o')

    operator fun not(): Player {
        return if (this is X) O else X
    }
}

