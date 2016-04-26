package ailincai.radu.raduailincai;

import android.content.Context;

public class ApplicationContext {

    private static Context applicationContext;

    public static void init(Context context) {
        applicationContext = context.getApplicationContext();
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }

}
