package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class CreateOTPRequest {

    @SerializedName("id")
    @Expose
    private String id;

    private CreateOTPRequest(String id) {
        this.id = id;
    }

    public final static class Builder {
        private CreateOTPRequest createOTPRequest;

        public Builder(String id) {
            createOTPRequest = new CreateOTPRequest(id);
        }

        public CreateOTPRequest build() {
            return createOTPRequest;
        }
    }
}
