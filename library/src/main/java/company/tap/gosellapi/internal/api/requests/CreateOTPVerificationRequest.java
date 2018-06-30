package company.tap.gosellapi.internal.api.requests;

import android.os.Build;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class CreateOTPVerificationRequest {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("otp")
    @Expose
    private String otp;

    private CreateOTPVerificationRequest(String id, String otp) {
        this.id = id;
        this.otp = otp;
    }

    public final static class Builder {
        private CreateOTPVerificationRequest createOTPVerificationRequest;

        public Builder(String id, String otp) {
            createOTPVerificationRequest = new CreateOTPVerificationRequest(id, otp);
        }

        public CreateOTPVerificationRequest build() {
            return createOTPVerificationRequest;
        }
    }
}
