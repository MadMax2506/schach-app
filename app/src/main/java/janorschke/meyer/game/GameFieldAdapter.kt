package janorschke.meyer.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import janorschke.meyer.R

class GameFieldAdapter(private val viewModel: BoardViewModel) : BaseAdapter() {

    override fun getCount(): Int {
        return viewModel.boardSize
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val button: Button = if (convertView == null) {
            val layoutInflater = LayoutInflater.from(parent?.context)
            val view = layoutInflater.inflate(R.layout.game_field, parent, false)
            view.findViewById(R.id.btn)
        } else {
            convertView.findViewById(R.id.btn)
        }

        val content = viewModel.getFieldContent(position)
        when(content)  {
            PieceType.PAWN_WHITE -> button.setBackgroundResource(R.drawable.chess_plt45)
            PieceType.PAWN_BLACK -> button.setBackgroundResource(R.drawable.chess_pdt45)
            PieceType.KNIGHT_WHITE -> button.setBackgroundResource(R.drawable.chess_nlt45)
            PieceType.KNIGHT_BLACK -> button.setBackgroundResource(R.drawable.chess_ndt45)
            PieceType.BISHOP_WHITE -> button.setBackgroundResource(R.drawable.chess_blt45)
            PieceType.BISHOP_BLACK -> button.setBackgroundResource(R.drawable.chess_bdt45)
            PieceType.ROOK_WHITE -> button.setBackgroundResource(R.drawable.chess_rlt45)
            PieceType.ROOK_BLACK -> button.setBackgroundResource(R.drawable.chess_rdt45)
            PieceType.QUEEN_WHITE -> button.setBackgroundResource(R.drawable.chess_qlt45)
            PieceType.QUEEN_BLACK -> button.setBackgroundResource(R.drawable.chess_qdt45)
            PieceType.KING_WHITE -> button.setBackgroundResource(R.drawable.chess_klt45)
            PieceType.KING_BLACK -> button.setBackgroundResource(R.drawable.chess_kdt45)
            else -> {button.setBackgroundResource(R.color.purple_200)} // TODO
        }

        button.setOnClickListener {
            viewModel.onFieldClicked(position)
        }
        return button
    }
}
