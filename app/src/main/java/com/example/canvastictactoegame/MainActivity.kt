package com.example.canvastictactoegame

import android.graphics.Paint
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToe()
        }
    }
}

@Composable
fun TicTacToe() {
    val context = LocalContext.current

    var gameState by remember {
        mutableStateOf(setNewGame())
    }
    var currentPlayer by remember {
        mutableStateOf<Player>(Player.X)
    }

    var pointsPlayerX by remember {
        mutableStateOf(0)
    }
    var pointsPlayerO by remember {
        mutableStateOf(0)
    }

    var isGameRunning by remember {
        mutableStateOf(true)
    }

    val scope = rememberCoroutineScope()


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 8.dp,
                end = 8.dp
            )
            .pointerInput(true) {
                detectTapGestures {
                    if (!isGameRunning) {
                        return@detectTapGestures
                    }
                    when {
                        it.x < size.width / 3f && (it.y < size.height * 2 / 5f && it.y > size.height / 5f) -> {
                            if (gameState[0][0] == '?') {
                                gameState = updateGameState(
                                    gameState, 0, 0, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        (it.x > size.width / 3f && it.x < size.width * 2 / 3f) && (it.y < size.height * 2 / 5f && it.y > size.height / 5f) -> {
                            if (gameState[0][1] == '?') {
                                gameState = updateGameState(
                                    gameState, 0, 1, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        it.x > size.width * 2 / 3f && (it.y < size.height * 2 / 5f && it.y > size.height / 5f) -> {
                            if (gameState[0][2] == '?') {
                                gameState = updateGameState(
                                    gameState, 0, 2, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        it.x < size.width / 3f && (it.y > size.height * 2 / 5f && it.y < size.height * 3 / 5f) -> {
                            if (gameState[1][0] == '?') {
                                gameState = updateGameState(
                                    gameState, 1, 0, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        (it.x > size.width / 3f && it.x < size.width * 2 / 3f) && (it.y > size.height * 2 / 5f && it.y < size.height * 3 / 5f) -> {
                            if (gameState[1][1] == '?') {
                                gameState = updateGameState(
                                    gameState, 1, 1, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        it.x > size.width * 2 / 3f && (it.y > size.height * 2 / 5f && it.y < size.height * 3 / 5f) -> {
                            if (gameState[1][2] == '?') {
                                gameState = updateGameState(
                                    gameState, 1, 2, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        it.x < size.width / 3f && (it.y > size.height * 3 / 5f && it.y < size.height * 4 / 5f) -> {
                            if (gameState[2][0] == '?') {
                                gameState = updateGameState(
                                    gameState, 2, 0, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        (it.x > size.width / 3f && it.x < size.width * 2 / 3f) && (it.y > size.height * 3 / 5f && it.y < size.height * 4 / 5f) -> {
                            if (gameState[2][1] == '?') {
                                gameState = updateGameState(
                                    gameState, 2, 1, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                        it.x > size.width * 2 / 3f && (it.y > size.height * 3 / 5f && it.y < size.height * 4 / 5f) -> {
                            if (gameState[2][2] == '?') {
                                gameState = updateGameState(
                                    gameState, 2, 2, currentPlayer.symbol
                                )
                                currentPlayer = !currentPlayer
                            }
                        }
                    }

                    //check the result of the game
                    val isItDraw = gameState.all { row ->
                        row.all { char ->
                            char != '?'
                        }
                    }
                    val playerXWin = whoIsTheWinner(gameState, Player.X)
                    val playerOWin = whoIsTheWinner(gameState, Player.O)

                    when {
                        playerXWin -> {
                            Toast
                                .makeText(context, "Player X won!", Toast.LENGTH_SHORT)
                                .show()
                            pointsPlayerX++
                        }
                        playerOWin -> {
                            Toast
                                .makeText(context, "Player O won!", Toast.LENGTH_SHORT)
                                .show()
                            pointsPlayerO++
                        }
                        isItDraw -> {
                            Toast
                                .makeText(context, "It's a draw!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    if (isItDraw || playerXWin || playerOWin) {
                        scope.launch {
                            isGameRunning = false
                            delay(2000L)
                            gameState = setNewGame()
                            if (playerXWin) {
                                currentPlayer = Player.O
                            } else if (playerOWin) {
                                currentPlayer = Player.X
                            }
                            isGameRunning = true
                        }
                    }
                }
            }
    ) {

        /* Draw text on native canvas */
        val textPath = android.graphics.Path().apply {
            moveTo(100f, 200f)
            quadTo(size.width / 2f, 100f, size.width - 100f, 200f)
        }
        val playerXPath = android.graphics.Path().apply {
            moveTo(0f, size.height * 4 / 5f + 100f)
            quadTo(size.width / 4f,
                size.height * 4 / 5f + 100f,
                size.width / 2f,
                size.height * 4 / 5f + 100f)
        }
        val playerOPath = android.graphics.Path().apply {
            moveTo(size.width / 2f, size.height * 4 / 5f + 100f)
            quadTo(size.width / 2f + size.width / 4f,
                size.height * 4 / 5f + 100f,
                size.width,
                size.height * 4 / 5f + 100f)
        }
        drawContext.canvas.nativeCanvas.apply {
            drawTextOnPath(
                "Current on move: Player ${currentPlayer.symbol}",
                textPath,
                0f,
                0f,
                Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 70f
                    textAlign = Paint.Align.CENTER
                }
            )

            drawTextOnPath(
                "Player X points: $pointsPlayerX",
                playerXPath,
                0f,
                0f,
                Paint().apply {
                    color = android.graphics.Color.RED
                    textSize = 50f
                }
            )
            drawTextOnPath(
                "Player O points: $pointsPlayerO",
                playerOPath,
                0f,
                0f,
                Paint().apply {
                    color = android.graphics.Color.GREEN
                    textSize = 50f
                }
            )
        }


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

        gameState.forEachIndexed { i, row ->
            row.forEachIndexed { j, symbol ->
                if (gameState[i][j] != '?') {
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

private fun whoIsTheWinner(gameState: Array<CharArray>, player: Player): Boolean {
    val firstRow = gameState[0][0] == player.symbol &&
            gameState[0][1] == player.symbol &&
            gameState[0][2] == player.symbol
    val secondRow = gameState[1][0] == player.symbol &&
            gameState[1][1] == player.symbol &&
            gameState[1][2] == player.symbol
    val thirdRow = gameState[2][0] == player.symbol &&
            gameState[2][1] == player.symbol &&
            gameState[2][2] == player.symbol

    val firstColumn = gameState[0][0] == player.symbol &&
            gameState[1][0] == player.symbol &&
            gameState[2][0] == player.symbol
    val secondColumn = gameState[0][1] == player.symbol &&
            gameState[1][1] == player.symbol &&
            gameState[2][1] == player.symbol
    val thirdColumn = gameState[0][2] == player.symbol &&
            gameState[1][2] == player.symbol &&
            gameState[2][2] == player.symbol

    val firstDiagonal = gameState[0][0] == player.symbol &&
            gameState[1][1] == player.symbol &&
            gameState[2][2] == player.symbol
    val secondDiagonal = gameState[0][2] == player.symbol &&
            gameState[1][1] == player.symbol &&
            gameState[2][0] == player.symbol

    //if and only if one or more conditions are true, return true
    return firstRow || secondRow || thirdRow || firstColumn || secondColumn || thirdColumn || firstDiagonal || secondDiagonal

}

sealed class Player(val symbol: Char) {
    object X : Player('x')
    object O : Player('o')

    operator fun not(): Player {
        return if (this is X) O else X
    }
}

