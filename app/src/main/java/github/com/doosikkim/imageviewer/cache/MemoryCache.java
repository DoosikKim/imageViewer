package github.com.doosikkim.imageviewer.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by doosik_kim on 2018. 4. 14..
 */
public class MemoryCache {

    private LruCache<String, Bitmap> memoryLruCache;

    public MemoryCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        memoryLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryLruCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryLruCache.get(key);
    }
}
