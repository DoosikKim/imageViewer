package github.com.doosikkim.imageviewer.viewmodel;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;

import github.com.doosikkim.imageviewer.ImageRecyclerViewAdapter;
import github.com.doosikkim.imageviewer.Item;
import github.com.doosikkim.imageviewer.Logger;
import github.com.doosikkim.imageviewer.R;
import github.com.doosikkim.imageviewer.connection.ImageUrlExtractor;

/**
 * Created by doosik_kim on 2018. 4. 12..
 */
public class MainViewModel implements BaseViewModel {

    public Activity activity;
    public String httpAddress;

    @Override
    public void onCreate(UrlLoadListener listener) {
        ImageUrlExtractor urlConnector = new ImageUrlExtractor();
        urlConnector.request(httpAddress, listener);
    }



    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    public interface UrlLoadListener {
        void onComplete(ArrayList<Item> itemList);
        void onError(int errorCode, String errorMessage);
    }
}
