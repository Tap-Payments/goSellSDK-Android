package company.tap.gosellapi.internal.data_managers.payment_options.utils;

import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.EmptyViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.EmptyViewModelData;

/**
 * The type Payment options data manager utils.
 */
public abstract class PaymentOptionsDataManagerUtils {

    /**
     * The type View model utils.
     */
    public static class ViewModelUtils{


        /**
         * Generate empty model empty view model.
         *
         * @param identifier the identifier
         * @param parent     the parent
         * @return the empty view model
         */
        public static EmptyViewModel generateEmptyModel(String identifier, PaymentOptionsDataManager parent) {
      EmptyViewModelData modelData = new EmptyViewModelData(identifier);
      return new EmptyViewModel(parent, modelData);
    }


        /**
         * Generate web payment model web payment view model.
         *
         * @param paymentOption the payment option
         * @param parent        the parent
         * @return the web payment view model
         */
        public static  WebPaymentViewModel generateWebPaymentModel(PaymentOption paymentOption, PaymentOptionsDataManager parent) {
      return new WebPaymentViewModel(parent, paymentOption);
    }





  }
}
