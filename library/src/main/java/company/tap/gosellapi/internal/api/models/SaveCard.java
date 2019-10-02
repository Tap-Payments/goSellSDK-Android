package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Save card.
 */
public final class SaveCard extends Charge{

    @SerializedName("save_card")
    @Expose
    @NonNull private boolean save_card;

//    @SerializedName("card")
//    @Expose
//    @NonNull private Card card;

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


    /**
     * Is save card boolean.
     *
     * @return the boolean
     */
    public boolean isSave_card() {
        return save_card;
    }

//    /**
//     * Gets card.
//     *
//     * @return the card
//     */
//    @NonNull
//    public Card getCard() {
//        return card;
//    }

    /**
     * Is risk boolean.
     *
     * @return the boolean
     */
    public boolean isRisk() {
        return risk;
    }

    /**
     * Is issuer boolean.
     *
     * @return the boolean
     */
    public boolean isIssuer() {
        return issuer;
    }

    /**
     * Is promo boolean.
     *
     * @return the boolean
     */
    public boolean isPromo() {
        return promo;
    }

    /**
     * Is loyalty boolean.
     *
     * @return the boolean
     */
    public boolean isLoyalty() {
        return loyalty;
    }
}
