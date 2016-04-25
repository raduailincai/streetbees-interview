package ailincai.radu.raduailincai.content;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ailincai.radu.raduailincai.content.network.NetworkRequest;
import ailincai.radu.raduailincai.model.Comic;
import ailincai.radu.raduailincai.model.ComicsParser;

public class Marvel {

    private static final ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();
    private static Marvel INSTANCE;
    private ArrayList<Comic> comics;

    public static Marvel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Marvel();
        }
        return INSTANCE;
    }

    public void startLoadingData(final MarvelListener marvelListener) {
        THREAD_POOL.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String rawData = NetworkRequest.doGet(MarvelApi.generateMarvelUrl());
                    comics = ComicsParser.parseComics(rawData);
                    notifySuccess(marvelListener);
                } catch (Exception e) {
                    notifyError(marvelListener);
                }
            }
        });
    }

    private static void notifySuccess(final MarvelListener marvelListener) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                marvelListener.onSuccess();
            }
        });
    }

    private static void notifyError(final MarvelListener marvelListener) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                marvelListener.onError();
            }
        });
    }

    public ArrayList<Comic> getComics() {
        return comics;
    }

    public interface MarvelListener {

        void onSuccess();

        void onError();

    }

}
