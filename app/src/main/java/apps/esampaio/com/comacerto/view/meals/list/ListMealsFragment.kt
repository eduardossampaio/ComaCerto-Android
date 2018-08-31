package apps.esampaio.com.comacerto.view.meals.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.meals.list.adapter.DailyMealViewPager
import kotlinx.android.synthetic.main.fragment_list_meals.*


class ListMealsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =  DailyMealViewPager(childFragmentManager)
        daily_meal_view_pager.adapter = adapter
        daily_meal_view_pager.currentItem = adapter.count -1
        daily_meal_view_pager.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        @JvmStatic
        fun newInstance() = ListMealsFragment().apply {

        }
    }
}