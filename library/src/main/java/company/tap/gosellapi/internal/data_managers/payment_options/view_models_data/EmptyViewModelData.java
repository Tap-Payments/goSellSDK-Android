package company.tap.gosellapi.internal.data_managers.payment_options.view_models_data;

public class EmptyViewModelData {

    private int contentHeight;
    private String identifier;

    public int getContentHeight() {
        return contentHeight;
    }

    public String getIdentifier() {
        return identifier;
    }

    public EmptyViewModelData(String identifier) {

        this(24, identifier);
    }

    public EmptyViewModelData(int contentHeight, String identifier) {

        this.contentHeight = contentHeight;
        this.identifier = identifier;
    }
}
