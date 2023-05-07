package janorschke.meyer.game

import androidx.lifecycle.ViewModel
import janorschke.meyer.game.adapter.GameFieldAdapter
import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.BoardHistory
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.piece.model.Piece
import janorschke.meyer.game.player.PlayerInfo

class GameViewModel : ViewModel() {
    private lateinit var gameFieldAdapter: GameFieldAdapter
    private val board: Board = Board()
    private val boardHistory: BoardHistory = BoardHistory()
    private var selectedPiecePosition: PiecePosition? = null
    private lateinit var playerInfo: PlayerInfo

    fun getField(position: PiecePosition): Piece? {
        return board.getField(position)
    }

    fun setGameFieldAdapter(gameFieldAdapter: GameFieldAdapter) {
        this.gameFieldAdapter = gameFieldAdapter
    }

    fun setPlayerInfo(playerInfo: PlayerInfo) {
        this.playerInfo = playerInfo
    }

    /**
     * Moves a piece to another position
     *
     * @param from source position
     * @param to target position
     */
    fun movePiece(from: PiecePosition, to: PiecePosition) {
        val piece = board.getField(from)!!
        piece.moved = true
        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/23
        // TODO Info: Der folgende Code aktualisiert das richtige Feld und macht keine Kopie
        boardHistory.push(board.createBoardMove(from, to))
    }

    /**
     * OnClick handler for a game field
     * This method is called when the user clicks on a game field.
     * It handles selecting a chess piece, showing possible moves for the selected piece and making moves.
     *
     * @param position which is selected
     */
    fun onFieldClicked(position: PiecePosition) {
        val piece = board.getField(position)
        val possibleMoves = piece?.possibleMoves(position) ?: emptyList()

        when {
            // handle first click
            selectedPiecePosition == null && piece?.color == playerInfo.color -> setSelectedPiece(position, possibleMoves)
            // handle second click
            selectedPiecePosition != null -> {
                val isTeammate = (piece?.color == playerInfo.color && selectedPiecePosition != position)
                if (piece?.color != playerInfo.color) tryToMovePiece(selectedPiecePosition!!, position)
                setSelectedPiece(if (isTeammate) position else null, if (isTeammate) possibleMoves else emptyList())
            }
        }
    }

    private fun tryToMovePiece(fromPosition: PiecePosition, toPosition: PiecePosition) {
        val possibleMoves = board.getField(fromPosition)?.possibleMoves(fromPosition) ?: emptyList()

        if (toPosition in possibleMoves) movePiece(fromPosition, toPosition)
        setSelectedPiece(null, emptyList())
    }

    private fun setSelectedPiece(position: PiecePosition?, possibleMoves: List<PiecePosition>) {
        selectedPiecePosition = position
        gameFieldAdapter.setPossibleMoves(possibleMoves)
    }
}
