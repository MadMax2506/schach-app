package janorschke.meyer.view.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import janorschke.meyer.R
import janorschke.meyer.databinding.GameFieldBinding
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.model.game.board.move.PossibleMove
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.utils.piece.PieceDrawables.getAttackingPossibleMove
import janorschke.meyer.service.utils.piece.PieceDrawables.getLastMovingPiece
import janorschke.meyer.service.utils.piece.PieceDrawables.getPiece
import janorschke.meyer.service.utils.piece.PieceDrawables.getPossibleMove
import janorschke.meyer.service.utils.piece.PieceDrawables.getSelectedPiece
import janorschke.meyer.viewModel.GameViewModel

/**
 * Adapter to display a chess board
 *
 * @param context of the application
 */
class BoardAdapter(private val context: Context, private val gameViewModel: GameViewModel) : BaseAdapter() {
    private data class ViewHolder(val binding: GameFieldBinding, val view: View)

    private var lastAiMove: Move? = null
    private var position: Position? = null

    private lateinit var fields: Array<Array<Piece?>>
    private lateinit var possibleMoves: Sequence<PossibleMove>

    fun setFields(fields: Array<Array<Piece?>>) {
        this.fields = fields
        notifyDataSetChanged()
    }

    fun setPossibleMoves(possibleMoves: Sequence<PossibleMove>) {
        this.possibleMoves = possibleMoves
        notifyDataSetChanged()
    }

    fun setLastMove(lastMove: Move?) {
        this.lastAiMove = lastMove
        notifyDataSetChanged()
    }

    fun setSelectedPosition(position: Position?) {
        this.position = position
        notifyDataSetChanged()
    }

    override fun getCount(): Int = Board.SIZE

    override fun getItem(index: Int) = Position(index).let { fields[it.row][it.col] }

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

        val position = Position(index)
        val piece = getItem(index)

        holder.view.setBackgroundResource(getViewBackgroundColor(position))
        holder.binding.btn.background = getPieceBackground(piece, position)
        holder.binding.btn.setOnClickListener { gameViewModel.onFieldClick(position) }

        return holder.view
    }

    private fun getViewBackgroundColor(position: Position): Int = if (position.row % 2 != position.col % 2) R.color.red_brown else R.color.beige

    private fun getPieceBackground(piece: Piece?, position: Position): Drawable? {
        val isPossibleMove = possibleMoves.map { it.to.position }.contains(position)
        return when {
            piece == null && isPossibleMove -> getPossibleMove(context)
            piece == null -> null
            isPossibleMove -> getAttackingPossibleMove(context, piece)
            this.position == position -> getSelectedPiece(context, piece)
            lastAiMove?.to?.position == position -> getLastMovingPiece(context, piece)
            else -> getPiece(context, piece)
        }
    }
}
