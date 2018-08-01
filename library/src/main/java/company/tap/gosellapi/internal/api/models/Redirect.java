package company.tap.gosellapi.internal.api.models;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Model for redirect object
 */

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class Redirect {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("return_url")
    @Expose
    private String returnURL;

    @SerializedName("post_url")
    @Expose
    private String postURL;

    public Redirect(String returnURL, String postURL) {

        this.returnURL = returnURL;
        this.postURL = postURL;
    }

    /**
     * @return The status of the payment, is either succeeded, pending, or failed
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return This is the payment URL that will be passed to you to forward it to your customer. This url, will contain a checkout page with all the details provided in the request&#8217;s body.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return The URL you provide to redirect the customer to after they completed their payment. The status of the payment is either succeeded, pending, or failed. Also "payload" (charge response) will be posted to the return_url
     */
    public String getReturnURL() {  return returnURL; }

    /**
     * @return The URL you provide to post the charge response after completion of payment. The status of the payment is either succeeded, pending, or failed
     */
    public String getPostURL() {
        return postURL;
    }

    @Override
    public String toString() {
        return "Redirect {" +
                "\n        status =  '" + status + '\'' +
                "\n        url =  '" + url + '\'' +
                "\n        return_url =  '" + returnURL + '\'' +
                "\n        post_url =  '" + postURL + '\'' +
                "\n    }";
    }
}
