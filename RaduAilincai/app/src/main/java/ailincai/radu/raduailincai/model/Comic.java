package ailincai.radu.raduailincai.model;

public class Comic {

    private final int id;
    private final String title;
    private final String imageUrl;

    public Comic(int id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
