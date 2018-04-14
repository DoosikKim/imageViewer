package github.com.doosikkim.imageviewer;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import github.com.doosikkim.imageviewer.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel = new MainViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel.activity = this;
        mainViewModel.httpAddress = "http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx";
        mainViewModel.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
