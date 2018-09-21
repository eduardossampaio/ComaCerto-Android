package apps.esampaio.com.comacerto.core.service

interface ViewPresenter {

    fun showAlert(message: String,  onOkPressed: (() -> Unit)? = null)

    fun showAskDialog(message: String,  onYesPressed: (() -> Unit)? = null,onNoPressed: (() -> Unit)? = null)

    fun showError(message: String)

    fun showLoading()

    fun hideLoading()

    fun finishScreen()
}
