package janorschke.meyer.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import janorschke.meyer.databinding.MoveHistoryFieldBinding
import janorschke.meyer.service.model.Move
import janorschke.meyer.service.model.piece.Pawn

/**
 * Adapter for the move history
 *
 * @param context of the application
 */
class MoveHistoryAdapter(private val context: Context) : BaseAdapter() {
    private data class ViewHolder(val binding: MoveHistoryFieldBinding, val view: View)

    private var moveHistory: MutableList<Move> = mutableListOf()

    fun setMoveHistory(moveHistory: MutableList<Move>) {
        this.moveHistory = moveHistory
        notifyDataSetChanged()
    }

    override fun getCount(): Int = moveHistory.size

    override fun getItem(index: Int): Move = moveHistory[index]

    override fun getItemId(index: Int): Long = index.toLong()

    override fun getView(index: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var holder: ViewHolder
        if (convertView == null) {
            val binding = MoveHistoryFieldBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            holder = ViewHolder(binding, binding.root)
            holder.view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.binding.notation.text = getMoveNotation(getItem(index))

        return holder.view
    }

    /**
     * @param move in the history
     * @return a valid notion for the move
     */
    private fun getMoveNotation(move: Move): String {
        StringBuilder()
                .apply {
                    this.append(context.resources.getString(move.fromPiece.pieceInfo.notationId))
                    if (move.toPiece != null) {
                        if (move.fromPiece is Pawn) this.append(move.from.getColNotation())
                        this.append("x")
                    }
                    this.append(move.to.getNotation())

                    return this.toString()
                }
    }
}