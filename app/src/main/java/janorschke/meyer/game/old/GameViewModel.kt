package janorschke.meyer.game.old

private const val LOG_TAG = "GameViewModel"

/**
 * The GameViewModel represents the view model for a chess game.
 * It manages the game state, handles user input, and communicates with the view layer through a GameFieldAdapter object.
 */
/*class GameViewModel(application: Application) : AndroidViewModel(application) {
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
}
*/