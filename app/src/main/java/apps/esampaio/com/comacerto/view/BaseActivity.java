package apps.esampaio.com.comacerto.view;

import android.support.v7.app.AppCompatActivity;

import apps.esampaio.com.comacerto.core.service.ViewPresenter;

public abstract class BaseActivity extends AppCompatActivity implements ViewPresenter{
    @Override
    public void showAlert(String message) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void finishScreen() {
        finish();
    }
}
