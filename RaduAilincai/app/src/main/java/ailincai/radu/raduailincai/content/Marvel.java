package ailincai.radu.raduailincai.content;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ailincai.radu.raduailincai.content.network.NetworkRequest;
import ailincai.radu.raduailincai.model.Comic;
import ailincai.radu.raduailincai.model.ComicsParser;

public class Marvel {

    private static final ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();
    private static Marvel INSTANCE;

    public static Marvel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Marvel();
        }
        return INSTANCE;
    }

    public void fetchComics(final int offset, final int limit, final MarvelListener marvelListener) {
        THREAD_POOL.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = MarvelApi.generateComicsUrl(offset, limit);
                    String rawData = NetworkRequest.doGet(url);
                    ArrayList<Comic> comics = ComicsParser.parseComics(rawData);
                    notifySuccess(marvelListener, comics);
                } catch (NoSuchAlgorithmException | JSONException e) {
                    notifyInternalError(marvelListener);
                } catch (IOException e) {
                    notifyNetworkError(marvelListener);
                }
            }
        });
    }

    private static void notifySuccess(final MarvelListener marvelListener, final ArrayList<Comic> comics) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                marvelListener.onSuccess(comics);
            }
        });
    }

    private static void notifyInternalError(final MarvelListener marvelListener) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                marvelListener.onInternalError();
            }
        });
    }

    private static void notifyNetworkError(final MarvelListener marvelListener) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                marvelListener.onNetworkError();
            }
        });
    }

    public interface MarvelListener {

        void onSuccess(ArrayList<Comic> comics);

        void onInternalError();

        void onNetworkError();

    }

}
