package github.com.doosikkim.imageviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;

import github.com.doosikkim.imageviewer.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel = new MainViewModel();

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Item> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel.activity = this;
        mainViewModel.httpAddress = "http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx";
        mainViewModel.onCreate(new MainViewModel.UrlLoadListener() {
            @Override
            public void onComplete(final ArrayList<Item> itemList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arrayList.addAll(itemList);
                    }
                });
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                Logger.e("errorCode = " + errorCode + ", errorMessage = " + errorMessage);
                Toast.makeText(getApplicationContext(), "errorCode = " + errorCode + ", errorMessage = " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        adapter = new ImageRecyclerViewAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
