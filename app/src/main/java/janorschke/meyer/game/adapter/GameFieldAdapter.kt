package janorschke.meyer.game.adapter

import android.content.Context
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import janorschke.meyer.R
import janorschke.meyer.databinding.GameFieldBinding
import janorschke.meyer.game.GameViewModel
import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.piece.model.Piece


class GameFieldAdapter(private val context: Context, private val gameViewModel: GameViewModel) : BaseAdapter() {
    private data class ViewHolder(val binding: GameFieldBinding, val view: View)

    override fun getCount(): Int {
        return Board.SIZE
    }

    override fun getItem(index: Int): Piece? {
        return gameViewModel.getField(PiecePosition(index))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(index: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var holder: ViewHolder
        if (convertView == null) {
            val binding = GameFieldBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            holder = ViewHolder(binding, binding.root)
            holder.view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val position = PiecePosition(index)
        holder.view.setBackgroundResource(getViewBackgroundColor(position))

        val piece = gameViewModel.getField(position)
        if (piece != null) {
            ContextCompat.getDrawable(context, piece.pieceInfo.imageId)!!.mutate().apply {
                if (piece.color == PieceColor.BLACK) {
                    val NEGATIVE = floatArrayOf(
                            -1.0f, 0f, 0f, 0f, 255f,  // red
                            0f, -1.0f, 0f, 0f, 255f,  // green
                            0f, 0f, -1.0f, 0f, 255f,  // blue
                            0f, 0f, 0f, 1.0f, 0f // alpha
                    )
                    this.colorFilter = ColorMatrixColorFilter(NEGATIVE)
                }
                holder.binding.btn.background = this
            }
        }
        holder.binding.btn.setOnClickListener { gameViewModel.onFieldClicked(position) }

        return holder.view
    }

    private fun getViewBackgroundColor(position: PiecePosition): Int {
        return if (position.row % 2 != position.col % 2) R.color.brown else R.color.beige
    }
}
