package github.com.doosikkim.imageviewer.cache;


import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by doosik_kim on 2018. 4. 14..
 */
public class CacheManager {

    private MemoryCache memoryCache;
    private DiskCache diskCache;

    public CacheManager(Context context) {
        memoryCache = new MemoryCache();
        diskCache = new DiskCache(context);
    }

    public void addImageToCache(String key, Bitmap bitmap) {
        memoryCache.addBitmapToMemoryCache(key, bitmap);
        diskCache.addBitmapToDiskCache(key, bitmap);
    }

    public Bitmap getImageFromCache(String key) {
        Bitmap imageBitmap = memoryCache.getBitmapFromMemCache(key);
        if (imageBitmap != null) {
            return imageBitmap;
        }
        imageBitmap = diskCache.getBitmapFromDiskCache(key);
        memoryCache.addBitmapToMemoryCache(key, imageBitmap);
        return imageBitmap;
    }
}
