package github.com.doosikkim.imageviewer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by doosik_kim on 2018. 4. 12..
 */
public class ImageRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Item> items;
    private int lastPosition = -1;

    public ImageRecyclerViewAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        ImageViewHolder holder = new ImageViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            Item currentItem = items.get(position);
            imageViewHolder.textView.setText(currentItem.imageTitle);

            Logger.d("title = " + currentItem.imageTitle);
            Logger.d("url = " + currentItem.imageUrl);

            // TODO. 여기서 이미지 받아와야 함.
            Glide.with(context)
                    .load(currentItem.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewHolder.imageView);

            setAnimation(imageViewHolder.imageView, position);
        }

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
