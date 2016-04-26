package ailincai.radu.raduailincai.adapters;

import java.util.ArrayList;

import ailincai.radu.raduailincai.content.MarvelApi;
import ailincai.radu.raduailincai.content.cache.UserData;
import ailincai.radu.raduailincai.model.Comic;

public class ComicWrapper {

    private final Comic comic;

    public ComicWrapper(Comic comic) {
        this.comic = comic;
    }

    public String getTitle() {
        return comic.getTitle();
    }

    public String getImageUrl() {
        if (UserData.getInstance().hasUserChangedImageForComic(comic)) {
            return UserData.getInstance().getUserImageForComic(comic);
        }
        return MarvelApi.generateImageUrl(comic);
    }

    public static ArrayList<ComicWrapper> mapComics(ArrayList<Comic> comics) {
        ArrayList<ComicWrapper> comicWrappers = new ArrayList<>();
        for (Comic currentComic : comics) {
            ComicWrapper newComicWrapper = new ComicWrapper(currentComic);
            comicWrappers.add(newComicWrapper);
        }
        return comicWrappers;
    }

}
