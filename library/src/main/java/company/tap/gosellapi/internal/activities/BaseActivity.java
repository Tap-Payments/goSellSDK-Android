package company.tap.gosellapi.internal.activities;

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
    protected void onResume() {

        super.onResume();
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
