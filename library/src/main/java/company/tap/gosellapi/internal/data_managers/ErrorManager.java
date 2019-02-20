package company.tap.gosellapi.internal.data_managers;

/**
 * The type Error manager.
 */
public class ErrorManager {

    private ErrorManager() {

    }

    private static class SingletonHolder {
        private static final ErrorManager INSTANCE = new ErrorManager();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ErrorManager getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
