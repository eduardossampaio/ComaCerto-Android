package apps.esampaio.com.comacerto.view

import android.support.v4.app.Fragment
import android.widget.FrameLayout

import apps.esampaio.com.comacerto.core.service.ViewPresenter

abstract class BaseFragment : Fragment(), ViewPresenter {
    override fun showAlert(message: String) {

    }

    override fun showError(message: String) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}
