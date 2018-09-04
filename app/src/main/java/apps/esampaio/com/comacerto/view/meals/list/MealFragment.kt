package apps.esampaio.com.comacerto.view.meals.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.view.meals.list.adapter.ListDailyMealAdapter
import kotlinx.android.synthetic.main.fragment_daily_meals.*

class MealFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_daily_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val meals = emptyList<Meal>()
        if (meals.isEmpty()){
            daily_meals_recycler_view.visibility = View.GONE
            no_meals_registered_text_view.visibility = View.VISIBLE
        }else {
            daily_meals_recycler_view.visibility = View.VISIBLE
            no_meals_registered_text_view.visibility = View.GONE
            daily_meals_recycler_view.layoutManager = LinearLayoutManager(activity)
            daily_meals_recycler_view.adapter = ListDailyMealAdapter(activity!!, meals)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = MealFragment().apply {

        }
    }
}