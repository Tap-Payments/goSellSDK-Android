package company.tap.gosellapi.internal.api.requests;

import android.support.annotation.RestrictTo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Bin lookup request.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class BINLookupRequest {

    @SerializedName("bin_number")
    @Expose
    private String binNumber;

    private BINLookupRequest(String binNumber) {
        this.binNumber = binNumber;
    }

    /**
     * The type Builder.
     */
    public final static class Builder {
        private BINLookupRequest binLookupRequest;

        /**
         * Instantiates a new Builder.
         *
         * @param binNumber the bin number
         */
        public Builder(String binNumber) {
            binLookupRequest = new BINLookupRequest(binNumber);
        }

        /**
         * Build bin lookup request.
         *
         * @return the bin lookup request
         */
        public BINLookupRequest build() {
            return binLookupRequest;
        }
    }
}
