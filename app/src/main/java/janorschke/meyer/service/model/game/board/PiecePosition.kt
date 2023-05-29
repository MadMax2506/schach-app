package janorschke.meyer.service.model.game.board

class PiecePosition {
    val row: Int
    val col: Int

    constructor(position: Int) {
        row = position / Board.LINE_SIZE
        col = position % Board.LINE_SIZE
    }

    constructor(row: Int, col: Int) {
        this.row = row
        this.col = col
    }

    /**
     * @return the row-position as string
     */
    fun getRowNotation(): String {
        return (row + 1).toString()
    }

    /**
     * @return the col-position as string
     */
    fun getColNotation(): String {
        return (97 + col).toChar().toString()
    }

    /**
     * @return the position as string in the valid chess notation
     */
    fun getNotation(): String {
        return "${getColNotation()}${getRowNotation()}"
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PiecePosition) return false

        return this.row == other.row && this.col == other.col
    }
}
