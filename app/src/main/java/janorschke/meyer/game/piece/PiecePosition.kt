package janorschke.meyer.game.piece

import janorschke.meyer.game.board.Board

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
}
