package company.tap.gosellapi.internal.custom_views;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import company.tap.gosellapi.R;

public class TapSlidingDrawer extends LinearLayout {
    public static final int STATE_OPENED = 0;
    public static final int STATE_CLOSED = 1;

    private int m_intState;
    private LinearLayout m_content;
    private ImageButton m_handle;

    public TapSlidingDrawer(Context context) {
      super(context);
      setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
      setOrientation(VERTICAL);
      setGravity(Gravity.CENTER);

      m_content = new LinearLayout(context);
      // add your content here
      addView(m_content);
      m_intState = STATE_CLOSED;

      m_handle = new ImageButton(context);
      m_handle.setImageResource(R.drawable.ic_bill_normal);
      m_handle.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
          toggleState();
        }
      });
      m_handle.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
      addView(m_handle);
    }
    private int getContentHeight() {
      return m_content.getHeight();
    }
    private void toggleState() {
      int intYStart = 0;
      int intYEnd = m_intState == STATE_OPENED ? -getContentHeight() : getContentHeight();
      Animation a = new TranslateAnimation(0.0f, 0.0f, intYStart, intYEnd);
      a.setDuration(1000);
      a.setStartOffset(300);
      a.setInterpolator(AnimationUtils.loadInterpolator(getContext(), android.R.anim.bounce_interpolator));
      startAnimation(a);
      m_intState = m_intState == STATE_OPENED ? STATE_CLOSED : STATE_OPENED;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
      super.onLayout(changed, l, t, r, b);
      offsetTopAndBottom(-getContentHeight()); // content is initially invisible
    }
    protected void onAnimationEnd() {
      super.onAnimationEnd();
      int intYOffset = m_intState == STATE_OPENED ? getContentHeight() : -getContentHeight();
      offsetTopAndBottom(intYOffset);
    }
  }