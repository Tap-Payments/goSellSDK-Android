package company.tap.gosellapi.internal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.models.PaymentOption;

public class CardSystemsRecyclerViewAdapter extends RecyclerView.Adapter<CardSystemsRecyclerViewAdapter.CardSystemViewHolder> {

    private ArrayList<PaymentOption> data;

    public CardSystemsRecyclerViewAdapter(ArrayList<PaymentOption> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CardSystemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_card_system, parent, false);
        return new CardSystemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardSystemViewHolder holder, int position) {
        PaymentOption option = data.get(position);
        Glide.with(holder.itemView.getContext()).load(option.getImage()).into(holder.cardSystemIcon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

     class CardSystemViewHolder extends RecyclerView.ViewHolder {
        ImageView cardSystemIcon;

        CardSystemViewHolder(View itemView) {
            super(itemView);

            cardSystemIcon = itemView.findViewById(R.id.cardSystemIcon);
        }
    }
}
