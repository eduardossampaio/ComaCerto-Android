package apps.esampaio.com.comacerto.view.meals.edit

import android.view.Menu
import android.view.MenuItem
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity

class EditMealActivity : AddNewMealActivity() {
    companion object {
        val MEAL_INTENT_PARAM = "PARAM_MEAL"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_meal_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.save_meal_menu_item) {
            mealInteractor.onUpdatePressed(this.meal)
            return true
        } else if (item.itemId == R.id.delete_meal_menu_item) {
            mealInteractor.onDeletePressed(this.meal)
            return true
        }
        return false
    }

}
