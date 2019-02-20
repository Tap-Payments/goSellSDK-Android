package company.tap.gosellapi.internal.data_managers.payment_options.view_models_data;

/**
 * The type Empty view model data.
 */
public class EmptyViewModelData {

    private int contentHeight;
    private String identifier;

    /**
     * Gets content height.
     *
     * @return the content height
     */
    public int getContentHeight() {
        return contentHeight;
    }

    /**
     * Gets identifier.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Instantiates a new Empty view model data.
     *
     * @param identifier the identifier
     */
    public EmptyViewModelData(String identifier) {

        this(24, identifier);
    }

    /**
     * Instantiates a new Empty view model data.
     *
     * @param contentHeight the content height
     * @param identifier    the identifier
     */
    public EmptyViewModelData(int contentHeight, String identifier) {

        this.contentHeight = contentHeight;
        this.identifier = identifier;
    }
}
