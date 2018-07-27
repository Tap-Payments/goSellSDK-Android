package company.tap.gosellapi.internal.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import company.tap.gosellapi.R;

public class BaseActionBarActivity extends AppCompatActivity {
    private View actionBarCustomView;
    ImageView actionBarImage;
    private TextView actionBarTitle;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBarCustomView = LayoutInflater.from(this).inflate(R.layout.gosellapi_action_bar_custom_view, null);
        prepareActionBar();
    }

    private void prepareActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null && actionBar.getCustomView() == null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.alabaster));

            actionBar.setElevation(2.0f);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);

            actionBar.setCustomView(actionBarCustomView, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            //back button
            FrameLayout actionBarBackButtonContainer = actionBarCustomView.findViewById(R.id.actionBarBackButtonContainer);
            actionBarBackButtonContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            //title
            actionBarTitle = actionBarCustomView.findViewById(R.id.actionBarTitle);

            //additional image
            actionBarImage = actionBarCustomView.findViewById(R.id.actionBarImage);
            actionBarImage.setVisibility(View.GONE);

            Toolbar parent = (Toolbar) actionBarCustomView.getParent();
            parent.setContentInsetsAbsolute(0, 0);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        actionBarTitle.setText(title);
    }
}
