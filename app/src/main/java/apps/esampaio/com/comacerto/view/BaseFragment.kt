package apps.esampaio.com.comacerto.view

import android.support.v4.app.Fragment

import apps.esampaio.com.comacerto.core.service.ViewPresenter
import apps.esampaio.com.comacerto.view.dialogs.Dialogs

abstract class BaseFragment : Fragment(), ViewPresenter {

    override fun showAlert(message: String, onOkPressed: (() -> Unit)?) {
        Dialogs.openAlertDialog(context!!,message,onOkPressed)
    }

    override fun showAskDialog(message: String, onYesPressed: (() -> Unit)?, onNoPressed: (() -> Unit)?) {
        Dialogs.openAskDialog(context!!,message,onYesPressed,onNoPressed);
    }

    override fun showError(message: String) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun finishScreen() {

    }
}
