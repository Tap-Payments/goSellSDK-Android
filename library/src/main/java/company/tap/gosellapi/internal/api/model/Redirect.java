package company.tap.gosellapi.internal.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene.goltsev on 14.02.2018.
 * <br>
 * Model for redirect object
 */

public final class Redirect {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("return_url")
    @Expose
    private String return_url;

    @SerializedName("post_url")
    @Expose
    private String post_url;

    public Redirect(String return_url, String post_url) {
        this.return_url = return_url;
        this.post_url = post_url;
    }

    public Redirect(String return_url) {
        this.return_url = return_url;
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
    public String getReturn_url() {
        return return_url;
    }

    /**
     * @return The URL you provide to post the charge response after completion of payment. The status of the payment is either succeeded, pending, or failed
     */
    public String getPost_url() {
        return post_url;
    }

    @Override
    public String toString() {
        return "Redirect {" +
                "\n        status =  '" + status + '\'' +
                "\n        url =  '" + url + '\'' +
                "\n        return_url =  '" + return_url + '\'' +
                "\n        post_url =  '" + post_url + '\'' +
                "\n    }";
    }
}
