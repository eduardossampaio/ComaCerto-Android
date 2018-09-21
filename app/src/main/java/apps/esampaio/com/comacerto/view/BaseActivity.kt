package apps.esampaio.com.comacerto.view

import android.support.v7.app.AppCompatActivity

import apps.esampaio.com.comacerto.core.service.ViewPresenter
import apps.esampaio.com.comacerto.view.dialogs.Dialogs

abstract class BaseActivity : AppCompatActivity(), ViewPresenter {
    override fun showAlert(message: String, onOkPressed: (() -> Unit)?) {
        Dialogs.openAlertDialog(this, message,onOkPressed)
    }

    override fun showAskDialog(message: String, onYesPressed: (() -> Unit)?, onNoPressed: (() -> Unit)?) {
        Dialogs.openAskDialog(this, message,onYesPressed,onNoPressed)
    }
    override fun showError(message: String) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun finishScreen() {
        finish()
    }
}
