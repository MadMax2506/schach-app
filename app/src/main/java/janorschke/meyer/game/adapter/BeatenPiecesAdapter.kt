package janorschke.meyer.game.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import janorschke.meyer.databinding.BeatenPieceBinding
import janorschke.meyer.game.GameViewModel
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.model.Piece
import janorschke.meyer.game.piece.utils.PieceDrawables

class BeatenPiecesAdapter(private val context: Context, private val gameViewModel: GameViewModel, private val color: PieceColor) : BaseAdapter() {
    private data class ViewHolder(val binding: BeatenPieceBinding, val view: View)

    init {
        gameViewModel.addBeatenPiecesAdapter(this)
    }

    override fun getCount(): Int = gameViewModel.numberOfBeatenPieceByColor(color)

    override fun getItem(index: Int): Piece = gameViewModel.getBeatenPieceByColor(index, color)

    override fun getItemId(index: Int): Long = index.toLong()

    override fun getView(index: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var holder: ViewHolder
        if (convertView == null) {
            val binding = BeatenPieceBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            holder = ViewHolder(binding, binding.root)
            holder.view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        Log.d("aa", "${getCount()}")

        holder.binding.btn.background = PieceDrawables.getPiece(context, getItem(index))
        return holder.view
    }
}
