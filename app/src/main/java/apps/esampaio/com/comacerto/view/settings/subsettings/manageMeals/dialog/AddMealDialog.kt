package apps.esampaio.com.comacerto.view.settings.subsettings.manageMeals.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon
import apps.esampaio.com.comacerto.databinding.DialogNewMealTypeBinding
import apps.esampaio.com.comacerto.view.settings.subsettings.manageMeals.MealIconRecyclerViewAdapter
import org.w3c.dom.Text

class AddMealDialog(private val myContext: Activity) : Dialog(myContext), TextWatcher {

    interface Listener {
        fun onMealAdded(mealName:String, icon: MealTypeIcon);
    }

    private lateinit var layout: DialogNewMealTypeBinding
    var listener : Listener? = null;
    lateinit var recyclerViewAdapter : MealIconRecyclerViewAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        layout = DialogNewMealTypeBinding.inflate(layoutInflater)
        setContentView(layout.root)
        recyclerViewAdapter = MealIconRecyclerViewAdapter(myContext)
        layout.mealListRV.adapter = recyclerViewAdapter
        layout.mealListRV.layoutManager = GridLayoutManager(myContext,3)

        layout.cancelButton.setOnClickListener { cancel()}

        layout.saveButton.setOnClickListener {
            listener?.onMealAdded(layout.mealNameEditText.text.toString(), recyclerViewAdapter.getSelectedIcon())
            cancel()
        }
        layout.mealNameEditText.addTextChangedListener(this)
        setCancelable(false)
        validateFields()

    }

    private fun validateFields() {
        var invalid = layout.mealNameEditText.text.isBlank();
        layout.saveButton.isEnabled = !invalid;
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        validateFields();
    }

    override fun afterTextChanged(p0: Editable?) {}
}