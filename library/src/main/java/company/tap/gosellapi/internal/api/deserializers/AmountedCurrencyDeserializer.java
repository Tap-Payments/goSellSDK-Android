package company.tap.gosellapi.internal.api.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import company.tap.gosellapi.internal.api.models.AmountedCurrency;
import company.tap.gosellapi.internal.utils.CurrencyFormatter;

/**
 * The type Amounted currency deserializer.
 */
public class AmountedCurrencyDeserializer implements JsonDeserializer<AmountedCurrency> {

    @Override
    public AmountedCurrency deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        AmountedCurrency amountedCurrency = gson.fromJson(json, AmountedCurrency.class);

        if ( amountedCurrency.getSymbol() == null ) {

            String symbol = CurrencyFormatter.getLocalizedCurrencySymbol(amountedCurrency.getCurrency());
            amountedCurrency.setSymbol(symbol);
        }

        return amountedCurrency;
    }
}
