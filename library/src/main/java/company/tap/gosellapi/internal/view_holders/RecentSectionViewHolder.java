package company.tap.gosellapi.internal.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import company.tap.gosellapi.R;

public class RecentSectionViewHolder extends RecyclerView.ViewHolder {

    public RecentSectionViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_recent_section, parent, false));
    }

}
