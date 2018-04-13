package github.com.doosikkim.imageviewer;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by doosik_kim on 2018. 4. 13..
 */
public class CustomGlideModule implements GlideModule {
    private final int MAX_MEMORY = (int)(Runtime.getRuntime().maxMemory() / 1024);
    private final int CACHESIZE = MAX_MEMORY / 8;
    private final int DISK_CACHE_SIZE = 1024 * 1024 * 10;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)
                .setMemoryCache(new LruResourceCache(CACHESIZE))
                .setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", DISK_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
