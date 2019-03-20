package company.tap.gosellapi.internal.utils;

import android.app.Activity;

import company.tap.gosellapi.R;


/**
 * The enum Expiration date dialog theme.
 */
public enum ExpirationDateDialogTheme {

    /**
     * Light expiration date dialog theme.
     */
    LIGHT(R.color.bt_black_87, R.color.bt_white_87, R.color.bt_black_38),
    /**
     * Dark expiration date dialog theme.
     */
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

    /**
     * Detect theme expiration date dialog theme.
     *
     * @param activity the activity
     * @return the expiration date dialog theme
     */
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

    /**
     * Gets item text color.
     *
     * @return the item text color
     */
    public int getItemTextColor() {
        return mResolvedItemTextColor;
    }

    /**
     * Gets item inverted text color.
     *
     * @return the item inverted text color
     */
    public int getItemInvertedTextColor() {
        return mResolvedItemInverseTextColor;
    }

    /**
     * Gets item disabled text color.
     *
     * @return the item disabled text color
     */
    public int getItemDisabledTextColor() {
        return mResolvedItemDisabledTextColor;
    }

    /**
     * Gets selected item background.
     *
     * @return the selected item background
     */
    public int getSelectedItemBackground() {
        return mResolvedSelectedItemBackground;
    }
}

