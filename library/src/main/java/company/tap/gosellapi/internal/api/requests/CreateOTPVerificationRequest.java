package company.tap.gosellapi.internal.api.requests;

import android.os.Build;
import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import company.tap.gosellapi.internal.api.enums.AuthenticationType;

/**
 * The type Create otp verification request.
 */
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

    /**
     * The type Builder.
     */
    public final static class Builder {
        private CreateOTPVerificationRequest createOTPVerificationRequest;

        /**
         * Instantiates a new Builder.
         *
         * @param id  the id
         * @param otp the otp
         */
        public Builder(AuthenticationType id, String otp) {
            createOTPVerificationRequest = new CreateOTPVerificationRequest(id, otp);
        }

        /**
         * Build create otp verification request.
         *
         * @return the create otp verification request
         */
        public CreateOTPVerificationRequest build() {
            return createOTPVerificationRequest;
        }
    }
}
