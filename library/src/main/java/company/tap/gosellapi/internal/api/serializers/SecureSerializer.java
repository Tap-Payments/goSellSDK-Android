package company.tap.gosellapi.internal.api.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import company.tap.gosellapi.internal.api.crypto.CryptoUtil;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.interfaces.SecureSerializable;

public class SecureSerializer implements JsonSerializer<SecureSerializable> {

    @Override
    public JsonElement serialize(SecureSerializable src, Type typeOfSrc, JsonSerializationContext context) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(src, typeOfSrc);

        String encryptedString = CryptoUtil.encryptJsonString(jsonString,getEncryptionKey());

        return new JsonPrimitive(encryptedString);
    }

    private String getEncryptionKey() {

        return PaymentDataManager.getInstance().getSDKSettings().getData().getEncryptionKey();
    }
}
