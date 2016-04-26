package ailincai.radu.raduailincai.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class LoadingDialog {

    private AlertDialog loadingDialog;

    public LoadingDialog(final Context context) {
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                loadingDialog = new AlertDialog.Builder(context).setMessage("Please wait...").setCancelable(false).create();
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
