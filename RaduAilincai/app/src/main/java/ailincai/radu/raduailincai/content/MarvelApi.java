package ailincai.radu.raduailincai.content;

import android.content.Context;
import android.net.Uri;

import java.security.NoSuchAlgorithmException;

import ailincai.radu.raduailincai.R;
import ailincai.radu.raduailincai.content.encryption.Hash;
import ailincai.radu.raduailincai.model.Comic;

public class MarvelApi {

    private static final String ROOT_URL = "http://gateway.marvel.com/v1/public/comics";
    private static final String PUBLIC_KEY = "ac63288ed3d30e8ce32a5cf9b4a93645";
    private static final String PRIVATE_KEY = "4c9089ccee4afcb16fb0700ee6f6e5dcc8c2f7c4";
    private static final String TIMESTAMP_PARAMETER_KEY = "ts";
    private static final String API_PARAMETER_KEY = "apikey";
    private static final String HASH_PARAMETER_KEY = "hash";
    private static final String PATH_EXTENSION_SEPARATOR = ".";
    private static final String PATH_RESOURCE_SEPARATOR = "/";

    public static String generateComicsUrl() throws NoSuchAlgorithmException {
        long currentTimestamp = System.currentTimeMillis();
        String preHash = currentTimestamp + PRIVATE_KEY + PUBLIC_KEY;
        String hash = new Hash(preHash).generateHash(Hash.MD5_ALGORITHM);
        Uri.Builder uriBuilder = Uri.parse(ROOT_URL).buildUpon();
        uriBuilder.appendQueryParameter(TIMESTAMP_PARAMETER_KEY, String.valueOf(currentTimestamp));
        uriBuilder.appendQueryParameter(API_PARAMETER_KEY, PUBLIC_KEY);
        uriBuilder.appendQueryParameter(HASH_PARAMETER_KEY, hash);
        return uriBuilder.toString();
    }

    public static String generateImageUrl(Context context, Comic comic) {
        String resourceType = context.getString(R.string.logo_resource_type);
        StringBuilder imageUrl = new StringBuilder();
        imageUrl.append(comic.getImagePath());
        imageUrl.append(PATH_RESOURCE_SEPARATOR);
        imageUrl.append(resourceType);
        imageUrl.append(PATH_EXTENSION_SEPARATOR);
        imageUrl.append(comic.getImageExtension());
        return imageUrl.toString();
    }

}
