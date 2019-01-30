package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class SaveCard extends Charge{

    @SerializedName("save_card")
    @Expose
    @NonNull private boolean save_card;

    @SerializedName("card")
    @Expose
    @NonNull private Card card;

    @SerializedName("risk")
    @Expose
    @Nullable private boolean risk;

    @SerializedName("issuer")
    @Expose
    @Nullable private boolean issuer;

    @SerializedName("promo")
    @Expose
    @Nullable private boolean promo;

    @SerializedName("loyalty")
    @Expose
    @Nullable private boolean loyalty;



    public boolean isSave_card() {
        return save_card;
    }

    @NonNull
    public Card getCard() {
        return card;
    }

    public boolean isRisk() {
        return risk;
    }

    public boolean isIssuer() {
        return issuer;
    }

    public boolean isPromo() {
        return promo;
    }

    public boolean isLoyalty() {
        return loyalty;
    }
}
