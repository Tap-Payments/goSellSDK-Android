package company.tap.gosellapi.internal.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import company.tap.gosellapi.R;

public class CardCredentialsViewHolder extends RecyclerView.ViewHolder {

    public CardCredentialsViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_card_credentials, parent, false));
    }
}