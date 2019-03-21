package company.tap.gosellapi.open.controllers;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import company.tap.gosellapi.R;
import company.tap.gosellapi.open.enums.AppearanceMode;

/**
 * The type Settings manager.
 */
public class ThemeObject {


        private String sdkLanguage="en";

        /**
         * Appearance Mode
         */
        private AppearanceMode appearanceMode ;

        /**
         *  Appearance Background
         */
        private int                     translucentColor;

        /**
         *  Appearance Header
         */
        private Typeface                headerFont;
        private int                     headerTextColor;
        private int                     headerTextSize;
        private int                     headerBackgroundColor;


        /**
         * Appearance : Card Input Fields
         */
        private Typeface                cardInputFont;
        private int                     cardInputTextColor;
        private int                     cardInputInvalidTextColor;
        private int                     cardInputPlaceholderTextColor;
        private Typeface                cardInputDescriptionFont;
        private int                     cardInputDescriptionColor;
        private int                     saveCardSwitchOffThumbTint;
        private int                     saveCardSwitchOnThumbTint;
        private int                     saveCardSwitchOffTrackTint;
        private int                     saveCardSwitchOnTrackTint;
        private Drawable                scanIconDrawable;


        /**
         *  Tap Button
         */
        private int                     payButtonResourceId;
        private boolean                 payButtonResourceIdFlag;

        private int                     payButtonDisabledBackgroundColor;
        private int                     payButtonEnabledBackgroundColor;
        private Typeface                payButtonFont;
        private int                     payButtonDisabledTitleColor;
        private int                     payButtonEnabledTitleColor;
//        private int                     payButtonHeight;
        private int                     payButtonCornerRadius;
        private boolean                 payButtSecurityIconVisible=true;
        private boolean                 payButtLoaderVisible=true;
        private int                     payButtonTextSize;


        ////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * SDK Language
         * @param sdkLanguage
         */
        public ThemeObject setSdkLanguage(String sdkLanguage) {
                this.sdkLanguage = sdkLanguage;
        return this;
        }

        /**
         * Appearance Mode [FullScreen  - Windowed]
         * @param appearanceMode
         */
        public ThemeObject setAppearanceMode(AppearanceMode appearanceMode) {
                this.appearanceMode = appearanceMode;
                return this;
        }



        /**
         * Translucent color
         * @param translucentColor
         */
        public ThemeObject setTranslucentColor(int translucentColor) {
                this.translucentColor = translucentColor;
                return this;
        }

        /**
         * Header Font Typeface
         * @param headerFont
         */
        public ThemeObject setHeaderFont(Typeface headerFont) {
                this.headerFont = headerFont;
                return this;
        }

        /**
         * Header Text Color
         * @param headerTextColor
         */
        public ThemeObject setHeaderTextColor(int headerTextColor) {
                this.headerTextColor = headerTextColor;
                return this;
        }

        /**
         * Header text size
         * @param textSize
         * @return
         */
        public ThemeObject setHeaderTextSize(int textSize){
                this.headerTextSize = textSize;
                return this;
        }

        /**
         * Header background color
         * @param headerBackgroundColor
         */
        public ThemeObject setHeaderBackgroundColor(int headerBackgroundColor) {
                this.headerBackgroundColor = headerBackgroundColor;
                return this;
        }

        /**
         * Card input font typeface
         * @param cardInputFontTypeFace
         */
        public ThemeObject setCardInputFont(Typeface cardInputFontTypeFace) {
                this.cardInputFont = cardInputFontTypeFace;
                return this;
        }

        /**
         * Card input text color
         * @param cardInputTextColor
         */
        public ThemeObject setCardInputTextColor(int cardInputTextColor) {
                this.cardInputTextColor = cardInputTextColor;
                return this;
        }

        /**
         * Card input invalid text color
         * @param cardInputInvalidTextColor
         */
        public ThemeObject setCardInputInvalidTextColor(int cardInputInvalidTextColor) {
                this.cardInputInvalidTextColor = cardInputInvalidTextColor;
                return this;
        }

        /**
         * Card input placeholder text color
         * @param cardInputPlaceholderTextColor
         */
        public ThemeObject setCardInputPlaceholderTextColor(int cardInputPlaceholderTextColor) {
                this.cardInputPlaceholderTextColor = cardInputPlaceholderTextColor;
                return this;
        }

