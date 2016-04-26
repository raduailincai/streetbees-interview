package ailincai.radu.raduailincai.content.cache;

import android.content.Context;
import android.content.SharedPreferences;

import ailincai.radu.raduailincai.ApplicationContext;
import ailincai.radu.raduailincai.model.Comic;

public class UserData {

    private static final String USER_DATA_KEY = "USER_DATA_KEY";
    private static UserData INSTANCE;
    private final SharedPreferences sharedPreferences;

    public static UserData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserData();
        }
        return INSTANCE;
    }

    private UserData() {
        Context applicationContext = ApplicationContext.getApplicationContext();
        sharedPreferences = applicationContext.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE);
    }

    public boolean hasUserChangedImageForComic(Comic comic) {
        String comicIdAsString = String.valueOf(comic.getId());
        return sharedPreferences.contains(comicIdAsString);
    }

    public String getUserImageForComic(Comic comic) {
        String comicIdAsString = String.valueOf(comic.getId());
        return sharedPreferences.getString(comicIdAsString, null);
    }

    public void setUserImageForComic(String userImagePath, Comic comic) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String comicIdAsString = String.valueOf(comic.getId());
        editor.putString(comicIdAsString, userImagePath);
        editor.apply();
    }

}
