package janorschke.meyer.view.dialog

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import janorschke.meyer.databinding.DialogPromotionBinding
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.move.PossibleMove
import janorschke.meyer.service.model.game.piece.Knight
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.model.game.piece.lineMoving.Bishop
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.model.game.piece.lineMoving.Rook
import janorschke.meyer.viewModel.GameViewModel

class PromotionDialog : BaseDialog() {
    private lateinit var binding: DialogPromotionBinding

    companion object {
        private const val ARG_PIECE_COLOR = "pieceColor"
        private const val ARG_POSSIBLE_MOVE = "possibleMove"

        fun newInstance(pieceColor: PieceColor, possibleMove: PossibleMove): PromotionDialog {
            return PromotionDialog().also { dialog ->
                dialog.arguments = Bundle().also { bundle ->
                    bundle.putSerializable(ARG_PIECE_COLOR, pieceColor)
                    bundle.putSerializable(ARG_POSSIBLE_MOVE, possibleMove)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogPromotionBinding.inflate(layoutInflater)

        val pieceColor: PieceColor = requireArguments().requiredSerializable(ARG_PIECE_COLOR)
        val possibleMove: PossibleMove = requireArguments().requiredSerializable(ARG_POSSIBLE_MOVE)

        setButtonOnClickHandlers(pieceColor, possibleMove)

        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    /**
     * Sets the onClickListeners of the Buttons of the Dialog
     *
     * @param pieceColor
     * @param possibleMove
     */
    private fun setButtonOnClickHandlers(pieceColor: PieceColor, possibleMove: PossibleMove) {
        binding.buttonQueenPromotion.setOnClickListener { onPromotionSelected(Queen(pieceColor), possibleMove) }
        binding.buttonRookPromotion.setOnClickListener { onPromotionSelected(Rook(pieceColor), possibleMove) }
        binding.buttonBishopPromotion.setOnClickListener { onPromotionSelected(Bishop(pieceColor), possibleMove) }
        binding.buttonKnightPromotion.setOnClickListener { onPromotionSelected(Knight(pieceColor), possibleMove) }
    }

    /**
     * This method gets executed when the player decides the piece-type to promote to
     *
     * @param selectedPiece that the pawn is going to be promoted to
     * @param possibleMove the move to execute
     */
    private fun onPromotionSelected(
            selectedPiece: Piece,
            possibleMove: PossibleMove
    ) {
        possibleMove.promotionTo = selectedPiece
        GameViewModel.getInstance().movePiece(possibleMove)
        dismiss()
    }
}