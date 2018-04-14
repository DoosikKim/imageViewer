package github.com.doosikkim.imageviewer.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.LruCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import github.com.doosikkim.imageviewer.Logger;

/**
 * Created by doosik_kim on 2018. 4. 14..
 */
public class DiskCache {

    private LruCache<String, String> diskLruCache;
    private String cacheDir;
    private static final long DEFAULT_CACHE_MAX_SIZE = 200 * 1024 * 1024;
    private long cacheMaxSize;
    private File cacheDirFile;
    private Object diskCacheLock = new Object();
    private Thread writeThread;


    public DiskCache(Context context) {
        cacheDir = context.getCacheDir().getAbsolutePath() + "/imageCache/";
//        cacheDir = Environment.getExternalStorageDirectory() + "/imageCache/";
        Logger.d("cache path = " + cacheDir);
        initialize();
        makeCacheListFromFileList();
    }

    private void initialize() {
        cacheDirFile = new File(cacheDir);
        if (!cacheDirFile.exists()) {
            cacheDirFile.mkdir();
        }
        cacheMaxSize = measureAvailableCacheSize();
        diskLruCache = new LruCache<String, String>((int) cacheMaxSize) {
            @Override
            protected void entryRemoved(boolean evicted, String key, String oldValue, String newValue) {
                File file = new File(cacheDir + key);
                if (file.exists()) {
                    file.delete();
                }
            }

            @Override
            protected int sizeOf(String key, String value) {
                File file = new File(value);
                return (int) file.length();
            }
        };
    }

    private void makeCacheListFromFileList() {
        synchronized (diskCacheLock) {
            File[] cachedFiles = cacheDirFile.listFiles();
            if (cachedFiles != null) {
                for (int i = 0; i < cachedFiles.length; i++) {
                    diskLruCache.put(cachedFiles[i].getName(), cachedFiles[i].getAbsolutePath());
                }
            }
        }
    }

    public void addBitmapToDiskCache(String key, Bitmap bitmap) {
        synchronized (diskCacheLock) {
            if (getBitmapFromDiskCache(key) == null) {
                Logger.d("add diskCache = " + key);
                String filePath = cacheDir + key;
                diskLruCache.put(key, filePath);
                writeBitmapToFile(bitmap, filePath);
            }
        }
    }

    private void writeBitmapToFile(final Bitmap bitmap, final String filePath) {
        writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (diskCacheLock) {
                    FileOutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(filePath);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    } catch (FileNotFoundException e) {
                        Logger.e("writeBitmapToFile : " + e.getMessage());
                    } finally {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                Logger.e("writeBitmapToFile : " + e.getMessage());
                            }
                        }
                    }
                }
            }
        });
        writeThread.start();
    }

    public Bitmap getBitmapFromDiskCache(String key) {
        String filePath = diskLruCache.get(key);
        if (TextUtils.isEmpty(filePath) || !(new File(filePath).exists())) {
            return null;
        }
        Logger.d("get diskCache = " + key);
        return BitmapFactory.decodeFile(filePath);
    }

    private long measureAvailableCacheSize() {
        long availableCacheSize = getAvailableInternalMemorySize();
        if (availableCacheSize < DEFAULT_CACHE_MAX_SIZE) {
            return availableCacheSize;
        }
        return DEFAULT_CACHE_MAX_SIZE;
    }

    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize, availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }

    public void clearDiskCache() {
        File file = new File(cacheDir);
        if (file.exists()) {
            file.delete();
        }
        initialize();
    }
}
