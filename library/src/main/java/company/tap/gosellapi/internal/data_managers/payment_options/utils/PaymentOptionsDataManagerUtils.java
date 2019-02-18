package company.tap.gosellapi.internal.data_managers.payment_options.utils;

import company.tap.gosellapi.internal.api.models.PaymentOption;
import company.tap.gosellapi.internal.data_managers.payment_options.PaymentOptionsDataManager;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.EmptyViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models.WebPaymentViewModel;
import company.tap.gosellapi.internal.data_managers.payment_options.view_models_data.EmptyViewModelData;

public abstract class PaymentOptionsDataManagerUtils {

  public static class ViewModelUtils{



    public static EmptyViewModel generateEmptyModel(String identifier, PaymentOptionsDataManager parent) {
      EmptyViewModelData modelData = new EmptyViewModelData(identifier);
      return new EmptyViewModel(parent, modelData);
    }


    public static  WebPaymentViewModel generateWebPaymentModel(PaymentOption paymentOption, PaymentOptionsDataManager parent) {
      return new WebPaymentViewModel(parent, paymentOption);
    }





  }
}
