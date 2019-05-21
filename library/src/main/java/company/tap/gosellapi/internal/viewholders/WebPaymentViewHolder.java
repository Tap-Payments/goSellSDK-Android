package company.tap.gosellapi.internal.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;

/**
 * The type Web payment view holder.
 */
public class WebPaymentViewHolder
        extends PaymentOptionsBaseViewHolder<PaymentOption, WebPaymentViewHolder, WebPaymentViewModel> {
    private ImageView paymentSystemIcon;
    private TextView paymentSystemName;
    private ImageView arrowIcon;

    /**
     * Instantiates a new Web payment view holder.
     *
     * @param itemView the item view
     */
    WebPaymentViewHolder(final View itemView) {
        super(itemView);
        paymentSystemIcon = itemView.findViewById(R.id.paymentSystemIcon);
        paymentSystemName = itemView.findViewById(R.id.paymentSystemName);
        arrowIcon = itemView.findViewById(R.id.arrowIcon);
        if (SDK_INT >= JELLY_BEAN_MR1) {
            if (itemView.getContext().getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                arrowIcon.setBackgroundResource(R.drawable.ic_arrow_left_normal);
            }
        }

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
