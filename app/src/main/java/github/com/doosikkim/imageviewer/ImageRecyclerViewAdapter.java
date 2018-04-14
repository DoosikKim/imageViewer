package github.com.doosikkim.imageviewer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import github.com.doosikkim.imageviewer.cache.CacheManager;
import github.com.doosikkim.imageviewer.dataloader.ImageLoader;

/**
 * Created by doosik_kim on 2018. 4. 12..
 */
public class ImageRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Item> items;
    private int lastPosition = -1;
    private ImageLoader imageLoader;
    private CacheManager cacheManager;

    public ImageRecyclerViewAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
        imageLoader = new ImageLoader();
        cacheManager = new CacheManager(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        ImageViewHolder holder = new ImageViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ImageViewHolder) {
            final ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            final Item currentItem = items.get(position);
            imageViewHolder.textView.setText(currentItem.imageTitle);
            imageViewHolder.imageView.setImageBitmap(null);

            Logger.d("title = " + currentItem.imageTitle);
            Logger.d("url = " + currentItem.imageUrl);

            Bitmap bitmap = cacheManager.getImageFromCache(currentItem.imageTitle);
            if (bitmap != null) {
                imageViewHolder.imageView.setImageBitmap(bitmap);
                setAnimation(imageViewHolder.imageView, position);
            } else {
                imageLoader.request(currentItem.imageUrl, new ImageLoader.ImageLoadListener() {
                    @Override
                    public void onComplete(final Bitmap bitmap) {
                        cacheManager.addImageToCache(currentItem.imageTitle, bitmap);
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageViewHolder.imageView.setImageBitmap(bitmap);
                                setAnimation(imageViewHolder.imageView, position);
                            }
                        });
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showDefaultImage(imageViewHolder.imageView);
                                setAnimation(imageViewHolder.imageView, position);
                            }
                        });
                    }
                });
            }

        }

    }

    private void showDefaultImage(ImageView imageView) {
        imageView.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        if(position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void addMoreItems(ArrayList<Item> items) {
        this.items.addAll(items);
    }
}
