package github.com.doosikkim.imageviewer.cache;


import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by doosik_kim on 2018. 4. 14..
 */
public class CacheManager {

    private MemoryCache memoryCache;

    public CacheManager(Context context) {
        memoryCache = new MemoryCache();
    }

    public void addImageToCache(String key, Bitmap bitmap) {
        memoryCache.addBitmapToMemoryCache(key, bitmap);
    }

    public Bitmap getImageFromCache(String key) {
        return memoryCache.getBitmapFromMemCache(key);
    }
}
