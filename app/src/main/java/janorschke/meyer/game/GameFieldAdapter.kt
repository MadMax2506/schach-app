package janorschke.meyer.game

import android.content.Context
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import androidx.core.content.ContextCompat
import janorschke.meyer.R
import janorschke.meyer.game.piece.Piece
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PiecePosition


class GameFieldAdapter(
    private val context: Context,
    private val boardViewModel: BoardViewModel
) : BaseAdapter() {

    override fun getCount(): Int {
        return BoardViewModel.BOARD_SIZE
    }

    override fun getItem(index: Int): Piece? {
        return boardViewModel.getField(PiecePosition(index))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/12
    override fun getView(index: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = if (convertView == null) {
            val layoutInflater = LayoutInflater.from(parent?.context)
            layoutInflater.inflate(R.layout.game_field, parent, false)
        } else {
            convertView
        }

        val position = PiecePosition(index)

        view.setBackgroundResource(getViewBackgroundColor(position))

        val button: Button = view.findViewById(R.id.btn)
        val piece = boardViewModel.getField(position)
        if (piece != null) {
            val drawable = ContextCompat.getDrawable(context, piece.getImageId())!!.mutate()

            if (piece.color == PieceColor.BLACK) {
                val NEGATIVE = floatArrayOf(
                    -1.0f, 0f, 0f, 0f, 255f,  // red
                    0f, -1.0f, 0f, 0f, 255f,  // green
                    0f, 0f, -1.0f, 0f, 255f,  // blue
                    0f, 0f, 0f, 1.0f, 0f // alpha
                )
                drawable.colorFilter = ColorMatrixColorFilter(NEGATIVE)
            }
            button.background = drawable
        }
        button.setOnClickListener { boardViewModel.onFieldClicked(position) }

        return view
    }

    private fun getViewBackgroundColor(position: PiecePosition): Int {
        return if (position.row % 2 == 0 && position.col % 2 == 1 || position.row % 2 == 1 && position.col % 2 == 0) {
            R.color.brown
        } else R.color.beige
    }
}