        /**
         * Card input description font typeface
         * @param cardInputDescriptionFont
         */
        public ThemeObject setCardInputDescriptionFont(Typeface cardInputDescriptionFont) {
                this.cardInputDescriptionFont = cardInputDescriptionFont;
                return this;
        }

        /**
         * card input description color
         * @param cardInputDescriptionColor
         */
        public ThemeObject setCardInputDescriptionColor(int cardInputDescriptionColor) {
                this.cardInputDescriptionColor = cardInputDescriptionColor;
                return this;
        }

        /**
         * card switch off thumb tint color
         * @param saveCardSwitchOffTint
         */
        public ThemeObject setSaveCardSwitchOffThumbTint(int saveCardSwitchOffTint) {
                this.saveCardSwitchOffThumbTint = saveCardSwitchOffTint;
                return this;
        }
        /**
         * card switch on thumb tint color
         * @param saveCardSwitchOnTint
         */
        public ThemeObject setSaveCardSwitchOnThumbTint(int saveCardSwitchOnTint) {
                this.saveCardSwitchOnThumbTint = saveCardSwitchOnTint;
                return this;
        }

        /**
         * card switch off thumb track color
         * @param saveCardSwitchOffTint
         */
        public ThemeObject setSaveCardSwitchOffTrackTint(int saveCardSwitchOffTint) {
                this.saveCardSwitchOffTrackTint = saveCardSwitchOffTint;
                return this;
        }
        /**
         * card switch on track tint color
         * @param saveCardSwitchOnTint
         */
        public ThemeObject setSaveCardSwitchOnTrackTint(int saveCardSwitchOnTint) {
                this.saveCardSwitchOnTrackTint = saveCardSwitchOnTint;
                return this;
        }

        public ThemeObject setScanIconDrawable(Drawable scanIconDrawable){
                this.scanIconDrawable = scanIconDrawable;
                return  this;
        }

        /**
         * pay button resource id
         * @param payButtonResourceId
         */
        public ThemeObject setPayButtonResourceId(int payButtonResourceId) {
                this.payButtonResourceId = payButtonResourceId;
                this.payButtonResourceIdFlag = true;
                return  this;
        }

        /**
         * Pay button disabled background color
         * @param payButtonDisabledBackgroundColor
         */
        public ThemeObject setPayButtonDisabledBackgroundColor(int payButtonDisabledBackgroundColor) {
                this.payButtonDisabledBackgroundColor = payButtonDisabledBackgroundColor;
                return this;
        }

        /**
         * pay button enable background color
         * @param payButtonEnabledBackgroundColor
         */
        public ThemeObject setPayButtonEnabledBackgroundColor(int payButtonEnabledBackgroundColor) {
                this.payButtonEnabledBackgroundColor = payButtonEnabledBackgroundColor;
                return this;
        }

        /**
         * pay button font typeface
         * @param payButtonFont
         */
        public ThemeObject setPayButtonFont(Typeface payButtonFont) {
                this.payButtonFont = payButtonFont;
                return this;
        }

        /**
         * pay button disabled title color
         * @param payButtonDisabledTitleColor
         */
        public ThemeObject setPayButtonDisabledTitleColor(int payButtonDisabledTitleColor) {
                this.payButtonDisabledTitleColor = payButtonDisabledTitleColor;
                return this;
        }

        /**
         * pay button enabled title color
         * @param payButtonEnabledTitleColor
         */
        public ThemeObject setPayButtonEnabledTitleColor(int payButtonEnabledTitleColor) {
                this.payButtonEnabledTitleColor = payButtonEnabledTitleColor;
                return this;
        }


        /**
         * pay button corner radius
         * @param payButtonCornerRadius
         */
        public ThemeObject setPayButtonCornerRadius(int payButtonCornerRadius) {
                this.payButtonCornerRadius = payButtonCornerRadius;
                return this;
        }

        /**
         * pay button view or hide security icon
         * @param payButtSecurityIconVisible
         */
        public ThemeObject setPayButtonSecurityIconVisible(boolean payButtSecurityIconVisible) {
                this.payButtSecurityIconVisible = payButtSecurityIconVisible;
                return this;
        }

        /**
         *
         * @param payButtLoaderVisible
         */
        public ThemeObject setPayButtonLoaderVisible(boolean payButtLoaderVisible) {
                this.payButtLoaderVisible = payButtLoaderVisible;
                return  this;
        }

