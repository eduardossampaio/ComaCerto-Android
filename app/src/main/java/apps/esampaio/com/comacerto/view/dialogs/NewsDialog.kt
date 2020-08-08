package apps.esampaio.com.comacerto.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.TextView

import apps.esampaio.com.comacerto.R
import kotlinx.android.synthetic.main.news_dialog.*

class NewsDialog(context: Context) : Dialog(context, R.style.Theme_Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.news_dialog)
        val title = findViewById<TextView>(R.id.txtTitle)
        btnPositive.setOnClickListener {
            dismiss()
        }
        setCancelable(false)
    }
}
