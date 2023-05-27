package janorschke.meyer.service.model.game

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.TimeMode
import janorschke.meyer.service.utils.board.PiecePosition

class Game(textResourceWhite: Int, textResourceBlack: Int, aiLevelWhite: AiLevel?, aiLevelBlack: AiLevel?, timeMode: TimeMode) {

    val playerWhite = Player(PieceColor.WHITE, textResourceWhite, aiLevelWhite)
    val playerBlack = Player(PieceColor.BLACK, textResourceBlack, aiLevelBlack)

    /**
     * Color of the player who is moving
     */
    private var color: PieceColor = PieceColor.WHITE

    /**
     * Current status of the game
     */
    private var status: GameStatus = GameStatus.RUNNING

    /**
     * Current selected position
     */
    private var selectedPosition: PiecePosition? = null

    /**
     * Possible moves for the piece on the selected position
     */
    private var possibleMoves: MutableList<PiecePosition> = mutableListOf()

    /**
     * Sets color of the current player
     *
     * @param color of the player
     */
    fun setColor(color: PieceColor) {
        this.color = color
    }

    /**
     * Sets status of the game
     *
     * @param status of the game
     */
    fun setStatus(status: GameStatus) {
        this.status = status
    }

    /**
     * Sets the selected piece and shows the possible moves through the GameFieldAdapter.
     *
     * @param position the position of the selected piece
     * @param possibleMoves the possible moves for the selected piece
     */
    fun setSelectedPiece(selectedPosition: PiecePosition? = null, possibleMoves: MutableList<PiecePosition> = mutableListOf()) {
        this.selectedPosition = selectedPosition
        this.possibleMoves = possibleMoves
    }

    fun getPlayer() = if (color == PieceColor.WHITE) playerWhite else playerBlack

    fun getColor() = color

    fun getStatus() = status

    fun getSelectedPosition() = selectedPosition

    fun getPossibleMoves() = possibleMoves
}