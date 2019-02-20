package company.tap.gosellapi.open.buttons;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.interfaces.IPaymentProcessListener;
import company.tap.gosellapi.internal.utils.Utils;
import company.tap.gosellapi.open.constants.SettingsKeys;
import company.tap.gosellapi.open.controllers.SettingsManager;
import gotap.com.tapglkitandroid.gl.Views.TapLoadingView;

/**
 * The type Pay button view.
 */
public final class PayButtonView extends FrameLayout  {

    private static final int VALUE_IS_MISSING = -11111;
    private static final String TAG = "GoSellPayLayout TAG";

    private int layoutId;
    private int mHeight;
    private int mMarginTop;
    private int mMarginBottom;
    private int mMarginLeft;
    private int mMarginStart;
    private int mMarginRight;
    private int mMarginEnd;

    private int mPaddingTop;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingStart;
    private int mPaddingRight;
    private int mPaddingEnd;

    private int mTextSize;
    private int mTextColor;
    private int mTextStyle;

    private int mAdditionalViewMarginVertical;
    private int mAdditionalViewMarginHorizontal;
    private int mAdditionalViewDimension;

    private AppCompatTextView payButton;
    private Drawable mBackground;
    private int mGravity;
    private CharSequence mText;

    private TapLoadingView loadingView;
    private ImageView securityIconView;

    private PaymentOption paymentOption;
    private IPaymentProcessListener observer;

    private Context context;

    private SettingsManager settingsManager;

    /**
     * instantiate PayButtonView
     *
     * @param context the context
     */
    public PayButtonView(Context context) {
        super(context);
        this.context = context;
        init(context, null);
    }

