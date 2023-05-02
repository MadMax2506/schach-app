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
        button.setOnClickListener {
            viewModel.onFieldClicked(position)
        }
        button.text = viewModel.getFieldContent(position).value
        return button
    }
}
