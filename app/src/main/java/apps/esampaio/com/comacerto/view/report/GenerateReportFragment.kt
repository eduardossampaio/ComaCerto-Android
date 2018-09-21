package apps.esampaio.com.comacerto.view.report

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
import kotlinx.android.synthetic.main.fragment_generate_reports.*
import org.jetbrains.anko.runOnUiThread
import java.util.*


class GenerateReportFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = GenerateReportFragment().apply {

        }
    }

    init{
        setHasOptionsMenu(true)
    }
    lateinit var shareMenuItem : MenuItem

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.generate_report_menu,menu)
        shareMenuItem = menu?.findItem(R.id.share_report_menu_item)!!
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.generate_report_menu_item){
           Toast.makeText(context,"Gerar relatório",Toast.LENGTH_SHORT).show()
            shareMenuItem.setEnabled(true)
        }else if(item?.itemId == R.id.share_report_menu_item){
            Toast.makeText(context,"Compartilhar relatório",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_generate_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        period_spinner.adapter = PeriodSpinnerAdapter(context!!)

        period_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                shareMenuItem.setEnabled(false)
            }

        }

    }


    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            context?.runOnUiThread {
               try {
                   period_expandable_layout.expand(true)
               }catch (e: Exception){

               }
            }
        },200)
    }
}

