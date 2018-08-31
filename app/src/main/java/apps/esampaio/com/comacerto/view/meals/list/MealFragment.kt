package apps.esampaio.com.comacerto.view.meals.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.meals.list.adapter.ListDailyMealAdapter
import kotlinx.android.synthetic.main.fragment_daily_meals.*

class MealFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_daily_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daily_meals_recycler_view.layoutManager = LinearLayoutManager(activity)
        daily_meals_recycler_view.adapter = ListDailyMealAdapter(activity!!, emptyList())
    }

    override fun onResume() {
        super.onResume()


    }


    companion object {
        @JvmStatic
        fun newInstance() = MealFragment().apply {

        }
    }
}