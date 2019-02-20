package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Create otp request.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class CreateOTPRequest {

    @SerializedName("id")
    @Expose
    private String id;

    private CreateOTPRequest(String id) {
        this.id = id;
    }

    /**
     * The type Builder.
     */
    public final static class Builder {
        private CreateOTPRequest createOTPRequest;

        /**
         * Instantiates a new Builder.
         *
         * @param id the id
         */
        public Builder(String id) {
            createOTPRequest = new CreateOTPRequest(id);
        }

        /**
         * Build create otp request.
         *
         * @return the create otp request
         */
        public CreateOTPRequest build() {
            return createOTPRequest;
        }
    }
}
