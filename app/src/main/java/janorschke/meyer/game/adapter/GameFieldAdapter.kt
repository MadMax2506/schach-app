package janorschke.meyer.game.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
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
    private class ViewHolder(val binding: GameFieldBinding, val view: View)

    private var possibleMoves: List<PiecePosition> = emptyList()

    override fun getCount(): Int {
        return Board.SIZE
    }

    override fun getItem(index: Int): Piece? {
        return gameViewModel.getField(PiecePosition(index))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setPossibleMoves(possibleMoves: List<PiecePosition>) {
        this.possibleMoves = possibleMoves
        notifyDataSetChanged()
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
        val piece = gameViewModel.getField(position)

        // field background
        holder.view.setBackgroundResource(getViewBackgroundColor(position))

        if (possibleMoves.contains(position)) {
            holder.binding.btn.background = ContextCompat.getDrawable(context, R.drawable.chess_possiblemove)!!.mutate()
        } else {
            holder.binding.btn.setBackgroundColor(Color.TRANSPARENT)
        }


        holder.binding.btn.background = getPieceBackground(piece, position)
        holder.binding.btn.setOnClickListener { gameViewModel.onFieldClicked(position) }

        return holder.view
    }

    private fun getViewBackgroundColor(position: PiecePosition): Int {
        return if (position.row % 2 != position.col % 2) R.color.brown else R.color.beige
    }

    private fun getPieceBackground(piece: Piece?, position: PiecePosition): Drawable? {
        if (piece == null) {
            return if (possibleMoves.contains(position)) {
                ContextCompat.getDrawable(context, R.drawable.chess_possiblemove)!!.mutate()
            } else null
        }

        val pieceImage = ContextCompat.getDrawable(context, piece.pieceInfo.imageId)!!.mutate().also { drawable ->
            if (piece.color == PieceColor.BLACK) {
                floatArrayOf(
                        -1.0f, 0f, 0f, 0f, 255f,  // red
                        0f, -1.0f, 0f, 0f, 255f,  // green
                        0f, 0f, -1.0f, 0f, 255f,  // blue
                        0f, 0f, 0f, 1.0f, 0f // alpha
                ).apply { drawable.colorFilter = ColorMatrixColorFilter(this) }
            }
        }

        val layers = mutableListOf<Drawable>()
        // Add beat indicator at the bottom
        if (possibleMoves.contains(position)) {
            ContextCompat.getDrawable(context, R.drawable.chess_possiblemove)!!
                    .mutate()
                    .also { drawable ->
                        floatArrayOf(
                                1f, 0.16f, 0.14f, 1f, 1f,  // red
                                0f, 0f, 0f, 0f, 0f,  // green
                                0f, 0f, 0f, 0f, 0f,  // blue
                                1f, 1f, 1f, 1f, 1f // alpha
                        ).apply { drawable.colorFilter = ColorMatrixColorFilter(this) }
                    }.apply { layers.add(this) }
        }

        // Add figure at the top
        layers.add(pieceImage)

        return LayerDrawable(layers.toTypedArray())
    }
}
