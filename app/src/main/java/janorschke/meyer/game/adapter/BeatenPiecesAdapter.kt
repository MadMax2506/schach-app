package janorschke.meyer.game.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import janorschke.meyer.databinding.GameFieldBinding
import janorschke.meyer.game.GameViewModel
import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.piece.model.Piece

class BeatenPiecesAdapter(private val context: Context, private val gameViewModel: GameViewModel) : BaseAdapter() {
    private data class ViewHolder(val binding: GameFieldBinding, val view: View)

    init {
        gameViewModel.addBeatenPiecesAdapter(this)
    }

    override fun getCount(): Int = Board.SIZE

    override fun getItem(index: Int): Piece? = gameViewModel.getField(PiecePosition(index))

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(index: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var holder: ViewHolder
        if (convertView == null) {
            val binding = GameFieldBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            holder = ViewHolder(binding, binding.root)
            holder.view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        return holder.view
    }
}
