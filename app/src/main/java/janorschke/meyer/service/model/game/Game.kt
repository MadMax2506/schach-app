package janorschke.meyer.service.model.game

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.board.PossibleMove
import janorschke.meyer.service.model.game.player.AiPlayer
import janorschke.meyer.service.model.game.player.PlayerFactory

class Game(playerNameWhite: String, playerNameBlack: String, aiLevelWhite: AiLevel?, aiLevelBlack: AiLevel?) {
    val playerWhite = PlayerFactory(PieceColor.WHITE, playerNameWhite, aiLevelWhite).create()
    val playerBlack = PlayerFactory(PieceColor.BLACK, playerNameBlack, aiLevelBlack).create()

    private var activeColor: PieceColor = PieceColor.WHITE
    private var status: GameStatus = GameStatus.RUNNING

    /**
     * Current selected position
     */
    private var selectedPosition: PiecePosition? = null

    /**
     * Possible moves for the piece on the selected position
     */
    private var possibleMoves: MutableList<PossibleMove> = mutableListOf()

    val activePlayer get() = if (activeColor == PieceColor.WHITE) playerWhite else playerBlack
    val aiPlayer get() = if (playerWhite is AiPlayer) playerWhite else playerBlack as AiPlayer

    /**
     * Set the next player to move pieces
     */
    fun setNextPlayer() {
        activeColor = activeColor.opponent()
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
     * @param selectedPosition the position of the selected piece
     * @param possibleMoves the possible moves for the selected piece
     */
    fun setSelectedPiece(selectedPosition: PiecePosition? = null, possibleMoves: MutableList<PossibleMove> = mutableListOf()) {
        this.selectedPosition = selectedPosition
        this.possibleMoves = possibleMoves
    }

    fun getActiveColor() = activeColor

    fun getStatus() = status

    fun getSelectedPosition() = selectedPosition

    fun getPossibleMoves() = possibleMoves
}