package janorschke.meyer.view.dialog

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import janorschke.meyer.databinding.DialogPromotionBinding
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.piece.lineMoving.Queen

class PromotionDialog : BaseDialog() {
    private lateinit var binding: DialogPromotionBinding

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogPromotionBinding.inflate(layoutInflater)

        val pieceColor: PieceColor = requireArguments().requiredSerializable(ARG_PIECE_COLOR)

        setButtonOnClickHandlers(pieceColor)

        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    private fun setButtonOnClickHandlers(pieceColor: PieceColor) {
        binding.buttonQueenPromotion.setOnClickListener {
            // TODO return the Piece
        }
    }
}