package ailincai.radu.raduailincai.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ComicsParser {

    private static final String DATA_KEY = "data";
    private static final String RESULTS_KEY = "results";
    private static final String ID_KEY = "id";
    private static final String TITLE_KEY = "title";
    private static final String IMAGES_KEY = "images";
    private static final String IMAGE_PATH_KEY = "path";
    private static final String IMAGE_EXTENSION_KEY = "extension";

    public static ArrayList<Comic> parseComics(String rawContent) throws JSONException {
        ArrayList<Comic> parsedComics = new ArrayList<>();
        JSONObject fullResponseAsJson = new JSONObject(rawContent);
        JSONObject dataAsJson = fullResponseAsJson.getJSONObject(DATA_KEY);
        JSONArray resultsAsJson = dataAsJson.getJSONArray(RESULTS_KEY);
        for (int jsonObjectIndex = 0; jsonObjectIndex < resultsAsJson.length(); jsonObjectIndex++) {
            Comic newComicBook = parseComic(resultsAsJson.getJSONObject(jsonObjectIndex));
            parsedComics.add(newComicBook);
        }
        return parsedComics;
    }

    private static Comic parseComic(JSONObject comicBookAsJson) throws JSONException {
        int id = comicBookAsJson.getInt(ID_KEY);
        String title = comicBookAsJson.getString(TITLE_KEY);
        JSONArray images = comicBookAsJson.getJSONArray(IMAGES_KEY);
        String firstImagePath = null;
        String firstImageExtension = null;
        if (images.length() > 0) {
            JSONObject firstImageAsJson = images.getJSONObject(0);
            firstImagePath = firstImageAsJson.getString(IMAGE_PATH_KEY);
            firstImageExtension = firstImageAsJson.getString(IMAGE_EXTENSION_KEY);
        }
        return new Comic(id, title, firstImagePath, firstImageExtension);
    }

}
