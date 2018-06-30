package company.tap.gosellapi.internal.data_managers;

public class ErrorManager {

    private ErrorManager() {

    }

    private static class SingletonHolder {
        private static final ErrorManager INSTANCE = new ErrorManager();
    }

    public static ErrorManager getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
