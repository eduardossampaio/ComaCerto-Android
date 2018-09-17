package apps.esampaio.com.comacerto.core.service;

public interface ViewPresenter {
    
    void showAlert(String message);
    
    void showError(String message);
    
    void showLoading();
    
    void hideLoading();

    void finishScreen();
}
