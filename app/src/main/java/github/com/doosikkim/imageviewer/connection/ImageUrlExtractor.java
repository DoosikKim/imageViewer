package github.com.doosikkim.imageviewer.connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import github.com.doosikkim.imageviewer.Item;
import github.com.doosikkim.imageviewer.Logger;
import github.com.doosikkim.imageviewer.viewmodel.MainViewModel;

/**
 * Created by doosik_kim on 2018. 4. 12..
 */
public class ImageUrlExtractor {
    private static final String TAG = ImageUrlExtractor.class.getSimpleName();
    private Thread connectionThread;
    public void request(final String httpAddress, final MainViewModel.UrlLoadListener listener) {

        connectionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Document document;
                try {

                    String hostName = new URL(httpAddress).getHost();

                    ArrayList<Item> resultArrayList = new ArrayList<>();
                    document = Jsoup.connect(httpAddress).get();
                    Elements images = document.select("img[src$=.jpg]");

                    for (int i = 0; i < images.size(); i++) {
                        String src = images.get(i).attr("src");
                        resultArrayList.add(new Item(src.substring(src.lastIndexOf("/") + 1), hostName + src));
                    }

                    Logger.d("url size = " + resultArrayList.size());
                    listener.onComplete(resultArrayList);

                } catch (IOException e) {
                    Logger.e(TAG + " : " + e.getMessage());
                    listener.onError(-1, e.getMessage());
                }
            }
        });

        connectionThread.start();
    }

}
