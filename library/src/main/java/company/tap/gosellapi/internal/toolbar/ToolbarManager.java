package company.tap.gosellapi.internal.toolbar;

public class ToolbarManager {
    private static final ToolbarManager ourInstance = new ToolbarManager();

    public static ToolbarManager getInstance() {
        return ourInstance;
    }

    private ToolbarManager() {
    }

}
