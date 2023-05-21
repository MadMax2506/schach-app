package janorschke.meyer.view.listener

import android.view.MenuItem

class GameVoteDrawOnClickListener : MenuItem.OnMenuItemClickListener {
    override fun onMenuItemClick(item: MenuItem): Boolean {
        // TODO Dialog öffnen "möchten Sie wirklich ein Unentschieden anbieten?"
        //  mit ja, nein entscheidung
        return true
    }
}