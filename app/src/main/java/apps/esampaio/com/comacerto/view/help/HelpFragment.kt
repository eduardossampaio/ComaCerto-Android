package apps.esampaio.com.comacerto.view.help

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import apps.esampaio.com.comacerto.R
import kotlinx.android.synthetic.main.fragment_help.*


class HelpFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topicsNames = context!!.resources.getStringArray(R.array.help_topics)
        val topicsUrls = context!!.resources.getStringArray(R.array.help_topics_urls)
        val adapter = ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,topicsNames)
        help_topics.adapter  = adapter

        help_topics.setOnItemClickListener { adapterView, view, position, id ->
            val intent = Intent(context,ShowHelpTopicActivity::class.java)
            val topicUrl = topicsUrls[position]
            intent.putExtra(ShowHelpTopicActivity.INTENT_PARAM_TOPIC_URL, topicUrl)
            startActivity(intent)
        }

    }
    companion object {
        @JvmStatic
        fun newInstance() = HelpFragment().apply {

        }
    }
}
