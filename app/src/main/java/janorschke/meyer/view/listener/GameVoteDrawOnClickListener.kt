package janorschke.meyer.view.listener

import android.content.Context
import android.view.MenuItem

class GameVoteDrawOnClickListener(private val context: Context) : MenuItem.OnMenuItemClickListener {
    override fun onMenuItemClick(item: MenuItem): Boolean {
        // TODO Dialog öffnen "möchten Sie wirklich ein Unentschieden anbieten?"
        //  mit ja, nein entscheidung
        return true
    }
}