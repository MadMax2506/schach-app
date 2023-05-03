package janorschke.meyer.game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import janorschke.meyer.R
import janorschke.meyer.game.piece.Piece
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

        val button: Button = view.findViewById(R.id.btn)

        val position = PiecePosition(index)
        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/13
        val piece = boardViewModel.getField(position)

        view.setBackgroundResource(getViewBackgroundColor(position))

        // Figure is on the current position
        if (piece != null) button.setBackgroundResource(piece.getImageId())
        button.setOnClickListener { boardViewModel.onFieldClicked(position) }

        return view
    }

    private fun getViewBackgroundColor(position: PiecePosition): Int {
        return if (position.row % 2 == 0 && position.col % 2 == 1 || position.row % 2 == 1 && position.col % 2 == 0) {
            R.color.purple_700
        } else R.color.white
    }
}
