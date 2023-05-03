package janorschke.meyer.game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import janorschke.meyer.R

class GameFieldAdapter(
    private val context: Context, private val boardViewModel: BoardViewModel
) : BaseAdapter() {

    override fun getCount(): Int {
        return BoardViewModel.BOARD_SIZE
    }

    override fun getItem(position: Int): PieceType {
        return boardViewModel.getFieldContent(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.game_field, null)!!
        }

        val button: Button = view.findViewById(R.id.btn)

        val content = boardViewModel.getFieldContent(position)
        button.setBackgroundResource(content.ressourceId)

        button.setOnClickListener {
            boardViewModel.onFieldClicked(position)
        }
        return view
    }
}
