package ailincai.radu.raduailincai.model;

public class Image {

    private final String path;
    private final String extension;

    public Image(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public String getExtension() {
        return extension;
    }
}
