package janorschke.meyer.view.listener

import android.view.MenuItem

class GameSurrenderOnClickListener : MenuItem.OnMenuItemClickListener {
    override fun onMenuItemClick(item: MenuItem): Boolean {
        // TODO Dialog öffnen "möchten Sie wirklich ein aufgeben?"
        //  mit ja, nein entscheidung
        return true
    }
}