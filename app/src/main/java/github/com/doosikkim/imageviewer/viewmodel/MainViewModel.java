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

    RecyclerView recyclerView;
    ImageRecyclerViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate() {
        initRecyclerView();
        ImageUrlExtractor urlConnector = new ImageUrlExtractor();
        urlConnector.request(httpAddress, new MainViewModel.UrlLoadListener() {
            @Override
            public void onComplete(final ArrayList<Item> itemList) {
                adapter.addMoreItems(itemList);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                Logger.e("errorCode = " + errorCode + ", errorMessage = " + errorMessage);
                Toast.makeText(activity, "errorCode = " + errorCode + ", errorMessage = " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ImageRecyclerViewAdapter(activity);
        recyclerView.setAdapter(adapter);
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
