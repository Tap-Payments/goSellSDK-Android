package company.tap.gosellapi.internal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Iterator;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.api.enums.CardScheme;
import company.tap.gosellapi.internal.api.enums.PaymentType;
import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.PaymentDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.tapcardvalidator_android.CardBrand;


/**
 * The type Card systems recycler view adapter.
 */
public class CardSystemsRecyclerViewAdapter extends RecyclerView.Adapter<CardSystemViewHolder> {

  private ArrayList<PaymentOption> data;
  private ArrayList<PaymentOption> initialData;

    /**
     * Instantiates a new Card systems recycler view adapter.
     *
     * @param _data the data
     */
    public CardSystemsRecyclerViewAdapter(ArrayList<PaymentOption> _data) {
    data = new ArrayList<>(_data);
//    for (PaymentOption option : _data) {
////      System.out.println(" filter based on  currency inside card view holder : " + option
////          .getPaymentType() + " >> " + option.getName());
//    }
    this.initialData = new ArrayList<>(filter(_data));
    filterPaymentOptions();
  }

  private ArrayList<PaymentOption> filter(ArrayList<PaymentOption> _data) {
    ArrayList<PaymentOption> filteredData = new ArrayList<>(_data);
    for (Iterator<PaymentOption> it = filteredData.iterator(); it.hasNext(); ) {
      PaymentOption p = it.next();
      if (p.getPaymentType() != PaymentType.CARD) {
        it.remove();
      } else if (p.getPaymentType() == PaymentType.CARD &&
          !isCardSupportSelectedCurrency(p,
              PaymentDataManager.getInstance().getPaymentOptionsDataManager())
          ) {
        it.remove();
      }
    }
    return filteredData;
  }

  @NonNull
  @Override
  public CardSystemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.gosellapi_viewholder_card_system, parent, false);
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

  private void filterPaymentOptions() {
    for (Iterator<PaymentOption> it = data.iterator(); it.hasNext(); ) {
      PaymentOption p = it.next();
//      System.out.println("payment type : " + p.getName());
      if (p.getPaymentType() != PaymentType.CARD) {
        it.remove();
      } else if (p.getPaymentType() == PaymentType.CARD &&
          !isCardSupportSelectedCurrency(p,
              PaymentDataManager.getInstance().getPaymentOptionsDataManager())
          ) {
        it.remove();
      }
    }
  }

  private boolean isCardSupportSelectedCurrency(PaymentOption paymentOption, @NonNull
      PaymentOptionsDataManager paymentOptionsDataManager) {
    for (String s : paymentOption.getSupportedCurrencies()) {
      if(paymentOptionsDataManager.getSelectedCurrency().getCurrency()!=null)
      if (s.equalsIgnoreCase(paymentOptionsDataManager.getSelectedCurrency().getCurrency())) {
        return true;
      }
    }
    return false;
  }

    /**
     * Update for card brand.
     *
     * @param brand      the brand
     * @param cardScheme the card scheme
     */
    public void updateForCardBrand(CardBrand brand, CardScheme cardScheme) {

    if (brand == null) {
      data = new ArrayList<>(initialData);
      notifyDataSetChanged();
      return;
    }

    data.clear();

    for (PaymentOption option : initialData) {

      ArrayList<CardBrand> cardBrands = option.getSupportedCardBrands();
//      System.out.println("brand :" + brand + " >>> scheme :" + cardScheme);

      if (cardScheme != null) {
        if (comparePaymentOptionWithCardScheme(option, cardScheme)) {
          data.add(option);
          continue;
        }
      } else {
        if (cardBrands.contains(brand)) {
          data.add(option);
        }
      }


    }

    if (data.size() == 0)
      data = new ArrayList<>(initialData);

    notifyDataSetChanged();
  }

  private boolean comparePaymentOptionWithCardScheme(@NonNull PaymentOption paymentOption,
                                                     @NonNull CardScheme cardScheme) {

    if (paymentOption.getName().equalsIgnoreCase(cardScheme.name())) {
      return true;
    }


    if (paymentOption.getBrand().compareTo(cardScheme.getCardBrand()) == 0) {
      return true;
    }
// stop this check to avoid displaying mada with master in case of scheme is not mada
//    if (paymentOption.getSupportedCardBrands().contains(cardScheme.getCardBrand())) {
//      return true;
//    }

    return false;
  }

}


/**
 * The type Card system view holder.
 */
class CardSystemViewHolder extends RecyclerView.ViewHolder {
    /**
     * The Card system icon.
     */
    ImageView cardSystemIcon;

    /**
     * Instantiates a new Card system view holder.
     *
     * @param itemView the item view
     */
    CardSystemViewHolder(View itemView) {
    super(itemView);

    cardSystemIcon = itemView.findViewById(R.id.cardSystemIcon);
  }
}