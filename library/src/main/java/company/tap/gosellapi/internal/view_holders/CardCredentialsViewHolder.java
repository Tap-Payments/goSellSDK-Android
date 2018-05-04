package company.tap.gosellapi.internal.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.MainRecyclerViewAdapter;

public class CardCredentialsViewHolder extends RecyclerView.ViewHolder {

    private MainRecyclerViewAdapter.MainRecyclerViewAdapterListener mListener;

    public CardCredentialsViewHolder(View view, MainRecyclerViewAdapter.MainRecyclerViewAdapterListener listener) {
        super(view);

        mListener = listener;
    }

    public void bind() {

        // Configure Card Scanner Button
        Button cardScannerButton = itemView.findViewById(R.id.cardScannerButton);

        cardScannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.cardScannerButtonClicked();
            }
        });

        // Configure Save Card Switch
        CheckBox saveCardCheck = itemView.findViewById(R.id.saveCardCheck);

        saveCardCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mListener.saveCardSwitchCheckedChanged();
            }
        });
    }
}