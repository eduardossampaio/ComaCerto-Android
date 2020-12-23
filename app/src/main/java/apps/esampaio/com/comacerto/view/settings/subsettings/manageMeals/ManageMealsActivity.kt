package apps.esampaio.com.comacerto.view.settings.subsettings.manageMeals

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon
import apps.esampaio.com.comacerto.core.service.meal.mealtype.MealTypeIteractor
import apps.esampaio.com.comacerto.core.service.meal.mealtype.MealTypePresenter
import apps.esampaio.com.comacerto.core.service.meal.mealtype.MealTypeService
import apps.esampaio.com.comacerto.databinding.ActivityManageMealsBinding
import apps.esampaio.com.comacerto.view.BaseActivity
import apps.esampaio.com.comacerto.view.meals.register.adapter.MealListRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.settings.subsettings.manageMeals.dialog.AddMealDialog

class ManageMealsActivity : BaseActivity(), AddMealDialog.Listener, MealTypePresenter {
    lateinit var mealTypeIteractor: MealTypeIteractor;
    lateinit var layout: ActivityManageMealsBinding;
    var myMealsListAdapter = MyMealTypesRecyclerViewAdapter(emptyList());


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.manage_meals)
        mealTypeIteractor = MealTypeService(this, this);

        layout = ActivityManageMealsBinding.inflate(layoutInflater);
        layout.defaultMealListRv.layoutManager = GridLayoutManager(this, 5);
        layout.defaultMealListRv.adapter = object : MealListRecyclerViewAdapter(this) {
            override fun onBindViewHolder(imageRecyclerViewAdapterViewHolder: ImageRecyclerViewAdapterViewHolder, i: Int) {
                super.selectedItem = -1;
                super.onBindViewHolder(imageRecyclerViewAdapterViewHolder, i);
            }
        }
        val dialog = AddMealDialog(this)
        dialog.listener = this;
        layout.floatingActionButton.setOnClickListener {
            dialog.show();
        }
        layout.myMealsListRv.layoutManager = LinearLayoutManager(this)
        layout.myMealsListRv.adapter = myMealsListAdapter;
        setContentView(layout.root)
        mealTypeIteractor.onScreenLoaded()
    }

    private fun updateMealTypeListViews(mealTypeList: List<MealType>) {
        if (mealTypeList.isEmpty()) {
            layout.myMealsListRv.visibility = View.GONE
            layout.noMealsTextView.visibility = View.VISIBLE
        } else {
            layout.myMealsListRv.visibility = View.VISIBLE
            layout.noMealsTextView.visibility = View.GONE
        }
        myMealsListAdapter.update(mealTypeList);
    }

    override fun onMealAdded(mealName: String, icon: MealTypeIcon) {
        mealTypeIteractor.newMealTypeAdded(mealName, icon)
    }

    override fun updateCustomMealTypeList(mealTypeList: List<MealType>) {
        updateMealTypeListViews(mealTypeList);

    }


    override fun newMealTypeAdded(mealType: MealType) {
        Toast.makeText(this, "meal name :${mealType.name} icon : ${mealType.mealTypeIcon.iconName}", Toast.LENGTH_LONG).show();

    }
}