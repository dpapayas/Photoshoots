package id.dpapayas.photoshoots.model;

/**
 * Created by dpapayas on 3/9/18.
 */

public class ItemModel {
    private String name;
    private String date;
    private int thumbnail;

    public ItemModel() {
    }

    public ItemModel(String name, String date, int thumbnail) {
        this.name = name;
        this.date = date;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}