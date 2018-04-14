package github.com.doosikkim.imageviewer.dataloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import github.com.doosikkim.imageviewer.Logger;

/**
 * Created by doosik_kim on 2018. 4. 12..
 */
public class ImageLoader {

    public ImageLoader() {
    }

    public void request(final String url, final ImageLoadListener listener) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                    listener.onComplete(bitmap);
                } catch (IOException e) {
                    Logger.e("ImageLoader.request IOException : " + e.getMessage());
                }
            }
        };

        ImageLoadExecutor.execute(runnable);
    }

    public interface ImageLoadListener {
        void onComplete(Bitmap bitmap);
        void onError(int errorCode, String errorMessage);
    }

}
