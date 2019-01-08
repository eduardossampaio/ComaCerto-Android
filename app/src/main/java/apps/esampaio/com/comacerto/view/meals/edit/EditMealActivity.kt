package apps.esampaio.com.comacerto.view.meals.edit

import android.view.Menu
import android.view.MenuItem
import android.view.View
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
import kotlinx.android.synthetic.main.activity_add_new_meal.*

class EditMealActivity : AddNewMealActivity() {
    companion object {
        val MEAL_INTENT_PARAM = "PARAM_MEAL"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_meal_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_meal_menu_item) {
            mealIteractor.onDeletePressed(this.meal)
            return true
        }
        return false
    }

    override fun onSaveClick(view: View){
        meal.whatDoing = what_doing_text_view.text.toString()
        mealIteractor.onUpdatePressed(this.meal)
    }

}
