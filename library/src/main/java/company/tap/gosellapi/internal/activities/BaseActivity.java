package company.tap.gosellapi.internal.activities;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * The type Base activity.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Gets current.
     *
     * @return the current
     */
    @Nullable public static BaseActivity getCurrent() {

        return currentActivity;
    }

    private static void setCurrent(@Nullable BaseActivity activity) {

        currentActivity = activity;
    }

    @Nullable private static BaseActivity currentActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        /**
         * Google throws this exception on Activity's onCreate method after v27, their meaning is :
         * if an Activity is translucent or floating, its orientation should be relied on parent(background) Activity,
         * can't make decision on itself.
         * Even if you remove android:screenOrientation="portrait" from the floating or translucent Activity
         *  but fix orientation on its parent(background) Activity, it is still fixed by the parent.
         *  In android Oreo (API 26) you can not change orientation for Activity that have below line in style
         */
        System.out.println("Build.VERSION.SDK_INT : "+ Build.VERSION.SDK_INT);

        if (Build.VERSION.SDK_INT == 26) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (Build.VERSION.SDK_INT == 26) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        BaseActivity.setCurrent(this);
    }

    @Override
    protected void onPause() {

        clearCurrentActivity();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        clearCurrentActivity();
        super.onDestroy();
    }

    private void clearCurrentActivity() {

        BaseActivity current = BaseActivity.getCurrent();
        if (this.equals(current)) {

            BaseActivity.setCurrent(null);
        }
    }
}
