package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.Constants;
import company.tap.gosellapi.internal.api.models.Card;

public class RecentPaymentsRecyclerViewAdapter extends RecyclerView.Adapter<RecentPaymentsRecyclerViewAdapter.RecentPaymentsViewHolder> {
    public interface RecentPaymentsRecyclerViewAdapterListener {
        void recentPaymentItemClicked(int position, Card card);
    }

    private ArrayList<Card> datasource;
    private RecyclerView parent;

    private RecentPaymentsRecyclerViewAdapterListener listener;
    private int focusedPosition = Constants.NO_FOCUS;

    public RecentPaymentsRecyclerViewAdapter(ArrayList<Card> datasource, RecentPaymentsRecyclerViewAdapterListener listener) {
        this.datasource = datasource;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecentPaymentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gosellapi_viewholder_recent_payments, parent, false);
        return new RecentPaymentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentPaymentsViewHolder holder, int position) {
        Card card = datasource.get(position);
        holder.bind(position, card);
        holder.setFocused(position == focusedPosition);
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }

    public void setFocused(boolean focused) {
        setFocused(focused ? focusedPosition : Constants.NO_FOCUS);
    }

    private void setFocused(int position) {
        RecentPaymentsViewHolder oldHolder;

        if (focusedPosition != Constants.NO_FOCUS) {
            oldHolder = (RecentPaymentsViewHolder) parent.findViewHolderForAdapterPosition(focusedPosition);
            if (oldHolder != null) {
                oldHolder.setFocused(false);
            }
        }

        focusedPosition = position;
        RecentPaymentsViewHolder newHolder = (RecentPaymentsViewHolder) parent.findViewHolderForAdapterPosition(focusedPosition);
        if (newHolder != null) {
            newHolder.setFocused(true);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        parent = null;
    }

    public class RecentPaymentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int position;
        private Card card;
        private ImageView itemCheckmark;

        private RecentPaymentsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        private void bind(int position, Card card) {
            this.position = position;
            this.card = card;

            String cardNumber = String.format(itemView.getResources().getString(R.string.textview_placeholder_last_four_digits), card.getLast4());
            TextView cardLastDigits = itemView.findViewById(R.id.cardLastDigits);
            cardLastDigits.setText(cardNumber);

            itemCheckmark = itemView.findViewById(R.id.itemCheckmark);
        }

        @Override
        public void onClick(View v) {
            RecentPaymentsRecyclerViewAdapter.this.setFocused(position);
//            listener.recentPaymentItemClicked(position, card);
        }

        private void setFocused(boolean focused) {
            itemCheckmark.setVisibility(focused ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
