package company.tap.gosellapi.internal.custom_views;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.HashMap;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.Utils;
import company.tap.gosellapi.internal.activities.MainActivity;
import company.tap.gosellapi.internal.api.callbacks.APIRequestCallback;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.PaymentInfo;
import company.tap.gosellapi.internal.api.models.Redirect;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;
import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;
import company.tap.gosellapi.internal.exceptions.NoPaymentInfoRequesterProvidedException;
import company.tap.gosellapi.internal.logger.lo;
import gotap.com.tapglkitandroid.gl.Views.TapLoadingView;

public final class GoSellPayLayout extends FrameLayout implements View.OnClickListener {
    public interface GoSellPaymentInfoRequester {
        PaymentInfo getPaymentInfo();
    }

    private static final int VALUE_IS_MISSING = -11111;
    private static final String TAG = "GoSellPayLayout TAG";

    private GoSellPaymentInfoRequester paymentInfoRequester;
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

    public GoSellPayLayout(Context context) {
        super(context);
        init(context, null);
    }

    public GoSellPayLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setPaymentInfoRequester(GoSellPaymentInfoRequester paymentInfoRequester) {
        this.paymentInfoRequester = paymentInfoRequester;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        payButton.setEnabled(enabled);
    }

    private void init(Context context, AttributeSet attrs) {
        payButton = new AppCompatTextView(context, attrs);
        loadingView = new TapLoadingView(context, attrs);
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

        super.setOnClickListener(this);
        payButton.setOnClickListener(this);
        securityIconView.setOnClickListener(this);
    }

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
        
        mBackground = ContextCompat.getDrawable(context, R.drawable.btn_pay_selector);
        mGravity = Gravity.CENTER;
        mText = context.getResources().getString(R.string.pay);

        if (attrs != null) {
            // Attribute initialization
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GoSellPayLayout);

            int height = a.getLayoutDimension(R.styleable.GoSellPayLayout_android_layout_height, VALUE_IS_MISSING);
            if (height != VALUE_IS_MISSING && height > mHeight) {
                mHeight = height;
            }

