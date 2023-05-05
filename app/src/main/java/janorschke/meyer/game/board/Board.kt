package janorschke.meyer.game.board

import janorschke.meyer.game.GameViewModel
import janorschke.meyer.game.piece.Bishop
import janorschke.meyer.game.piece.King
import janorschke.meyer.game.piece.Knight
import janorschke.meyer.game.piece.Pawn
import janorschke.meyer.game.piece.Piece
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.piece.Queen
import janorschke.meyer.game.piece.Rook

class Board(private val gameViewModel: GameViewModel) {
    companion object {
        const val SIZE = 64
        const val LINE_SIZE = 8
    }

    private lateinit var board: Array<Array<Piece?>>

    init {
        reset()
    }

    /**
     * @return the whole chess board
     */
    fun get(): Array<Array<Piece?>> {
        return board
    }

    /**
     * @param position target
     * @return piece on the target
     */
    fun getField(position: PiecePosition): Piece? {
        return board[position.row][position.col]
    }

    /**
     * Moves an piece to another position
     *
     * @param from source position
     * @param to target position
     * @return board move
     */
    fun createBoardMove(from: PiecePosition, to: PiecePosition): BoardMove {
        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/23
        val fromFieldPiece = getField(from)
        val toFieldPiece = getField(to)

        setField(from, null)
        setField(to, fromFieldPiece)

        return BoardMove(board.clone(), to, toFieldPiece == null)
    }

    private fun setField(position: PiecePosition, piece: Piece?) {
        board[position.row][position.col] = piece
    }

    /**
     * Resets the board to the initial state
     */
    fun reset() {
        board = Array(LINE_SIZE) { Array(LINE_SIZE) { null } }
        board[0] = generateBaseLine(PieceColor.BLACK)
        board[1] = generatePawnLine(PieceColor.BLACK)
        board[6] = generatePawnLine(PieceColor.WHITE)
        board[7] = generateBaseLine(PieceColor.WHITE)
    }

    /**
     * @param color of the piece
     * @return the pawn line
     */
    private fun generatePawnLine(color: PieceColor): Array<Piece?> {
        return Array(LINE_SIZE) { Pawn(gameViewModel, color) }
    }

    /**
     * @param color of the piece
     * @return the base line of pieces
     */
    private fun generateBaseLine(color: PieceColor): Array<Piece?> {
        return arrayOf(
                Rook(gameViewModel, color),
                Knight(gameViewModel, color),
                Bishop(gameViewModel, color),
                Queen(gameViewModel, color),
                King(gameViewModel, color),
                Bishop(gameViewModel, color),
                Knight(gameViewModel, color),
                Rook(gameViewModel, color)
        )
    }
}