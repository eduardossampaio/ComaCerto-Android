package apps.esampaio.com.comacerto.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import apps.esampaio.com.comacerto.R;

public class LoadingDialog extends Dialog{

    private String title;
    private String message;

    public LoadingDialog(@NonNull Context context,String message){
        this(context,context.getString(R.string.app_name),message);
    }
    public LoadingDialog(@NonNull Context context,String title,String message) {
        super(context,R.style.Theme_Dialog);
        this.title = title;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_dialog);
        TextView title = findViewById(R.id.txtTitle);
        TextView message = findViewById(R.id.txtContent);
        title.setText(this.title);
        message.setText(this.message);
        setCancelable(false);
    }
}