    /**
     * instantiate PayButtonView
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public PayButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init(context, attrs);
    }

    /**
     * set PayButtonView status [enabled - disabled]
     * @param enabled
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        payButton.setEnabled(enabled);
    }


    /**
     * initialize PayButtonView and load loading view
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        settingsManager = SettingsManager.getInstance();
        settingsManager.setPref(context);

        loadingView = new TapLoadingView(context, attrs);
        payButton = new AppCompatTextView(context, attrs);
        securityIconView = new ImageView(context, attrs);

        layoutId = getId() == -1 ? R.id.pay_layout_id : getId();
        setId(layoutId);

        payButton.setId(R.id.pay_button_id);
        securityIconView.setId(R.id.pay_security_icon_id);

        initAttributes(context, attrs);
        setAttributes();

        addView(payButton, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(loadingView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.START | Gravity.CENTER_VERTICAL));
        addView(securityIconView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.END | Gravity.CENTER_VERTICAL));
    }

    /**
     * initialize PayButtonView from xml
     * @param context
     * @param attrs
     */
    private void initAttributes(Context context, AttributeSet attrs) {
        mHeight = context.getResources().getDimensionPixelSize(R.dimen.pay_btn_min_height);

        mMarginTop = context.getResources().getDimensionPixelSize(R.dimen.pay_btn_margin_vertical);
        mMarginBottom = context.getResources().getDimensionPixelSize(R.dimen.pay_btn_margin_vertical);
        mMarginLeft = context.getResources().getDimensionPixelSize(R.dimen.default_padding);
        mMarginStart = VALUE_IS_MISSING;
        mMarginRight = context.getResources().getDimensionPixelSize(R.dimen.default_padding);
        mMarginEnd = VALUE_IS_MISSING;

        mPaddingTop = context.getResources().getDimensionPixelSize(R.dimen.pay_btn_padding_vertical);
        mPaddingBottom = context.getResources().getDimensionPixelSize(R.dimen.pay_btn_padding_vertical);
        mPaddingLeft = context.getResources().getDimensionPixelSize(R.dimen.pay_btn_padding_horizontal);
        mPaddingStart = VALUE_IS_MISSING;
        mPaddingRight = context.getResources().getDimensionPixelSize(R.dimen.pay_btn_padding_horizontal);
        mPaddingEnd = VALUE_IS_MISSING;

        mTextSize = context.getResources().getDimensionPixelSize(R.dimen.pay_btn_text_size);
        mTextColor = ContextCompat.getColor(context, R.color.white);
        mTextStyle = Typeface.BOLD;


        setInitialBackground();


        mGravity = Gravity.CENTER;
        mText = context.getResources().getString(R.string.pay);

        if (attrs != null) {
            // Attribute initialization
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PayButtonView);

            int height = a.getLayoutDimension(R.styleable.PayButtonView_android_layout_height, VALUE_IS_MISSING);
            if (height != VALUE_IS_MISSING && height > mHeight) {
                mHeight = height;
            }

            //margins
            int marginTop = a.getDimensionPixelSize(R.styleable.PayButtonView_android_layout_marginTop, VALUE_IS_MISSING);
            if (marginTop != VALUE_IS_MISSING) {
                mMarginTop = marginTop;
            }

            int marginBottom = a.getDimensionPixelSize(R.styleable.PayButtonView_android_layout_marginBottom, VALUE_IS_MISSING);
            if (marginBottom != VALUE_IS_MISSING) {
                mMarginBottom = marginBottom;
            }

            int marginLeft = a.getDimensionPixelSize(R.styleable.PayButtonView_android_layout_marginLeft, VALUE_IS_MISSING);
            if (marginLeft != VALUE_IS_MISSING) {
                mMarginLeft = marginLeft;
            }

            int marginRight = a.getDimensionPixelSize(R.styleable.PayButtonView_android_layout_marginRight, VALUE_IS_MISSING);
            if (marginRight != VALUE_IS_MISSING) {
                mMarginRight = marginRight;
            }

            int marginStart = a.getDimensionPixelSize(R.styleable.PayButtonView_android_layout_marginStart, VALUE_IS_MISSING);
            if (marginStart != VALUE_IS_MISSING) {
                mMarginStart = marginStart;
            }

            int marginEnd = a.getDimensionPixelSize(R.styleable.PayButtonView_android_layout_marginEnd, VALUE_IS_MISSING);
            if (marginEnd != VALUE_IS_MISSING) {
                mMarginEnd = marginEnd;
            }

            //padding
            int paddingTop = a.getDimensionPixelSize(R.styleable.PayButtonView_android_paddingTop, VALUE_IS_MISSING);
            if (paddingTop != VALUE_IS_MISSING) {
                mPaddingTop = paddingTop;
            }

            int paddingBottom = a.getDimensionPixelSize(R.styleable.PayButtonView_android_paddingBottom, VALUE_IS_MISSING);
            if (paddingBottom != VALUE_IS_MISSING) {
                mPaddingBottom = paddingBottom;
            }

            int paddingLeft = a.getDimensionPixelSize(R.styleable.PayButtonView_android_paddingLeft, VALUE_IS_MISSING);
            if (paddingLeft != VALUE_IS_MISSING) {
                mPaddingLeft = paddingLeft;
            }

            int paddingRight = a.getDimensionPixelSize(R.styleable.PayButtonView_android_paddingRight, VALUE_IS_MISSING);
            if (paddingRight != VALUE_IS_MISSING) {
                mPaddingRight = paddingRight;
            }

            int paddingStart = a.getDimensionPixelSize(R.styleable.PayButtonView_android_paddingStart, VALUE_IS_MISSING);
            if (paddingStart != VALUE_IS_MISSING) {
                mPaddingStart = paddingStart;
            }

            int paddingEnd = a.getDimensionPixelSize(R.styleable.PayButtonView_android_paddingEnd, VALUE_IS_MISSING);
            if (paddingEnd != VALUE_IS_MISSING) {
                mPaddingEnd = paddingEnd;
            }

            //text options
            int textSize = a.getDimensionPixelSize(R.styleable.PayButtonView_android_textSize, VALUE_IS_MISSING);
            if (textSize != VALUE_IS_MISSING) {
                mTextSize = textSize;
            }

            int textColor = a.getColor(R.styleable.PayButtonView_android_textColor, VALUE_IS_MISSING);
            if (textColor != VALUE_IS_MISSING) {
                mTextColor = textColor;
            }

            int textStyle = a.getInteger(R.styleable.PayButtonView_android_textStyle, VALUE_IS_MISSING);
            if (textStyle != VALUE_IS_MISSING) {
                mTextStyle = textStyle;
            }

            //other
            Drawable background = a.getDrawable(R.styleable.PayButtonView_android_background);
            if (background != null) {
                mBackground = background;
            }

            int gravity = a.getInteger(R.styleable.PayButtonView_android_gravity, VALUE_IS_MISSING);
            if (gravity != VALUE_IS_MISSING) {
                mGravity = gravity;
            }

            CharSequence text = a.getText(R.styleable.PayButtonView_android_text);
            if (text != null) {
                mText = text;
            }

            a.recycle();
        }

