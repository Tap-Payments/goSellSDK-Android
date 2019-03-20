package company.tap.gosellapi.internal.api.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Collections;

import company.tap.gosellapi.internal.api.responses.PaymentOptionsResponse;


/**
 * The type Payment options response deserializer.
 */
public class PaymentOptionsResponseDeserializer implements JsonDeserializer<PaymentOptionsResponse> {
    @Override
    public PaymentOptionsResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        PaymentOptionsResponse paymentOptionsResponse = gson.fromJson(json, PaymentOptionsResponse.class);

        if (paymentOptionsResponse.getPaymentOptions() != null) {
            Collections.sort(paymentOptionsResponse.getPaymentOptions());
        }

        return paymentOptionsResponse;
    }
}