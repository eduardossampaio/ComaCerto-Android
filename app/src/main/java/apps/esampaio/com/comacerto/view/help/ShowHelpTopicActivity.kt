package apps.esampaio.com.comacerto.view.help

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.HelpTopic
import apps.esampaio.com.comacerto.core.firebase.RemoteConfig
import kotlinx.android.synthetic.main.activity_show_help_topic.*
import kotlinx.android.synthetic.main.fragment_help.*
import java.io.IOException
import java.io.InputStream


class ShowHelpTopicActivity : AppCompatActivity() {
    companion object {
        val INTENT_PARAM_TOPIC_URL = "INTENT_PARAM_TOPIC_URL"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_help_topic)
        val helpTopic = intent.getSerializableExtra(INTENT_PARAM_TOPIC_URL) as HelpTopic
        markdown_view.loadFromUrl(helpTopic.contentUrl)


    }

//
//    private fun loadAssetTextAsString(context: Context, name: String): String? {
//        val assetManager = assets
//        val input: InputStream
//        var text = ""
//
//        try {
//            input = assetManager.open(name)
//
//            val size = input.available()
//            val buffer = ByteArray(size)
//            input.read(buffer)
//            input.close()
//
//            // byte buffer into a string
//            text = String(buffer)
//
//        } catch (e: IOException) {
//            // TODO Auto-generated catch block
//            e.printStackTrace()
//        }
//
//
//        Log.v("TAG", "Text File: $text")
//        return text
//    }
}
