package company.tap.gosellapi.internal.utils;

import android.app.Activity;

import company.tap.gosellapi.R;


public enum ExpirationDateDialogTheme {

    LIGHT(R.color.bt_black_87, R.color.bt_white_87, R.color.bt_black_38),
    DARK(R.color.black, R.color.bt_black_87, R.color.french_gray);

    private final int mItemTextColor;
    private final int mItemInverseTextColor;
    private final int mItemDisabledTextColor;

    private int mResolvedItemTextColor;
    private int mResolvedItemInverseTextColor;
    private int mResolvedItemDisabledTextColor;
    private int mResolvedSelectedItemBackground;

    ExpirationDateDialogTheme(int itemTextColor, int itemInverseTextColor, int itemDisabledTextColor) {
        mItemTextColor = itemTextColor;
        mItemInverseTextColor = itemInverseTextColor;
        mItemDisabledTextColor = itemDisabledTextColor;
    }

    public static ExpirationDateDialogTheme detectTheme(Activity activity) {
        ExpirationDateDialogTheme theme;
        if (ViewUtils.isDarkBackground(activity)) {
            theme = ExpirationDateDialogTheme.LIGHT;
        } else {
            theme = ExpirationDateDialogTheme.DARK;
        }

        theme.mResolvedItemTextColor = activity.getResources().getColor(theme.mItemTextColor);
        theme.mResolvedItemInverseTextColor = ColorUtils.getColor(activity,
                "textColorPrimaryInverse", theme.mItemInverseTextColor);
        theme.mResolvedItemDisabledTextColor = activity.getResources()
                .getColor(theme.mItemDisabledTextColor);
        theme.mResolvedSelectedItemBackground = ColorUtils.getColor(activity, "mercury",
                R.color.mercury);

        return theme;
    }

    public int getItemTextColor() {
        return mResolvedItemTextColor;
    }

    public int getItemInvertedTextColor() {
        return mResolvedItemInverseTextColor;
    }

    public int getItemDisabledTextColor() {
        return mResolvedItemDisabledTextColor;
    }

    public int getSelectedItemBackground() {
        return mResolvedSelectedItemBackground;
    }
}

