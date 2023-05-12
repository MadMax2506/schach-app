package janorschke.meyer.game.adapter.beaten

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import janorschke.meyer.databinding.BeatenPieceBinding
import janorschke.meyer.game.GameViewModel
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.utils.PieceDrawables

/**
 * Adapter for the beaten pieces
 */
class BeatenPiecesAdapter(private val context: Context, private val gameViewModel: GameViewModel, private val color: PieceColor) : RecyclerView.Adapter<BeatenPiecesAdapter.ViewHolder>() {
    data class ViewHolder(val binding: BeatenPieceBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        gameViewModel.addBeatenPiecesAdapter(color, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        BeatenPieceBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
                .apply { return ViewHolder(this) }
    }

    override fun getItemCount(): Int = gameViewModel.numberOfBeatenPieceByColor(color)

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        PieceDrawables
                .getPiece(context, gameViewModel.getBeatenPieceByColor(index, color))
                .apply { holder.binding.beatenPiece.setImageDrawable(this) }

    }
}