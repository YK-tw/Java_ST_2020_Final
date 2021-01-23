package by.training.finalp.action;

public class MenuItem {
    private String url;
    private String name;

    public MenuItem(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
