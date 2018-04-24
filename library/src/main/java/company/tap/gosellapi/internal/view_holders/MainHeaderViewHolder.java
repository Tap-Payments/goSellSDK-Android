package company.tap.gosellapi.internal.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import company.tap.gosellapi.R;

public class MainHeaderViewHolder extends RecyclerView.ViewHolder {

    public MainHeaderViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_main_header, parent, false));
    }
}
