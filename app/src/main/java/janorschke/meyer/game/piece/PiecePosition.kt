package janorschke.meyer.game.piece

class PiecePosition {
    val row: Int
    val col: Int get

    constructor(position: Int) {
        row = position / 8
        col = position % 8
    }

    constructor(row: Int, col: Int) {
        this.row = row
        this.col = col
    }
}