            //margins
            int marginTop = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_layout_marginTop, VALUE_IS_MISSING);
            if (marginTop != VALUE_IS_MISSING) {
                mMarginTop = marginTop;
            }

            int marginBottom = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_layout_marginBottom, VALUE_IS_MISSING);
            if (marginBottom != VALUE_IS_MISSING) {
                mMarginBottom = marginBottom;
            }

            int marginLeft = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_layout_marginLeft, VALUE_IS_MISSING);
            if (marginLeft != VALUE_IS_MISSING) {
                mMarginLeft = marginLeft;
            }

            int marginRight = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_layout_marginRight, VALUE_IS_MISSING);
            if (marginRight != VALUE_IS_MISSING) {
                mMarginRight = marginRight;
            }

            int marginStart = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_layout_marginStart, VALUE_IS_MISSING);
            if (marginStart != VALUE_IS_MISSING) {
                mMarginStart = marginStart;
            }

            int marginEnd = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_layout_marginEnd, VALUE_IS_MISSING);
            if (marginEnd != VALUE_IS_MISSING) {
                mMarginEnd = marginEnd;
            }

            //paddings
            int paddingTop = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_paddingTop, VALUE_IS_MISSING);
            if (paddingTop != VALUE_IS_MISSING) {
                mPaddingTop = paddingTop;
            }

            int paddingBottom = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_paddingBottom, VALUE_IS_MISSING);
            if (paddingBottom != VALUE_IS_MISSING) {
                mPaddingBottom = paddingBottom;
            }

            int paddingLeft = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_paddingLeft, VALUE_IS_MISSING);
            if (paddingLeft != VALUE_IS_MISSING) {
                mPaddingLeft = paddingLeft;
            }

            int paddingRight = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_paddingRight, VALUE_IS_MISSING);
            if (paddingRight != VALUE_IS_MISSING) {
                mPaddingRight = paddingRight;
            }

            int paddingStart = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_paddingStart, VALUE_IS_MISSING);
            if (paddingStart != VALUE_IS_MISSING) {
                mPaddingStart = paddingStart;
            }

            int paddingEnd = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_paddingEnd, VALUE_IS_MISSING);
            if (paddingEnd != VALUE_IS_MISSING) {
                mPaddingEnd = paddingEnd;
            }

            //text options
            int textSize = a.getDimensionPixelSize(R.styleable.GoSellPayLayout_android_textSize, VALUE_IS_MISSING);
            if (textSize != VALUE_IS_MISSING) {
                mTextSize = textSize;
            }

            int textColor = a.getColor(R.styleable.GoSellPayLayout_android_textColor, VALUE_IS_MISSING);
            if (textColor != VALUE_IS_MISSING) {
                mTextColor = textColor;
            }

            int textStyle = a.getInteger(R.styleable.GoSellPayLayout_android_textStyle, VALUE_IS_MISSING);
            if (textStyle != VALUE_IS_MISSING) {
                mTextStyle = textStyle;
            }

            //other
            Drawable background = a.getDrawable(R.styleable.GoSellPayLayout_android_background);
            if (background != null) {
                mBackground = background;
            }

            int gravity = a.getInteger(R.styleable.GoSellPayLayout_android_gravity, VALUE_IS_MISSING);
            if (gravity != VALUE_IS_MISSING) {
                mGravity = gravity;
            }

            CharSequence text = a.getText(R.styleable.GoSellPayLayout_android_text);
            if (text != null) {
                mText = text;
            }

            a.recycle();
        }

        mAdditionalViewMarginVertical = mAdditionalViewMarginHorizontal = mHeight / 6;
        mAdditionalViewDimension = mHeight * 4 / 6;
    }

    private void setAttributes() {
        payButton.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            payButton.setPaddingRelative(mPaddingStart != VALUE_IS_MISSING ? mPaddingStart : mPaddingLeft, mPaddingTop, mPaddingEnd != VALUE_IS_MISSING ? mPaddingEnd : mPaddingRight, mPaddingBottom);
        }

        payButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        payButton.setTextColor(mTextColor);
        payButton.setTypeface(payButton.getTypeface(), mTextStyle);
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

    //goSell handles clicks
    @Override
    public final void setOnClickListener(@Nullable OnClickListener l) {
    }


    @Override
    public void onClick(View v) {
        if (paymentInfoRequester == null) {
            throw new NoPaymentInfoRequesterProvidedException();
        }

        int i = v.getId();
        if (i == layoutId || i == R.id.pay_button_id) {
            getPaymentTypes();
        } else if (i == R.id.pay_security_icon_id) {
        }
    }

    private void createCharge() {
        HashMap<String, String> chargeMetadata = new HashMap<>();
        chargeMetadata.put("Order Number", "ORD-1001");

        GoSellAPI.getInstance().createCharge(
                new CreateChargeRequest
                        .Builder(10, "KWD", new Redirect("http://return.com/returnurl", "http://return.com/posturl"))
                        .statement_descriptor("Test Txn 001")
                        .description("Test Transaction")
                        .metadata(chargeMetadata)
                        .receipt_sms("96598989898")
                        .receipt_email("test@test.com")
                        .build(),
                new APIRequestCallback<Charge>() {
                    @Override
                    public void onSuccess(int responseCode, Charge serializedResponse) {
                        Log.d(TAG, "onSuccess createCharge: serializedResponse:" + serializedResponse);
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        Log.d(TAG, "onFailure createCharge, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                }
        );

        Intent intent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(intent);
    }

    private void getPaymentTypes() {
        loadingView.start();
        GoSellAPI.getInstance().getPaymentTypes(paymentInfoRequester.getPaymentInfo(),
                new APIRequestCallback<PaymentOptionsResponse>() {
                    @Override
                    public void onSuccess(int responseCode, PaymentOptionsResponse serializedResponse) {
                        loadingView.setForceStop(true);
                        lo.g("payment types success");

                        MainActivity.paymentOptionsResponse = serializedResponse;
                        startMainActivity();
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        loadingView.setForceStop(true);
                        lo.g("payment types fail");
                    }
                });
    }

    private void startMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(intent);
    }
}
