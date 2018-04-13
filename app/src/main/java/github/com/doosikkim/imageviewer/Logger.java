package github.com.doosikkim.imageviewer;

import android.util.Log;

/**
 * Created by doosik_kim on 2018. 4. 12..
 */
public class Logger {
    private static final String TAG = "ImageViewer";
    private static boolean enable = true;

    public static void d(String text) {
        if (enable) {
            Log.d(TAG, text);
        }
    }

    public static void e(String text) {
        if (enable) {
            Log.e(TAG, text);
        }
    }

    public static void i(String text) {
        if (enable) {
            Log.i(TAG, text);
        }
    }

    public static void setEnable(boolean flag) {
        if (enable) {
            enable = flag;
        }
    }
}
