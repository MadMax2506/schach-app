package janorschke.meyer.game.piece

import janorschke.meyer.game.BoardViewModel

class PiecePosition {
    val row: Int
    val col: Int

    constructor(position: Int) {
        row = position / BoardViewModel.LINE_SIZE
        col = position % BoardViewModel.LINE_SIZE
    }

    constructor(row: Int, col: Int) {
        this.row = row
        this.col = col
    }

    /**
     * @return the position as string in the valid chess notation
     */
    override fun toString(): String {
        return "${(97 + col).toChar()}${row + 1}"
    }
}
