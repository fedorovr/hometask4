package task

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import game.Game
import java.util.*

/*
Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game)
Implement the helper function first (moveAndMergeEqual in SurnameNameGame2048Helper.kt), then extension functions below.

Try to use methods of SquareBoard and GameBoard instead of reimplementing them.
(You may use and add extensions like SquareBoard.indices() as well).

When you finish, you can play the game by executing 'PlayGame2048' (or choosing the corresponding run configuration).
 */
fun newGame2048(): Game = Game2048()

val FIELD_SIZE = 4

class Game2048 : Game {
    private val board = createGameBoard<Int?>(FIELD_SIZE)

    override fun initialize() {
        board.addRandomValue()
        board.addRandomValue()
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addRandomValue()
        }
    }

    override fun get(i: Int, j: Int): Int? = board[i, j]
}

val random = Random()
fun generateRandomStartValue() = if (random.nextInt(10) == 9) 4 else 2

/*
Add a random value to a free cell in a board.
The value should be 2 for 90% cases, 4 for the rest of the cases.
Use the generateRandomStartValue function above.
Examples and tests in TestAddRandomValue.
 */
fun GameBoard<Int?>.addRandomValue() {
    val emptyCells: Collection<Cell> = filter { it == null }
    set(emptyCells.drop(random.nextInt(emptyCells.size)).first(), generateRandomStartValue())
}

fun <T> List<T>.padWithNulls(targetLength: Int): List<T?> =
        mutableListOf<T?>()
                .apply { addAll(this@padWithNulls) }
                .apply { (this.size..targetLength - 1).forEach { add(null) } }

/*
Move values in a specified rowOrColumn only.
Use the helper function 'moveAndMergeEqual' (in SurnameNameGame2048Helper.kt).
The values should be moved to the beginning of the row (or column), in the same manner as in the function 'moveAndMergeEqual'.
Examples and tests in TestMoveValuesInRowOrColumn.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
    val oldValues = rowOrColumn.map { get(it) }
    val newValues = oldValues.moveAndMergeEqual { it * 2 }.padWithNulls(rowOrColumn.size)
    rowOrColumn.zip(newValues).forEach { set(it.first, it.second) }
    return oldValues != newValues
}

/*
Move values by the rules of the 2048 game to the specified direction.
Use the moveValuesInRowOrColumn function above.
Examples and tests in TestMoveValues.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
    val field_range = 1..FIELD_SIZE
    val field_range_rev = field_range.reversed()
    return when (direction) {
        Direction.UP -> field_range.map { getColumn(field_range, it) }.map { moveValuesInRowOrColumn(it) }.any { it }
        Direction.DOWN -> field_range.map { getColumn(field_range_rev, it) }.map { moveValuesInRowOrColumn(it) }.any { it }
        Direction.LEFT -> field_range.map { getRow(it, field_range) }.map { moveValuesInRowOrColumn(it) }.any { it }
        Direction.RIGHT -> field_range.map { getRow(it, field_range_rev) }.map { moveValuesInRowOrColumn(it) }.any { it }
    }
}