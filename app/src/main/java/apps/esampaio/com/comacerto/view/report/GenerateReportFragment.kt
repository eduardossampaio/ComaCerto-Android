package apps.esampaio.com.comacerto.view.report

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.esampaio.com.comacerto.R
import kotlinx.android.synthetic.main.fragment_generate_reports.*
import org.jetbrains.anko.runOnUiThread


class GenerateReportFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_generate_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        period_spinner.adapter = PeriodSpinnerAdapter(context!!)
    }
    companion object {
        @JvmStatic
        fun newInstance() = GenerateReportFragment().apply {

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
        //
    }

    override fun onPause() {
        super.onPause()
        //period_expandable_layout.collapse()
    }
}
