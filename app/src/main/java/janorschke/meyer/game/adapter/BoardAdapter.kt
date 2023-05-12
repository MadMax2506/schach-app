package janorschke.meyer.game.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import janorschke.meyer.R
import janorschke.meyer.databinding.GameFieldBinding
import janorschke.meyer.game.GameViewModel
import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.model.Piece
import janorschke.meyer.game.piece.utils.PieceDrawables
import janorschke.meyer.game.piece.utils.PiecePosition

/**
 * Adapter for the board
 */
class BoardAdapter(private val context: Context, private val gameViewModel: GameViewModel) : BaseAdapter() {
    private data class ViewHolder(val binding: GameFieldBinding, val view: View)

    private var possibleMoves: List<PiecePosition> = emptyList()

    init {
        gameViewModel.setBoardAdapter(this)
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

        val position = PiecePosition(index)
        val piece = getItem(index)

        holder.view.setBackgroundResource(getViewBackgroundColor(position))
        holder.binding.btn.background = getPieceBackground(piece, position)
        holder.binding.btn.setOnClickListener { gameViewModel.onFieldClicked(position) }

        return holder.view
    }

    fun setPossibleMoves(possibleMoves: List<PiecePosition>) {
        this.possibleMoves = possibleMoves
        notifyDataSetChanged()
    }

    private fun getViewBackgroundColor(position: PiecePosition): Int = if (position.row % 2 != position.col % 2) R.color.brown else R.color.beige

    private fun getPieceBackground(piece: Piece?, position: PiecePosition): Drawable? {
        val isPossibleMove = possibleMoves.contains(position)
        if (piece == null) {
            return if (isPossibleMove) PieceDrawables.getPossibleMove(context) else null
        }

        return if (isPossibleMove) PieceDrawables.getAttackingPossibleMove(context, piece) else PieceDrawables.getPiece(context, piece)
    }
}
