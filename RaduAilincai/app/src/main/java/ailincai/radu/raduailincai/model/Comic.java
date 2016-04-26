package ailincai.radu.raduailincai.model;

import java.util.ArrayList;

public class Comic {

    private final int id;
    private final String title;
    private final ArrayList<Image> images;

    public Comic(int id, String title, ArrayList<Image> images) {
        this.id = id;
        this.title = title;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Image> getImages() {
        return images;
    }
}
