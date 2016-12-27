package task

import board.*
import game.Game
import java.util.Collections.shuffle

fun newGameOfFifteen(): Game = GameOfFifteen()

/*
Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
When you finish, you can play the game by executing 'PlayGameOfFifteen' (or choosing the corresponding run configuration).
 */

val FIELD_SIZE_15GAME = 4

class GameOfFifteen : Game {
    private val board = createGameBoard<Int?>(FIELD_SIZE_15GAME)
    private val fieldRange = 1..FIELD_SIZE_15GAME
    private val tilesRange = 1..(FIELD_SIZE_15GAME * FIELD_SIZE_15GAME) - 1

    override fun initialize() {
        val cells = fieldRange.flatMap { row -> fieldRange.map { col -> CellImpl(row, col) } }
        val randomPermutation = tilesRange.toList().apply {
            do {
                shuffle(this)
            } while (!countParity(this))
        }
        cells.zip(randomPermutation).forEach { board[it.first] = it.second }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean =
            board.indices.dropLast(1).zip(tilesRange).all { board[it.first.first, it.first.second] == it.second }

    override fun processMove(direction: Direction) {
        board.moveValuesGameOf15(direction)
    }

    override fun get(i: Int, j: Int): Int? = board[i, j]

    private fun GameBoard<Int?>.moveValuesGameOf15(direction: Direction) {
        val fieldRangeRev = fieldRange.reversed()
        val getColumnOrRow: (Int) -> List<Cell> = when (direction) {
            Direction.UP -> { c -> getColumn(fieldRange, c) }
            Direction.DOWN -> { c -> getColumn(fieldRangeRev, c) }
            Direction.LEFT -> { r -> getRow(r, fieldRange) }
            Direction.RIGHT -> { r -> getRow(r, fieldRangeRev) }
        }
        fieldRange.map { getColumnOrRow(it) }.forEach { moveValuesInRowOrColumnGameOf15(it) }
    }

    private fun GameBoard<Int?>.moveValuesInRowOrColumnGameOf15(rowOrColumn: List<Cell>) {
        val indexOfNullValue = rowOrColumn.indexOfFirst { get(it) == null }
        if (indexOfNullValue in 0..rowOrColumn.size - 2) {  // swap null and element to the right of null
            set(rowOrColumn[indexOfNullValue], get(rowOrColumn[indexOfNullValue + 1]))
            set(rowOrColumn[indexOfNullValue + 1], null)
        }
    }
}
