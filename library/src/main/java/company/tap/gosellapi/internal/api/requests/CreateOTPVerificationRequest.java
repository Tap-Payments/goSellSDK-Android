package company.tap.gosellapi.internal.api.requests;

import android.os.Build;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.AuthenticationType;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class CreateOTPVerificationRequest {

    @SerializedName("type")
    @Expose
    private AuthenticationType type;

    @SerializedName("value")
    @Expose
    private String value;

    private CreateOTPVerificationRequest(AuthenticationType id, String otp) {
        this.type = id;
        this.value = otp;
    }

    public final static class Builder {
        private CreateOTPVerificationRequest createOTPVerificationRequest;

        public Builder(AuthenticationType id, String otp) {
            createOTPVerificationRequest = new CreateOTPVerificationRequest(id, otp);
        }

        public CreateOTPVerificationRequest build() {
            return createOTPVerificationRequest;
        }
    }
}
