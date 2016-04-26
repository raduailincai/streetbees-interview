package ailincai.radu.raduailincai.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import ailincai.radu.raduailincai.R;

public class LoadingDialog {

    private AlertDialog loadingDialog;

    public LoadingDialog(final Context context) {
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setMessage(R.string.loading_dialog_message);
                dialogBuilder.setCancelable(false);
                loadingDialog = dialogBuilder.create();
            }
        });
    }

    public void show() {
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                loadingDialog.show();
            }
        });
    }

    public void dismiss() {
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
            }
        });
    }
}
