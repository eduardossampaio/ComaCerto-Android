package apps.esampaio.com.comacerto.view.dialogs

import android.app.Dialog
import android.content.Context
import android.support.v4.content.ContextCompat
import apps.esampaio.com.comacerto.MyApplication

import com.droidbyme.dialoglib.DroidDialog

import apps.esampaio.com.comacerto.R

class Dialogs {
    companion object {
        fun openAlertDialog(context: Context, message: String, onOkPressed: (() -> Unit)? = null) {
            DroidDialog.Builder(context)
                    .icon(R.drawable.tick_100)
                    .title(context.getString(R.string.app_name))
                    .content(message)
                    .cancelable(true, true)
                    .positiveButton(context.getString(R.string.dialog_ok_text)) { droidDialog ->
                        droidDialog.dismiss()
                        onOkPressed?.invoke()
                    }
                    .color(ContextCompat.getColor(context, R.color.primary),
                            ContextCompat.getColor(context, R.color.white),
                            ContextCompat.getColor(context, R.color.primary_dark))
                    .show()
        }


        fun openAskDialog(context: Context, message: String, onYesPressed: (() -> Unit)? = null, onNoPressed: (() -> Unit)? = null) {
            DroidDialog.Builder(context)
                    .icon(R.drawable.ic_dialog_question)
                    .title(context.getString(R.string.app_name))
                    .content(message)
                    .cancelable(false, false)
                    .positiveButton(context.getString(R.string.dialog_yes_text)) { droidDialog ->
                        droidDialog.dismiss()
                        onYesPressed?.invoke()
                    }.negativeButton(context.getString(R.string.dialog_no_text)) { droidDialog ->
                        droidDialog.dismiss()
                        onNoPressed?.invoke()
                    }
                    .color(ContextCompat.getColor(context, R.color.dialog_alert_color),
                            ContextCompat.getColor(context, R.color.white),
                            ContextCompat.getColor(context, R.color.dialog_alert_color))
                    .show()
        }

        fun openErrorDialog(context: Context, message: String, onOkPressed: (() -> Unit)? = null) {
            DroidDialog.Builder(context)
                    .icon(R.drawable.ic_error_icon)
                    .title(context.getString(R.string.app_name))
                    .content(message)
                    .cancelable(true, true)
                    .positiveButton(context.getString(R.string.dialog_ok_text)) { droidDialog ->
                        droidDialog.dismiss()
                        onOkPressed?.invoke()
                    }
                    .color(ContextCompat.getColor(context, R.color.text_error),
                            0,
                            ContextCompat.getColor(context, R.color.text_error))
                    .show()
        }

        fun openLoadingDialog(context: Context, message: String): Dialog {
            val loadingDialog = LoadingDialog(context, message);
            loadingDialog.show()
            return loadingDialog
        }
    }
}
