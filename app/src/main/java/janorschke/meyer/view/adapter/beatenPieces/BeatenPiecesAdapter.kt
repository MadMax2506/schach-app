package janorschke.meyer.view.adapter.beatenPieces

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import janorschke.meyer.databinding.BeatenPieceBinding
import janorschke.meyer.service.model.piece.Piece
import janorschke.meyer.service.utils.piece.PieceDrawables

/**
 * Display beaten pieces
 *
 * @param context of the application
 */
class BeatenPiecesAdapter(private val context: Context) : RecyclerView.Adapter<BeatenPiecesAdapter.ViewHolder>() {
    data class ViewHolder(val binding: BeatenPieceBinding) : RecyclerView.ViewHolder(binding.root)

    private var beatenPieces: MutableList<Piece> = mutableListOf()

    fun setBeatenPieces(beatenPieces: MutableList<Piece>) {
        this.beatenPieces = beatenPieces
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        BeatenPieceBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
                .apply { return ViewHolder(this) }
    }

    override fun getItemCount(): Int = beatenPieces.size

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        PieceDrawables
                .getPiece(context, beatenPieces[index])
                .apply { holder.binding.beatenPiece.setImageDrawable(this) }

    }
}