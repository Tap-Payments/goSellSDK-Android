package company.tap.gosellapi.internal.data_managers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import company.tap.gosellapi.R;
import gotap.com.tapglkitandroid.gl.Views.TapLoadingView;
import jp.wasabeef.blurry.Blurry;

public class LoadingScreenManager {

    // Constants
    private static final int BLUR_RADIUS = 40;
    private static final long ANIMATION_DURATION = 300;

    // Variables
    private AppCompatActivity hostActivity;
    private static final int loadingLayoutID = R.layout.gosellapi_layout_loading;
    private TapLoadingView loadingView;
    private View loadingLayout;

    private LoadingScreenManager() {

    }

    private static class SingletonHolder {
        private static final LoadingScreenManager INSTANCE = new LoadingScreenManager();
    }

    public static LoadingScreenManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void showLoadingScreen(AppCompatActivity activity) {
        this.hostActivity = activity;
        createLoadingScreen();
    }

    public void closeLoadingScreen() {
        if (loadingView != null) {

            loadingView.setForceStop(true, new TapLoadingView.FullProgressListener() {
                @Override
                public void onFullProgress() {

                    hostActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingView.setVisibility(View.GONE);
                            loadingView = null;
                        }
                    });

                    final ViewGroup insertPoint = hostActivity.findViewById(android.R.id.content);

                    if (loadingLayout != null) {

                        loadingLayout.animate()
                                .alpha(0.0f)
                                .setDuration(ANIMATION_DURATION)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);

                                        insertPoint.removeView(loadingLayout);
                                        loadingLayout = null;
                                        hostActivity = null;
                                    }
                                });
                    }

                }
            });

        }
    }

    private void createLoadingScreen() {

        LayoutInflater inflater = (LayoutInflater) hostActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) return;

        loadingLayout = inflater.inflate(loadingLayoutID, null);
        loadingLayout.setAlpha(0.0f);

        // Configure background
        ImageView loadingViewBackground = loadingLayout.findViewById(R.id.blurredBackground);

        ViewGroup insertPoint = hostActivity.findViewById(android.R.id.content);

        Bitmap bitmap = createScreenshot(insertPoint);

        Blurry.with(hostActivity)
                .radius(BLUR_RADIUS)
                .color(Color.argb(220, 255, 255, 255))
                .from(bitmap)
                .into(loadingViewBackground);

        insertPoint.addView(loadingLayout, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        loadingLayout.bringToFront();

        // Configure appearance animation
        loadingLayout.animate()
                .alpha(1.0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        // Configure loading view
                        loadingView = loadingLayout.findViewById(R.id.loadingView);
                        loadingView.start();
                    }
                });

    }

    private Bitmap createScreenshot(ViewGroup view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

}
