package janorschke.meyer.game

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import janorschke.meyer.game.adapter.GameFieldAdapter
import janorschke.meyer.game.adapter.MoveHistoryAdapter
import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.BoardHistory
import janorschke.meyer.game.board.BoardMove
import janorschke.meyer.game.board.validator.BoardValidator
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.piece.model.Piece
import janorschke.meyer.game.player.PlayerInfo

private const val LOG_TAG = "GameViewModel"

/**
 * The GameViewModel represents the view model for a chess game.
 * It manages the game state, handles user input, and communicates with the view layer through a GameFieldAdapter object.
 */
class GameViewModel : ViewModel() {
    private val board: Board = Board()
    private val boardHistory: BoardHistory = BoardHistory()
    private var selectedPiecePosition: PiecePosition? = null

    private lateinit var playerInfo: PlayerInfo
    private lateinit var gameFieldAdapter: GameFieldAdapter
    private lateinit var moveHistoryAdapter: MoveHistoryAdapter

    /**
     * @see Board.getField
     */
    fun getField(position: PiecePosition): Piece? {
        return board.getField(position)
    }

    /**
     * @see BoardHistory.getHistory
     */
    fun getBoardHistory(): MutableList<BoardMove> {
        return boardHistory.getHistory()
    }

    fun setGameFieldAdapter(gameFieldAdapter: GameFieldAdapter) {
        this.gameFieldAdapter = gameFieldAdapter
    }

    fun setMoveHistoryAdapter(moveHistoryAdapter: MoveHistoryAdapter) {
        this.moveHistoryAdapter = moveHistoryAdapter
    }

    fun setPlayerColor(playerInfo: PlayerInfo) {
        this.playerInfo = playerInfo
    }

    /**
     * Moves a piece to the target position
     *
     * @param from source position
     * @param to target position
     */
    private fun movePiece(from: PiecePosition, to: PiecePosition) {
        getField(from)!!.move()

        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/23
        boardHistory.push(board.createBoardMove(from, to))

        moveHistoryAdapter.notifyDataSetChanged()
        Log.d(LOG_TAG, "move piece from ${from.getNotation()} to ${to.getNotation()}")
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
        val possibleMoves = piece?.possibleMoves(board, position) ?: emptyList()
        val isPlayersPiece = (piece?.color == playerInfo.color)

        when {
            // handle first click
            (selectedPiecePosition == null && isPlayersPiece) -> setSelectedPiece(position, possibleMoves)
            // handle second click
            (selectedPiecePosition != null && !isPlayersPiece) -> tryToMovePiece(selectedPiecePosition!!, position)
            (isPlayersPiece && selectedPiecePosition != position) -> setSelectedPiece(position, possibleMoves)
            else -> setSelectedPiece()
        }
    }

    /**
     * Moves a chess piece from the source position to the target position, if the target position is valid.
     * @param fromPosition the source position of the chess piece
     * @param toPosition the target position to move the chess piece to
     */
    private fun tryToMovePiece(fromPosition: PiecePosition, toPosition: PiecePosition) {
        val piece = board.getField(fromPosition)
        val possibleMoves = piece?.possibleMoves(board, fromPosition) ?: emptyList()

        if (toPosition in possibleMoves) {
            movePiece(fromPosition, toPosition)

            if (BoardValidator.isKingCheckmate(board, piece!!.color.opponent())) {
                Log.d("", "Checkmate")
            }
            //TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/53

            setPlayerColor(playerInfo.nextPlayer())
        }
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
