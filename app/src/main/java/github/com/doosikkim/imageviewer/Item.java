package github.com.doosikkim.imageviewer;

/**
 * Created by doosik_kim on 2018. 4. 12..
 */
public class Item {

    String imageTitle;
    String imageUrl;

    public String getImageTitle() {
        return imageTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Item(String imageTitle, String imageUrl) {
        this.imageTitle = imageTitle;
        this.imageUrl = imageUrl;
    }
}
