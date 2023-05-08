package janorschke.meyer.game

import androidx.lifecycle.ViewModel
import janorschke.meyer.game.adapter.GameFieldAdapter
import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.BoardHistory
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.piece.model.Piece
import janorschke.meyer.game.player.PlayerInfo

/**
 * The GameViewModel represents the view model for a chess game.
 * It manages the game state, handles user input, and communicates with the view layer through a GameFieldAdapter object.
 */
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
     * Moves a piece to the target position
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
        val isPlayersPiece = (piece?.color == playerInfo.color)

        // handle first click
        if (selectedPiecePosition == null && isPlayersPiece) {
            setSelectedPiece(position, possibleMoves)
            return
        }
        // handle second click
        if (selectedPiecePosition != null && !isPlayersPiece) {
            tryToMovePiece(selectedPiecePosition!!, position)
            return
        }
        if (isPlayersPiece && selectedPiecePosition != position) setSelectedPiece(position, possibleMoves)
        else setSelectedPiece()
    }

    /**
     * Moves a chess piece from the source position to the target position, if the target position is valid.
     * @param fromPosition the source position of the chess piece
     * @param toPosition the target position to move the chess piece to
     */
    private fun tryToMovePiece(fromPosition: PiecePosition, toPosition: PiecePosition) {
        val possibleMoves = board.getField(fromPosition)?.possibleMoves(fromPosition) ?: emptyList()

        if (toPosition in possibleMoves) movePiece(fromPosition, toPosition)
        setSelectedPiece()
    }

    /**
     * Sets the selected piece and shows the possible moves through the GameFieldAdapter.
     * @param position the position of the selected piece (optional: Default = null)
     * @param possibleMoves the possible moves for the selected piece (optional: Default = emptyList())
     */
    private fun setSelectedPiece(position: PiecePosition? = null, possibleMoves: List<PiecePosition> = emptyList()) {
        selectedPiecePosition = position
        gameFieldAdapter.setPossibleMoves(possibleMoves)
    }
}
