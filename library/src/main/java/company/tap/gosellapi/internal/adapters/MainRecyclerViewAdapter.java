package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.view_holders.CardCredentialsViewHolder;
import company.tap.gosellapi.internal.view_holders.PaymentSystemsViewHolder;
import company.tap.gosellapi.internal.view_holders.RecentSectionViewHolder;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter {

    public interface MainRecyclerViewAdapterListener {

        void cardScannerButtonClicked();
        void saveCardSwitchCheckedChanged();
        void paymentSystemViewHolderClicked();
        void recentPaymentItemClicked();
    }

    enum SectionType {
        RECENT(0),
        PAYMENT_SYSTEMS(1),
        CARD_CREDENTIALS(2);

        private int value;
        private static SparseArray<SectionType> sectionsMap = new SparseArray<>();

        SectionType(int value) {
            this.value = value;
        }

        static {
            for (SectionType pageType : SectionType.values()) {
                sectionsMap.put(pageType.value, pageType);
            }
        }

        public static SectionType valueOf(int pageType) {
            return sectionsMap.get(pageType);
        }

        public int getValue() {
            return value;
        }
    }

    private ArrayList<SectionType> temporarySectionsModel = new ArrayList<SectionType>() {
        {
            add(SectionType.RECENT);
            add(SectionType.PAYMENT_SYSTEMS);
            add(SectionType.CARD_CREDENTIALS);
        }
    };

    private MainRecyclerViewAdapterListener listener;

    public MainRecyclerViewAdapter(MainRecyclerViewAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SectionType type = SectionType.valueOf(viewType);
        return getViewHolderForType(type, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        SectionType type = SectionType.valueOf(position);

        switch (type) {

            case RECENT:
                ((RecentSectionViewHolder) holder).bind();
                break;

            case PAYMENT_SYSTEMS:
                ((PaymentSystemsViewHolder) holder).bind();
                break;

            case CARD_CREDENTIALS:
                ((CardCredentialsViewHolder) holder).bind();
                break;

            default:

                 break;
        }
    }

    @Override
    public int getItemCount() {
        return temporarySectionsModel.size();
    }

    @Override
    public int getItemViewType(int position) {
        SectionType type = temporarySectionsModel.get(position);
        return type.getValue();
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolderForType(SectionType type,  ViewGroup parent) {

        switch (type) {

            case RECENT:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_recent_section, parent, false);
                return new RecentSectionViewHolder(view, this.listener);

            case PAYMENT_SYSTEMS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_payment_systems, parent, false);
                return new PaymentSystemsViewHolder(view, this.listener);

            case CARD_CREDENTIALS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_card_credentials, parent, false);
                return new CardCredentialsViewHolder(view, this.listener);

            default:
                view = new View(parent.getContext());
                return new BlankViewHolder(view);
        }
    }

    private class BlankViewHolder extends RecyclerView.ViewHolder {

        BlankViewHolder(View itemView) {
            super(itemView);
        }
    }
}
