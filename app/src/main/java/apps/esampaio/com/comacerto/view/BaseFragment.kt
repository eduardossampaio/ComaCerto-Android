package apps.esampaio.com.comacerto.view

import android.app.Dialog
import android.support.v4.app.Fragment
import android.widget.Toast

import apps.esampaio.com.comacerto.core.service.ViewPresenter
import apps.esampaio.com.comacerto.view.dialogs.Dialogs

abstract class BaseFragment : Fragment(), ViewPresenter {
    var loadingDialog : Dialog? = null



    override fun showAlert(message: String, onOkPressed: (() -> Unit)?) {
        Dialogs.openAlertDialog(context!!,message,onOkPressed)
    }

    override fun showAskDialog(message: String, onYesPressed: (() -> Unit)?, onNoPressed: (() -> Unit)?) {
        Dialogs.openAskDialog(context!!,message,onYesPressed,onNoPressed);
    }

    override fun showError(message: String,onOkPressed: (() -> Unit)?) {
        Dialogs.openErrorDialog(context!!,message,onOkPressed)
    }

    override fun showLoading(message: String) {
        loadingDialog = Dialogs.openLoadingDialog(context!!,message)

    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun finishScreen() {

    }
}
