package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import company.tap.gosellapi.internal.view_holders.CardCredentialsViewHolder;
import company.tap.gosellapi.internal.view_holders.KNETSectionViewHolder;
import company.tap.gosellapi.internal.view_holders.MainHeaderViewHolder;
import company.tap.gosellapi.internal.view_holders.RecentSectionViewHolder;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter {

    enum SectionType {
        HEADER(0),
        RECENT(1),
        KNET(2),
        CARD_CREDENTIALS(3);

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
            add(SectionType.HEADER);
            add(SectionType.RECENT);
            add(SectionType.KNET);
            add(SectionType.CARD_CREDENTIALS);
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SectionType type = SectionType.valueOf(viewType);
        return getViewHolderForType(type, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

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

            case HEADER:
                return new MainHeaderViewHolder(parent);

            case RECENT:
                return new RecentSectionViewHolder(parent);

            case KNET:
                return new KNETSectionViewHolder(parent);

            case CARD_CREDENTIALS:
                return new CardCredentialsViewHolder(parent);

            default:
                View view = new View(parent.getContext());
                return new BlankViewHolder(view);
        }
    }

    private class BlankViewHolder extends RecyclerView.ViewHolder {

        BlankViewHolder(View itemView) {
            super(itemView);
        }
    }
}