        mAdditionalViewMarginVertical = mAdditionalViewMarginHorizontal = mHeight / 6;
        mAdditionalViewDimension = mHeight * 4 / 6;
    }


    /**
     * set PayButtonView attributes
     */
    private void setAttributes() {
        payButton.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            payButton.setPaddingRelative(mPaddingStart != VALUE_IS_MISSING ? mPaddingStart : mPaddingLeft, mPaddingTop, mPaddingEnd != VALUE_IS_MISSING ? mPaddingEnd : mPaddingRight, mPaddingBottom);
        }

        payButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);

        //setupTextColor();

        //setupFontTypeFace();

        payButton.setAllCaps(true);

        payButton.setBackgroundDrawable(mBackground);
        payButton.setGravity(mGravity);
        payButton.setText(mText);

        loadingView.useCustomColor = true;
        loadingView.color = ContextCompat.getColor(getContext(), R.color.white);
        loadingView.setPercent(1.0f);

        securityIconView.setImageDrawable(Utils.setImageTint(getContext(), R.drawable.image_security, R.color.white));

        //padding stuff is for better click UX
        securityIconView.setPadding(mAdditionalViewMarginHorizontal * 2, mAdditionalViewMarginVertical, mAdditionalViewMarginHorizontal * 2, mAdditionalViewMarginVertical);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            securityIconView.setPaddingRelative(mAdditionalViewMarginHorizontal * 2, mAdditionalViewMarginVertical, mAdditionalViewMarginHorizontal * 2, mAdditionalViewMarginVertical);
        }
    }

    /**
     * set pay button background from xml file
     */
    private void setInitialBackground(){
        mBackground = ContextCompat.getDrawable(context, R.drawable.btn_pay_selector);
    }

    /**
     * set pay button background from xml file
     *
     * @param selectorId the selector id
     */
    public void setBackgroundSelector(int selectorId){
        mBackground = ContextCompat.getDrawable(context, selectorId);
        payButton.setBackgroundDrawable(mBackground);
    }

    /**
     * set pay button background using color code
     *
     * @param enabledBackgroundColor  the enabled background color
     * @param disabledBackgroundColor the disabled background color
     */
    public void setupBackgroundWithColorList(int enabledBackgroundColor,int disabledBackgroundColor) {
        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled}, // enabled
                        new int[]{-android.R.attr.state_enabled} // disabled
                },
                new int[] {
                        enabledBackgroundColor,   // enabled
                        disabledBackgroundColor, //  disabled
                }
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            payButton.setBackgroundTintList(myColorStateList);
        }

    }

    /**
     * set pay button text type face
     *
     * @param typeface the typeface
     */
    public void setupFontTypeFace(Typeface typeface) {
        if(typeface!=null){
            payButton.setTypeface(typeface, mTextStyle);
        }else {
            payButton.setTypeface(payButton.getTypeface(), mTextStyle);
        }
    }

    /**
     * Setup text color.
     *
     * @param enabledTextColor  the enabled text color
     * @param disabledTextColor the disabled text color
     */
    public void setupTextColor(int enabledTextColor,int disabledTextColor){
        if (payButton.isEnabled())
            payButton.setTextColor(enabledTextColor);
        else
            payButton.setTextColor(disabledTextColor);
    }

    private void setupHeight(){

    }


    /**
     * handle pay button on attached to window
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            layoutParams.height = mHeight;
            layoutParams.setMargins(mMarginLeft, mMarginTop, mMarginRight, mMarginBottom);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (mMarginStart != VALUE_IS_MISSING) {
                    layoutParams.setMarginStart(mMarginStart);
                }
                if (mMarginEnd != VALUE_IS_MISSING) {
                    layoutParams.setMarginEnd(mMarginEnd);
                }
            }

            setLayoutParams(layoutParams);
            requestLayout(); //just to make sure that changes will apply
        }

        if (loadingView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams loadingViewLayoutParams = (MarginLayoutParams) loadingView.getLayoutParams();
            loadingViewLayoutParams.height = loadingViewLayoutParams.width = mAdditionalViewDimension;
            loadingViewLayoutParams.setMargins(mAdditionalViewMarginHorizontal, mAdditionalViewMarginVertical, 0, mAdditionalViewMarginVertical);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                loadingViewLayoutParams.setMarginStart(mAdditionalViewMarginHorizontal);
            }

            loadingView.setLayoutParams(loadingViewLayoutParams);
        }

        if (securityIconView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams iconViewLayoutParams = (MarginLayoutParams) securityIconView.getLayoutParams();
            iconViewLayoutParams.height = mHeight;
            iconViewLayoutParams.width = mAdditionalViewDimension / 2 + mAdditionalViewMarginHorizontal * 2 * 2;

            securityIconView.setLayoutParams(iconViewLayoutParams);
        }
    }

    /**
     * Gets layout id.
     *
     * @return the layout id
     */
    public int getLayoutId() {
        return layoutId;
    }

    /**
     * get loading view
     *
     * @return loading view
     */
    public TapLoadingView getLoadingView() {
        return loadingView;
    }

    /**
     * get pay button
     *
     * @return pay button
     */
    public AppCompatTextView getPayButton() {
        return payButton;
    }

    /**
     * get security icon
     *
     * @return security icon view
     */
    public ImageView getSecurityIconView() {
        return securityIconView;
    }
}