        /**
         * pay button text size
         * @param payButtonTextSize
         */
        public ThemeObject setPayButtonTextSize(int payButtonTextSize) {
                this.payButtonTextSize = payButtonTextSize;
                return this;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * @return Appearance mode [Windowed - FullScreen]
         */
        public AppearanceMode getAppearanceMode() {
                return this.appearanceMode;
        }

        /**
         * @return sdk language
         */
        public String getSdkLanguage() {
                return this.sdkLanguage;
        }



        /**
         * @return  header font typeface
         */
        public Typeface getHeaderFont() {
                return this.headerFont;
        }

        /**
         * @return header text color
         */
        public int getHeaderTextColor() {
                return this.headerTextColor;
        }


        /**
         * @return text size
         */
        public int getHeaderTextSize() {
                return this.headerTextSize;
        }

        /**
         * @return background color
         */
        public int getHeaderBackgroundColor() {
                return this.headerBackgroundColor;
        }

        /**
         * @return card input font type face
         */
        public Typeface getCardInputFontTypeFace() {
                return this.cardInputFont;
        }

        /**
         * @return card input text color
         */
        public int getCardInputTextColor() {
                return this.cardInputTextColor;
        }

        /**
         * @return card input invalidate text color
         */
        public int getCardInputInvalidTextColor() {
                return this.cardInputInvalidTextColor;
        }

        /**
         * @return card input place holder text color
         */
        public int getCardInputPlaceholderTextColor() {
                return this.cardInputPlaceholderTextColor;
        }


        /**
         * @return save card switch off tint
         */
        public int getSaveCardSwitchOffThumbTint() {
                return this.saveCardSwitchOffThumbTint;
        }

        /**
         * @return save card switch on tint
         */
        public int getSaveCardSwitchOnThumbTint() {
                        return this.saveCardSwitchOnThumbTint;
        }

        /**
         * @return save card switch off track tint
         */
        public int getSaveCardSwitchOffTrackTint() {
                return this.saveCardSwitchOffTrackTint;
        }

        /**
         * @return save card switch on track tint
         */
        public int getSaveCardSwitchOnTrackTint() {
                return this.saveCardSwitchOnTrackTint;
        }


        /**
         * @return Scan icon drawable
         */
        public Drawable getScanIconDrawable(Context context) {
                if(scanIconDrawable!=null)
                return this.scanIconDrawable;

                return  context.getResources().getDrawable(R.drawable.btn_card_scanner_normal);
        }


        /**
         * Card Input font
         * @return
         */
        public Typeface getCardInputFont() {
                return this.cardInputFont;
        }

        /**
         * Pay Button resource id
         * @return
         */
        public int getPayButtonResourceId() {
                if(this.payButtonResourceIdFlag)
                return this.payButtonResourceId;

                return R.drawable.btn_pay_selector;
        }

        /**
         * @return pay button disabled background color
         */
        public int getPayButtonDisabledBackgroundColor() {
                return this.payButtonDisabledBackgroundColor;
        }

        /**
         * @return pay button enabled background color
         */
        public int getPayButtonEnabledBackgroundColor() {
                return this.payButtonEnabledBackgroundColor;
        }

        /**
         * @return pay button font
         */
        public Typeface getPayButtonFont() {
                return this.payButtonFont;
        }

        /**
         * @return pay button disabled title text color
         */
        public int getPayButtonDisabledTitleColor() {

                        return this.payButtonDisabledTitleColor;

        }

        /**
         * @return pay button enabled title color
         */
        public int getPayButtonEnabledTitleColor() {


                        return this.payButtonEnabledTitleColor;

        }

        /**
         * @return pay button corner radius
         */
        public int getPayButtonCornerRadius() {
                return this.payButtonCornerRadius;
        }

        /**
         * @return pay button security icon visible
         */
        public boolean isPayButtSecurityIconVisible() {
                return this.payButtSecurityIconVisible;
        }

        /**
         *
         * @return
         */
        public boolean isPayButtLoaderVisible() {
                return this.payButtLoaderVisible;
        }

        /**
         *
         * @return Text size
         */
        public int getPayButtonTextSize() {
                if(payButtonTextSize!=0)
                return this.payButtonTextSize;

                return 14;
        }


        //////////////////////////////////////////  Single Instance ////////////////////////

        /**
         *  Get Shared instance of ThemeObject
         * @return ThemeObject
         */
        public static ThemeObject getInstance(){
                return  SingleInstanceAdmin.instance;
        }

        private static class SingleInstanceAdmin{
                /**
                 * The Instance.
                 */
               public final static ThemeObject instance = new ThemeObject();
        }

        }