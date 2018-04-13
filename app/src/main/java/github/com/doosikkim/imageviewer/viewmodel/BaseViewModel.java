package github.com.doosikkim.imageviewer.viewmodel;

/**
 * Created by doosik_kim on 2018. 4. 12..
 */
public interface BaseViewModel {
    void onCreate(MainViewModel.UrlLoadListener listener);
    void onResume();
    void onPause();
    void onDestroy();
}
