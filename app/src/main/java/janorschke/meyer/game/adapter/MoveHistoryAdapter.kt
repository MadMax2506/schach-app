package janorschke.meyer.game.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import janorschke.meyer.databinding.GameFieldBinding
import janorschke.meyer.game.GameViewModel

class MoveHistoryAdapter(private val context: Context, private val gameViewModel: GameViewModel) : BaseAdapter() {
    private data class ViewHolder(val binding: GameFieldBinding, val view: View)

    override fun getCount(): Int {
        return gameViewModel.getBoardHistory().size
    }

    override fun getItem(index: Int): Any {
        return gameViewModel.getBoardHistory()[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getView(index: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }
}