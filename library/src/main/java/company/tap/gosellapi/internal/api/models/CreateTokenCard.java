package company.tap.gosellapi.internal.api.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.crypto.CryptoUtil;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.interfaces.SecureSerializable;

/**
 * The type Create token card.
 */
public final class CreateTokenCard {

  @SerializedName("crypted_data")
  @Expose
  @NonNull private String sensitiveCardData;

  @SerializedName("address")
  @Expose
  @Nullable private Address address;


    /**
     * Instantiates a new Create token card.
     *
     * @param cardNumber      the card number
     * @param expirationMonth the expiration month
     * @param expirationYear  the expiration year
     * @param cvc             the cvc
     * @param cardholderName  the cardholder name
     * @param address         the address
     */
    public CreateTokenCard(String cardNumber, String expirationMonth, String expirationYear, String cvc, String cardholderName, @Nullable Address address) {

    SensitiveCardData _sensitiveCardData  = new SensitiveCardData(cardNumber, expirationYear, expirationMonth, cvc, cardholderName);

    String cryptedDataJson = new Gson().toJson(_sensitiveCardData);
    this.sensitiveCardData = CryptoUtil.encryptJsonString(cryptedDataJson, PaymentDataManager.getInstance().getSDKSettings().getData().getEncryptionKey());

    this.address            = address;
  }

  private final class SensitiveCardData implements SecureSerializable {

    @SerializedName("number")
    @Expose
    @NonNull private String number;

    @SerializedName("exp_year")
    @Expose
    @NonNull private String expirationYear;

    @SerializedName("exp_month")
    @Expose
    @NonNull private String expirationMonth;

    @SerializedName("cvc")
    @Expose
    @NonNull private String cvc;

    @SerializedName("name")
    @Expose
    @NonNull private String cardholderName;

    private SensitiveCardData(String number, String expirationYear, String expirationMonth, String cvc, String cardholderName) {

      this.number             = number;
      this.expirationYear     = expirationYear;
      this.expirationMonth    = expirationMonth;
      this.cvc                = cvc;
      this.cardholderName     = cardholderName;
    }
  }
}