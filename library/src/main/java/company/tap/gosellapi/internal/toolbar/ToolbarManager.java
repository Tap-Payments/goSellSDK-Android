package company.tap.gosellapi.internal.toolbar;

/**
 * The type Toolbar manager.
 */
public class ToolbarManager {
    private static final ToolbarManager ourInstance = new ToolbarManager();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ToolbarManager getInstance() {
        return ourInstance;
    }

    private ToolbarManager() {
    }

}
