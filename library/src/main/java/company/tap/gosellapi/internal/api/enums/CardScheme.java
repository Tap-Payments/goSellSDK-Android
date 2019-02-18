package company.tap.gosellapi.internal.api.enums;

import com.google.gson.annotations.SerializedName;

import company.tap.tapcardvalidator_android.CardBrand;

public enum CardScheme {

    @SerializedName("KNET")             KNET,
    @SerializedName("VISA")             VISA,
    @SerializedName("MASTERCARD")       MASTERCARD,
    @SerializedName("AMERICAN_EXPRESS") AMERICAN_EXPRESS,
    @SerializedName("MADA")             MADA,
    @SerializedName("BENEFIT")          BENEFIT,
    @SerializedName("SADAD_ACCOUNT")    SADAD,
    @SerializedName("FAWRY")            FAWRY,
    @SerializedName("NAPS")             NAPS,
    @SerializedName("OMAN_NET")         OMAN_NET;

    public CardBrand getCardBrand() {

        switch (this) {

            case KNET:              return CardBrand.knet;
            case VISA:              return CardBrand.visa;
            case MASTERCARD:        return CardBrand.masterCard;
            case AMERICAN_EXPRESS:  return CardBrand.americanExpress;
            case MADA:              return CardBrand.mada;
            case BENEFIT:           return CardBrand.benefit;
            case SADAD:             return CardBrand.sadad;
            case FAWRY:             return CardBrand.fawry;
            case NAPS:              return CardBrand.naps;
            case OMAN_NET:          return CardBrand.omanNet;

            default:                return CardBrand.unknown;
        }
    }

}
