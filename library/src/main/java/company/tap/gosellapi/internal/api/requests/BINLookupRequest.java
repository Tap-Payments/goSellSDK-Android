package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class BINLookupRequest {

    @SerializedName("bin_number")
    @Expose
    private String binNumber;

    private BINLookupRequest(String binNumber) {
        this.binNumber = binNumber;
    }

    public final static class Builder {
        private BINLookupRequest binLookupRequest;

        public Builder(String binNumber) {
            binLookupRequest = new BINLookupRequest(binNumber);
        }

        public BINLookupRequest build() {
            return binLookupRequest;
        }
    }
}
