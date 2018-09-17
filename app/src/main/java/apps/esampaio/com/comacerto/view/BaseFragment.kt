package apps.esampaio.com.comacerto.view

import android.support.v4.app.Fragment
import android.widget.FrameLayout
import android.widget.Toast

import apps.esampaio.com.comacerto.core.service.ViewPresenter

abstract class BaseFragment : Fragment(), ViewPresenter {
    override fun showAlert(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
