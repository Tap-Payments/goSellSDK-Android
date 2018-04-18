package company.tap.gosellapi.internal.custom_views;

import android.content.Context;
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

import java.util.HashMap;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.api_service.APIRequestCallback;
import company.tap.gosellapi.internal.api.api_service.GoSellAPI;
import company.tap.gosellapi.internal.api.api_service.GoSellError;
import company.tap.gosellapi.internal.api.model.Charge;
import company.tap.gosellapi.internal.api.model.Redirect;
import company.tap.gosellapi.internal.api.requests.CreateChargeRequest;

public class GoSellPayButton extends AppCompatTextView implements View.OnClickListener {
    private static final int VALUE_IS_MISSING = -11111;
    private static final String TAG = "GoSellPayButton TAG";

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

    private Drawable mBackground;
    private int mGravity;
    private CharSequence mText;

    public GoSellPayButton(Context context) {
        super(context);
        init(context, null);
    }

    public GoSellPayButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttributes(context, attrs);
        setAttributes();

        super.setOnClickListener(this);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        mMarginTop = context.getResources().getDimensionPixelSize(R.dimen.btn_margin_vertical);
        mMarginBottom = context.getResources().getDimensionPixelSize(R.dimen.btn_margin_vertical);
        mMarginLeft = context.getResources().getDimensionPixelSize(R.dimen.btn_margin_horizontal);
        mMarginStart = VALUE_IS_MISSING;
        mMarginRight = context.getResources().getDimensionPixelSize(R.dimen.btn_margin_horizontal);
        mMarginEnd = VALUE_IS_MISSING;

        mPaddingTop = context.getResources().getDimensionPixelSize(R.dimen.btn_padding_vertical);
        mPaddingBottom = context.getResources().getDimensionPixelSize(R.dimen.btn_padding_vertical);
        mPaddingLeft = context.getResources().getDimensionPixelSize(R.dimen.btn_padding_horizontal);
        mPaddingStart = VALUE_IS_MISSING;
        mPaddingRight = context.getResources().getDimensionPixelSize(R.dimen.btn_padding_horizontal);
        mPaddingEnd = VALUE_IS_MISSING;

        mTextSize = context.getResources().getDimensionPixelSize(R.dimen.btn_text_size);
        mTextColor = ContextCompat.getColor(context, R.color.white);
        mTextStyle = Typeface.BOLD;
        
        mBackground = ContextCompat.getDrawable(context, R.drawable.btn_pay_selector);
        mGravity = Gravity.CENTER;
        mText = context.getResources().getString(R.string.pay);

        if (attrs != null) {
            // Attribute initialization
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GoSellPayButton);

            //margins
            int marginTop = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_layout_marginTop, VALUE_IS_MISSING);
            if (marginTop != VALUE_IS_MISSING) {
                mMarginTop = marginTop;
            }

            int marginBottom = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_layout_marginBottom, VALUE_IS_MISSING);
            if (marginBottom != VALUE_IS_MISSING) {
                mMarginBottom = marginBottom;
            }

            int marginLeft = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_layout_marginLeft, VALUE_IS_MISSING);
            if (marginLeft != VALUE_IS_MISSING) {
                mMarginLeft = marginLeft;
            }

            int marginRight = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_layout_marginRight, VALUE_IS_MISSING);
            if (marginRight != VALUE_IS_MISSING) {
                mMarginRight = marginRight;
            }

            int marginStart = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_layout_marginStart, VALUE_IS_MISSING);
            if (marginStart != VALUE_IS_MISSING) {
                mMarginStart = marginStart;
            }

            int marginEnd = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_layout_marginEnd, VALUE_IS_MISSING);
            if (marginEnd != VALUE_IS_MISSING) {
                mMarginEnd = marginEnd;
            }

            //paddings
            int paddingTop = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_paddingTop, VALUE_IS_MISSING);
            if (paddingTop != VALUE_IS_MISSING) {
                mPaddingTop = paddingTop;
            }

            int paddingBottom = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_paddingBottom, VALUE_IS_MISSING);
            if (paddingBottom != VALUE_IS_MISSING) {
                mPaddingBottom = paddingBottom;
            }

            int paddingLeft = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_paddingLeft, VALUE_IS_MISSING);
            if (paddingLeft != VALUE_IS_MISSING) {
                mPaddingLeft = paddingLeft;
            }

            int paddingRight = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_paddingRight, VALUE_IS_MISSING);
            if (paddingRight != VALUE_IS_MISSING) {
                mPaddingRight = paddingRight;
            }

            int paddingStart = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_paddingStart, VALUE_IS_MISSING);
            if (paddingStart != VALUE_IS_MISSING) {
                mPaddingStart = paddingStart;
            }

            int paddingEnd = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_paddingEnd, VALUE_IS_MISSING);
            if (paddingEnd != VALUE_IS_MISSING) {
                mPaddingEnd = paddingEnd;
            }

            //text options
            int textSize = a.getDimensionPixelSize(R.styleable.GoSellPayButton_android_textSize, VALUE_IS_MISSING);
            if (textSize != VALUE_IS_MISSING) {
                mTextSize = textSize;
            }

            int textColor = a.getColor(R.styleable.GoSellPayButton_android_textColor, VALUE_IS_MISSING);
            if (textColor != VALUE_IS_MISSING) {
                mTextColor = textColor;
            }

            int textStyle = a.getInteger(R.styleable.GoSellPayButton_android_textStyle, VALUE_IS_MISSING);
            if (textStyle != VALUE_IS_MISSING) {
                mTextStyle = textStyle;
            }

            //other
            Drawable background = a.getDrawable(R.styleable.GoSellPayButton_android_background);
            if (background != null) {
                mBackground = background;
            }

            int gravity = a.getInteger(R.styleable.GoSellPayButton_android_gravity, VALUE_IS_MISSING);
            if (gravity != VALUE_IS_MISSING) {
                mGravity = gravity;
            }

            CharSequence text = a.getText(R.styleable.GoSellPayButton_android_text);
            if (text != null) {
                mText = text;
            }

            a.recycle();
        }
    }

    private void setAttributes() {
        setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setPaddingRelative(mPaddingStart != VALUE_IS_MISSING ? mPaddingStart : mPaddingLeft, mPaddingTop, mPaddingEnd != VALUE_IS_MISSING ? mPaddingEnd : mPaddingRight, mPaddingBottom);
        }

        setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        setTextColor(mTextColor);
        setTypeface(getTypeface(), mTextStyle);

        setBackgroundDrawable(mBackground);
        setGravity(mGravity);
        setText(mText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
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
            requestLayout();
        }
    }

    //goSell handles clicks
    @Override
    public final void setOnClickListener(@Nullable OnClickListener l) {
    }


    @Override
    public void onClick(View v) {
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
    }
}
