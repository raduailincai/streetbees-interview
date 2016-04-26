package ailincai.radu.raduailincai.model;

public class Comic {

    private final int id;
    private final String title;
    private final String imagePath;
    private final String imageExtension;

    public Comic(int id, String title, String imagePath, String imageExtension) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.imageExtension = imageExtension;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImageExtension() {
        return imageExtension;
    }
}
