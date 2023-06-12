package janorschke.meyer.view.dialog

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import janorschke.meyer.databinding.DialogPromotionBinding
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.piece.Knight
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.model.game.piece.lineMoving.Bishop
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.model.game.piece.lineMoving.Rook

class PromotionDialog : BaseDialog() {
    private lateinit var binding: DialogPromotionBinding
    private lateinit var promotionListener: PromotionListener

    interface PromotionListener {
        fun onPromotionSelected(piece: Piece)
    }

    companion object {
        private const val ARG_PIECE_COLOR = "pieceColor"

        fun newInstance(pieceColor: PieceColor): PromotionDialog {
            return PromotionDialog().also { dialog ->
                dialog.arguments = Bundle().also { bundle ->
                    bundle.putSerializable(ARG_PIECE_COLOR, pieceColor)
                }
            }
        }
    }

    fun setPromotionListener(listener: PromotionListener) {
        promotionListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogPromotionBinding.inflate(layoutInflater)

        val pieceColor: PieceColor = requireArguments().requiredSerializable(ARG_PIECE_COLOR)

        setButtonOnClickHandlers(pieceColor)

        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    private fun setButtonOnClickHandlers(pieceColor: PieceColor) {
        binding.buttonQueenPromotion.setOnClickListener {
            val selectedPiece = Queen(pieceColor)
            promotionListener.onPromotionSelected(selectedPiece)
            dismiss()
        }

        binding.buttonRookPromotion.setOnClickListener {
            val selectedPiece = Rook(pieceColor)
            promotionListener.onPromotionSelected(selectedPiece)
            dismiss()
        }

        binding.buttonBishopPromotion.setOnClickListener {
            val selectedPiece = Bishop(pieceColor)
            promotionListener.onPromotionSelected(selectedPiece)
            dismiss()
        }

        binding.buttonKnightPromotion.setOnClickListener {
            val selectedPiece = Knight(pieceColor)
            promotionListener.onPromotionSelected(selectedPiece)
            dismiss()
        }
    }
}