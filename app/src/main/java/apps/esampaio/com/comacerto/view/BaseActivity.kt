package apps.esampaio.com.comacerto.view

import android.app.Dialog
import android.support.v7.app.AppCompatActivity

import apps.esampaio.com.comacerto.core.service.ViewPresenter
import apps.esampaio.com.comacerto.view.dialogs.Dialogs

abstract class BaseActivity : AppCompatActivity(), ViewPresenter {
    var loadingDialog : Dialog? = null

    override fun showAlert(message: String, onOkPressed: (() -> Unit)?) {
        Dialogs.openAlertDialog(this, message,onOkPressed)
    }

    override fun showAskDialog(message: String, onYesPressed: (() -> Unit)?, onNoPressed: (() -> Unit)?) {
        Dialogs.openAskDialog(this, message,onYesPressed,onNoPressed)
    }
    override fun showError(message: String,onOkPressed: (() -> Unit)?) {
        Dialogs.openErrorDialog(this, message,onOkPressed)
    }

    override fun showLoading(message: String) {
        loadingDialog = Dialogs.openLoadingDialog(this,message);
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun finishScreen() {
        finish()
    }
}
