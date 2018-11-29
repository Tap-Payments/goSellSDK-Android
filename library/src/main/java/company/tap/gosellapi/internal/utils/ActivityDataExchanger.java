package company.tap.gosellapi.internal.utils;

import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ActivityDataExchanger {

    public static ActivityDataExchanger getInstance() {

        return SingletonHolder.INSTANCE;
    }

    public @Nullable Object getExtra(Intent intent, String key) {

        Map<String, Object> intentData = getStorage().get(intent);
        if ( intentData == null ) {

            return null;
        }

        return intentData.get(key);
    }

    public void putExtra(@Nullable Object data, String key, Intent intent) {

        Map<String, Object> intentData = getStorage().get(intent);
        if ( intentData == null ) {

            if ( data == null ) { return; }

            intentData = new HashMap<>();
        }

        if ( intentData.containsKey(key) ) {

            if ( data == null ) {

                intentData.remove(key);
            }
            else {

                intentData.put(key, data);
            }
        }
        else {

            if ( data == null ) { return; }

            intentData.put(key, data);
        }

        getStorage().put(intent, intentData);
    }

    public void clearData(Intent intent) {

        getStorage().remove(intent);
    }

    private static class SingletonHolder {

        private static final ActivityDataExchanger INSTANCE = new ActivityDataExchanger();
    }

    private ActivityDataExchanger() {

        storage = new HashMap<>();
    }

    private Map<Intent, Map<String, Object>> storage;

    private Map<Intent, Map<String, Object>> getStorage() {

        return storage;
    }
}
