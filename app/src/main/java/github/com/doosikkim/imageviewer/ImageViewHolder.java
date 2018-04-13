package github.com.doosikkim.imageviewer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by doosik_kim on 2018. 4. 12..
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;

    public ImageViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        textView = itemView.findViewById(R.id.imagetitle);
    }
}
