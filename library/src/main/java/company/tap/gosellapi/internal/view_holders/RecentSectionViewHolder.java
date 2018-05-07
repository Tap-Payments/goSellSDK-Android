package company.tap.gosellapi.internal.view_holders;

import android.view.View;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.Card;
import company.tap.gosellapi.internal.data_source.payment_options.PaymentOptionsBaseModel;

public class RecentSectionViewHolder extends PaymentOptionsBaseViewHolder<PaymentOptionsBaseModel<ArrayList<Card>>> {
    RecentSectionViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(PaymentOptionsBaseModel<ArrayList<Card>> data) {

    }
}
