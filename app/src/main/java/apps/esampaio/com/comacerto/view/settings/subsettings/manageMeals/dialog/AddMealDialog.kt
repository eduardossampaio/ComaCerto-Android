package apps.esampaio.com.comacerto.view.settings.subsettings.manageMeals.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon
import apps.esampaio.com.comacerto.databinding.DialogNewMealTypeBinding
import apps.esampaio.com.comacerto.view.settings.subsettings.manageMeals.MealIconRecyclerViewAdapter

class AddMealDialog(private val myContext: Activity) : Dialog(myContext) {

    interface Listener {
        fun onMealAdded(mealName:String, icon: MealTypeIcon);
    }
    var listener : Listener? = null;
    lateinit var recyclerViewAdapter : MealIconRecyclerViewAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        val layout = DialogNewMealTypeBinding.inflate(layoutInflater)
        setContentView(layout.root)
        recyclerViewAdapter = MealIconRecyclerViewAdapter(myContext)
        layout.mealListRV.adapter = recyclerViewAdapter
        layout.mealListRV.layoutManager = GridLayoutManager(myContext,3)

        layout.cancelButton.setOnClickListener { cancel()}

        layout.saveButton.setOnClickListener {
            listener?.onMealAdded(layout.mealNameEditText.text.toString(), recyclerViewAdapter.getSelectedIcon())
            cancel()
        }
        setCancelable(false)

    }
}