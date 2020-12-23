package apps.esampaio.com.comacerto.view

import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import apps.esampaio.com.comacerto.core.service.ViewPresenter
import apps.esampaio.com.comacerto.view.dialogs.Dialogs


abstract class BaseActivity : AppCompatActivity(), ViewPresenter {
    private var loadingDialog: Dialog? = null
    private val requestCodePermission = 123;

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.clear()
    }

    //presenter
    override fun showAlert(message: String, onOkPressed: (() -> Unit)?) {
        Dialogs.openAlertDialog(this, message, onOkPressed)
    }

    override fun showAskDialog(message: String, onYesPressed: (() -> Unit)?, onNoPressed: (() -> Unit)?) {
        Dialogs.openAskDialog(this, message, onYesPressed, onNoPressed)
    }

    override fun showError(message: String, onOkPressed: (() -> Unit)?) {
        Dialogs.openErrorDialog(this, message, onOkPressed)
    }

    override fun showLoading(message: String) {
        loadingDialog = Dialogs.openLoadingDialog(this, message);
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun finishScreen() {
        finish()
    }


}
