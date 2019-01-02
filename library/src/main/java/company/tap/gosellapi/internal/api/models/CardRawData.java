package company.tap.gosellapi.internal.api.models;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Model for Source object
 */

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class CardRawData {
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

    // TODO: 08.05.2018 address details will be added later

    public CardRawData(String number, String exp_month, String exp_year, String cvc, String name) {
        this.number = number;
        this.exp_month = exp_month;
        this.exp_year = exp_year;
        this.cvc = cvc;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getExp_month() {
        return exp_month;
    }

    public String getExp_year() {
        return exp_year;
    }

    public String getCvc() {
        return cvc;
    }

    public String getName() {
        return name;
    }
}
