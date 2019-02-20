package company.tap.gosellapi.internal.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;
import company.tap.tapcardvalidator_android.CardBrand;

/**
 * The type Web payment view holder.
 */
public class WebPaymentViewHolder
        extends PaymentOptionsBaseViewHolder<PaymentOption, WebPaymentViewHolder, WebPaymentViewModel> {
    private ImageView paymentSystemIcon;
    private TextView paymentSystemName;

    /**
     * Instantiates a new Web payment view holder.
     *
     * @param itemView the item view
     */
    WebPaymentViewHolder(final View itemView) {
        super(itemView);
        paymentSystemIcon = itemView.findViewById(R.id.paymentSystemIcon);
        paymentSystemName = itemView.findViewById(R.id.paymentSystemName);

    }

    @Override
    public void bind(PaymentOption data) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.itemClicked();
            }
        });

        paymentSystemName.setText(data.getName());
        Glide.with(itemView.getContext()).load(data.getImage()).into(paymentSystemIcon);
    }
}
