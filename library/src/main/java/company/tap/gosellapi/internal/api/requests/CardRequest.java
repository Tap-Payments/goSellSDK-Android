package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.RestrictTo;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.crypto.CryptoUtil;

/**
 * Created by eugene.goltsev on 12.02.2018.
 * <br>
 * Model for {@link CardRequest} object
 */

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class CardRequest {
    /**
     * Builder to create {@link CardRequest} instance
     */
    public final static class Builder {
        private CardRequest card;
        private String encryptionKey;

        /**
         * Builder constructor with necessary params
         * @param number The card number, as a string without any separators.
         * @param exp_month Two digit number representing the card's expiration month.
         * @param exp_year Two or four digit number representing the card's expiration year.
         * @param cvc Card's security code.
         * @param encryptionKey Encryption key to protect card data.
         */
        public Builder(String number, String exp_month, String exp_year, String cvc, String encryptionKey) {
            card = new CardRequest(number, exp_month, exp_year, cvc);
            this.encryptionKey = encryptionKey;
        }

        public Builder name(String name) {
            card.setName(name);
            return this;
        }

        public Builder address_city(String address_city) {
            card.address_city = address_city;
            return this;
        }

        public Builder address_country(String address_country) {
            card.address_country = address_country;
            return this;
        }

        public Builder address_line1(String address_line1) {
            card.address_line1 = address_line1;
            return this;
        }

        public Builder address_line2(String address_line2) {
            card.address_line2 = address_line2;
            return this;
        }

        public Builder address_state(String address_state) {
            card.address_state = address_state;
            return this;
        }

        public Builder address_zip(String address_zip) {
            card.address_zip = address_zip;
            return this;
        }

        public CardRequest build() {
            String cryptedDataJson = new Gson().toJson(card.cryptedDataInternal);
            card.cryptedData = CryptoUtil.encryptJsonString(cryptedDataJson, encryptionKey);

            card.number = null;
            card.exp_month = null;
            card.exp_year = null;
            card.cvc = null;
            card.name = null;

            return card;
        }
    }

    @SerializedName("crypted_data")
    @Expose
    private String cryptedData;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("exp_month")
    @Expose
    private String exp_month;

    @SerializedName("exp_year")
    @Expose
    private String exp_year;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("address_city")
    @Expose
    private String address_city;

    @SerializedName("address_country")
    @Expose
    private String address_country;

    @SerializedName("address_line1")
    @Expose
    private String address_line1;

    @SerializedName("address_line2")
    @Expose
    private String address_line2;

    @SerializedName("address_state")
    @Expose
    private String address_state;

    @SerializedName("address_zip")
    @Expose
    private String address_zip;

    @SerializedName("cvc")
    @Expose
    private String cvc;

    private CryptedData cryptedDataInternal;

    //only mandatory parameters
    private CardRequest(String number, String exp_month, String exp_year, String cvc) {
        cryptedDataInternal = new CryptedData(number, exp_month, exp_year, cvc);
    }

    private void setName(String name) {
        cryptedDataInternal.name = name;
    }

        //helper class to encrypt data
        private final class CryptedData {
            @SerializedName("number")
            @Expose
            private String number;

            @SerializedName("exp_month")
            @Expose
            private String exp_month;

            @SerializedName("exp_year")
            @Expose
            private String exp_year;

            @SerializedName("cvc")
            @Expose
            private String cvc;

            @SerializedName("name")
            @Expose
            private String name;

            private CryptedData(String number, String exp_month, String exp_year, String cvc) {
                this.number = number;
                this.exp_month = exp_month;
                this.exp_year = exp_year;
                this.cvc = cvc;
            }
        }
}
