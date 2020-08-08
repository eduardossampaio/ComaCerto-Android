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
import apps.esampaio.com.comacerto.core.entity.HelpTopic
import apps.esampaio.com.comacerto.core.firebase.RemoteConfig
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_help.*
import android.net.Uri.fromParts
import android.net.Uri
import android.widget.Toast


class HelpFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val helpTopicsJson = RemoteConfig.getInstance().helpTopics
        var helpTopics = Gson().fromJson<Array<HelpTopic>>(helpTopicsJson, Array<HelpTopic>::class.java)
        if ( helpTopics == null){
            helpTopics = emptyArray();
        }
        val adapter = ArrayAdapter<HelpTopic>(context,android.R.layout.simple_list_item_1,helpTopics)
        help_topics.adapter  = adapter

        help_topics.setOnItemClickListener { adapterView, view, position, id ->
            val intent = Intent(context,ShowHelpTopicActivity::class.java)
            val topicUrl = helpTopics[position]
            intent.putExtra(ShowHelpTopicActivity.INTENT_PARAM_TOPIC_URL, topicUrl)
            startActivity(intent)
        }

        talk_with_developer.setOnClickListener { sendOpenEmailIntent() }

    }

    private fun sendOpenEmailIntent() {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.contact_email) , null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_app_name))
        startActivityForResult(Intent.createChooser(emailIntent, getString(R.string.send_email)), 123);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 123){
            Toast.makeText(context,"Obrigado :D", Toast.LENGTH_LONG).show();
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = HelpFragment().apply {

        }
    }
}
