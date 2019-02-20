# goSell Android SDK
Android SDK to use [goSell API][1].

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://tap-payments.github.io/goSellSDK-Android/)
[![Build Status](https://img.shields.io/badge/build-passing-success.svg?branch=development_2.0)](https://travis-ci.org/Tap-Payments/goSellSDK-Android/builds/494970284)
[![Documentation](https://img.shields.io/badge/documentation-100%25-bright%20green.svg)](https://tap-payments.github.io/goSellSDK-Android/)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-16-blue.svg)](https://stuff.mit.edu/afs/sipb/project/android/docs/reference/packages.html)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-28-informational.svg)](https://stuff.mit.edu/afs/sipb/project/android/docs/reference/packages.html)

A library that fully covers payment/authorization/card saving process inside your Android application

# Table of Contents
---

1. [Requirements](#requirements)
2. [Installation](#installation)
   1. [Include goSellSDK library as a dependency module in your project](#include_library_to_code_locally)
   2. [Installation with jitpack](#installation_with_jitpack)
3. [Setup](#setup)
    1. [goSellSDK Class Properties](#setup_gosellsdk_class_properties)
4. [Usage](#usage)
    1. [Pay Button](#paybutton)
       1. [Start SDK with Pay Button](#start_sdk_with_pay_button)
       2. [Init Pay Button](#pay_button_init)
       3. [Appearance](#pay_button_appearance)
    2. [API SDKTrigger](#api_sdkTrigger)
    	 1. [Properties](#api_sdkTrigger_properties)
    	 2. [Methods](#api_sdkTrigger_methods)
    3. [SDKTrigger Data Source](#sdkTrigger_data_source)
        1. [Structure](#sdkTrigger_data_source_structure)
        2. [Samples](#sdkTrigger_data_source_samples)
    4. [SDK Delegate](#sdk_delegate)
        1. [Charge Result Enum](#charge_result)
        2. [Get Payment result](#getPaymentResult)
    5. [Documentation](#docs)


<a name="requirements"></a>
# Requirements
---
To use the SDK the following requirements must be met:

1. **Android Studio 3.3** or newer
2. **Android SDK Tools 26.1.1** or newer
3. **Android Platform Version: API 28: Android 9.0 (Pie) revision 6** or later

<a name="installation"></a>
# Installation
---
<a name="include_library_to_code_locally"></a>
### Include goSellSDK library as a dependency module in your project
---
1. Clone goSellSDK library from Tap repository
   ```
       git@github.com:Tap-Payments/goSellSDK-Android.git
    ```
2. Add goSellSDK library to your project settings.gradle file as following
    ```Android
        include ':library', ':YourAppName'
    ```
3. Setup your project to include goSellSDK as a dependency Module.
   1. File -> Project Structure -> Modules -> << your project name >>
   2. Dependencies -> click on **+** icon in the screen bottom -> add Module Dependency
   3. select goSellSDK library

<a name="installation_with_jitpack"></a>
### Installation with JitPack
---
[JitPack](https://jitpack.io/) is a novel package repository for JVM and Android projects. It builds Git projects on demand and provides you with ready-to-use artifacts (jar, aar).

To integrate goSellSDK into your project add it in your **root** `build.gradle` at the end of repositories:
```android
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```android
	dependencies {
	        compile 'com.github.Tap-Payments:goSellSDK-Android:1.0.2'
	}
```
<a name="setup"></a>
# Setup
---
First of all, `goSellSDK` should be set up. In this section only secret key is required.

<a name="setup_gosellsdk_class_properties"></a>
## goSellSDK Class Properties
First of all, `goSellSDK` should be set up. To set it up, add the following lines of code somewhere in your project and make sure they will be called before any usage of `goSellSDK`.

Below is the list of properties in goSellSDK class you can manipulate. Make sure you do the setup before any usage of the SDK.

<a name="setup_gosellsdk_class_properties_secret_key"></a>
### Secret Key

To set it up, add the following line of code somewhere in your project and make sure it will be called before any usage of `goSellSDK`, otherwise an exception will be thrown. **Required**.

Android
```java
    GoSellSDK.init(context, "sk_XXXXXXXXXXXXXXXXXXXXXXXX"); // Authentication key
```
1. **`authToken`** - to authorize your requests.// Secret key (format: "sk_XXXXXXXXXXXXXXXXXXXXXXXX")

Don't forget to import the class at the beginning of the file:

*Android*:

```android
 import company.tap.gosellapi.GoSellSDK;
```

<a name="usage"></a>
#Usage
---
<a name="paybutton"></a>
### Pay Button
---
<a name="start_sdk_with_pay_button"></a>
**Pay Button** is an instance of android button view customized by tap. Pay button look and feel is totally customized.

Pay Button is restricted to the height of exactly **40 dp**. For better experience, make sure that it has enough **width** to display the content.

<a name="start_sdk_with_pay_button"></a>
If you would like to include it do the following:

1. include PayButton view into your layout xml
```android
   <company.tap.gosellapi.open.buttons.PayButtonView
        android:id="@+id/payButtonId"
        android:layout_width="match_parent"
        android:layout_height="40dp"
        android:layout_alignParentBottom="true"/>
```

<a name="pay_button_init"></a>
2. get reference inside your activity
```android
    payButtonView = findViewById(R.id.payButtonId);
 ```

<a name="pay_button_appearance"></a>
## Appearance
 To customize pay button appearance you must instantiate an object of SDKTrigger and then use it to do the customization as following:

 1. Instantiate an instance of SDKTrigger
 ```android
      sdkTrigger = new SDKTrigger();
 ```
 2. set button view
 ```android
     sdkTrigger.setButtonView(payButtonView, this, SDK_REQUEST_CODE);
 ```
 3. setup button view background
     1. setup pay button using xml selector
     ```android
         sdkTrigger.setPayButtonBackgroundSelector(YOUR_XML_SELECTOR);
     ```
     2. setup pay button using color list
     ```android
         sdkTrigger.setupBackgroundWithColorList(ENABLED_COLOR_CODE,DISABLED_COLOR_CODE);
     ```
  4. setup pay button font type face
  ```android
         sdkTrigger.setupPayButtonFontTypeFace(FONT_FACE);
  ```
  5. set pay button text color
  ```android
         sdkTrigger.setupTextColor(ENABLED_TEXT_COLOR, DISABLED_TEXT_COLOR);
  ```
<a name="api_sdkTrigger"></a>
## SDKTrigger
**SDKTrigger** is the main interface for goSellSDK library from you application, so you can use it to start SDK with pay button or without pay button.

<a name="api_sdkTrigger_properties"></a>
### Properties

<table style="text-align:center">
    <th colspan=1>Property</th>
    <th colspan=1>Type</th>
    <th rowspan=1>Description</th>

   <tr>
	<td> payButtonView  </td>
	<td> PayButtonView </td>
	<td> Pay Button View can be used to start SDK </td>
   <tr>

   <tr>
	<td> paymentDataSource  </td>
	<td> PaymentDataSource </td>
	<td> Payment data source. All input payment information is passed through this protocol. Required. </td>
   <tr>
  <tr>
	<td> activityListener  </td>
	<td> Activity </td>
	<td> Activity. used as a context to setup sdk. also, it is used to notify Merchant application when goSellSDK finish's its work . </td>
   <tr>



</table>

<a name="api_sdkTrigger_methods"></a>
### Methods

<table style="text-align:center">
    <th colspan=1>Method</th>
    <th rowspan=1>Description</th>

   <tr>
	<td> setButtonView  </td>
	<td> Set pay button instance. Pay Button can be used to start the payment process </td>
   </tr>

   <tr>
	<td> setPayButtonBackgroundSelector  </td>
	<td> Setup pay button background using selector xml file. </td>
   </tr>
  <tr>
	<td> setupBackgroundWithColorList  </td>
	<td> Setup pay button background using two colors. You need to provide method with color to be applied in case of button is enabled and also color to be applied in case of button is disabled. </td>
   </tr>
   <tr>
	<td> setupPayButtonFontTypeFace </td>
	<td> Setup pay button font type face. You need to passs font type face to this method </td>
   </tr>
   <tr>
	<td> setupTextColor </td>
	<td> Setup pay button text color. You need to passs color code to be applied in case of pay button is enabled and int color to be applied in case of pay button is disabled</td>
   </tr>
<tr>
	<td> persistPaymentDataSource </td>
	<td> Setup pay button title. You can setup paay button text according to payment type [Payment - Authorize - Save Cards].</td>
   </tr>

</table>

<a name="sdkTrigger_data_source"></a>
### SDKTrigger Payment Data Source

**PaymentDataSource** is an interface which you should implement somewhere in your code to pass payment information in order to be able to access payment flow within the SDK.

<a name="session_data_source_structure"></a>
### Structure

The following table describes its structure and specifies which fields are required for each of the modes.

<table style="text-align:center">
    <th rowspan=2>Member</th>
    <th colspan=1>Type</th>
    <th colspan=3>Required</th>
    <th rowspan=2>Description</th>
    <tr>
        <th><sub>Android</sub></th>
        <th><sub>Purchase</sub></th>
        <th><sub>Authorize</sub></th>
        <th><sub>Card Saving</sub></th>
    </tr>
    <tr>
        <td><sub><i>mode</i></sub></td>
        <td colspan=1><sub><b>TransactionMode</b></sub></td>
        <td colspan=3><sub><i>false</i></sub></td>
        <td align="left"><sub>Mode of the transactions (purchase or authorize). If this    property is not implemented, <i>purchase</i> mode is used.</sub></td>
    </tr>
     <tr>
        <td><sub><i>customer</i></sub></td>
        <td colspan=1><sub><b>Customer</b></sub></td>
        <td colspan=3><sub><i>true</i></sub></td>
        <td align="left"><sub>Customer information. For more details on how to create the customer, please refer to <i>Customer</i> class reference.</sub></td>
    </tr>
     <tr>
        <td><sub><i>currency</i></sub></td>
        <td colspan=1><sub><b>Currency</b></sub></td>
        <td colspan=2><sub><i>true</i></sub></td><td><sub><i>false</i></sub></td>
        <td align="left"><sub>Currency of the transaction.</sub></td>
    </tr>
    <tr>
        <td><sub><i>amount</i></sub></td>
        <td colspan=1><sub><b><nobr>BigDecimal</nobr></b></sub></td>
        <td colspan=3><sub><i>false</i></sub></td>
        <td align="left"><sub>Payment/Authorization amount.<br><b>Note:</b> In order to have payment amount either <i>amount</i> or <i>items</i> should be implemented. If both are implemented, <i>items</i> is preferred.</sub></td>
    </tr>
    <tr>
    <td><sub><i>items</i></sub></td>
    <td colspan=1><sub><b>ArrayList <nobr>[PaymentItem]</PaymentItem> </nobr></b></sub></td>
    <td colspan=3><sub><i>false</i></sub></td>
    <td align="left"><sub>List of items to pay for.<br><b>Note:</b> In order to have payment amount either <i>amount</i> or <i>items</i> should be implemented. If both are implemented, <i>items</i> is preferred.</sub></td>
</tr>
<tr>
    <td><sub><i>taxes</i></sub></td>
    <td colspan=1><sub><b>ArrayList <nobr>[Tax]</PaymentItem> </nobr></b></sub></td>
    <td colspan=3><sub><i>false</i></sub></td>
    <td align="left"><sub>You can specify taxation details here. By default, there are no taxes.<br> <b>Note:</b> Specifying taxes will affect total payment/authorization amount.</sub></td>
</tr>
<tr>
    <td><sub><i>shipping</i></sub></td>
    <td colspan=1><sub><b>ArrayList <nobr>[Shipping]</PaymentItem> </nobr></b></sub></td>
    <td colspan=3><sub><i>false</i></sub></td>
    <td align="left"><sub>You can specify shipping details here. By default, there are no shipping details.<br> <b>Note:</b> Specifying shipping will affect total payment/authorization amount.</sub></td>
</tr>
<tr>
    <td><sub><i>postURL</i></sub></td>
    <td colspan=1><sub><b>String </b></sub></td>
    <td colspan=3><sub><i>false</i></sub></td>
    <td align="left"><sub>The URL which will be called by Tap system notifying that payment has either succeed or failed</sub></td>
</tr>
<tr>
    <td><sub><i>paymentDescription</i></sub></td>
    <td colspan=1><sub><b>String </b></sub></td>
    <td colspan=3><sub><i>false</i></sub></td>
    <td align="left"><sub>Description of the payment.</sub></td>
</tr>
<tr>
    <td><sub><i>paymentMetadata</i></sub></td>
    <td colspan=1><sub><b>String </b></sub></td>
    <td colspan=3><sub><i>false</i></sub></td>
    <td align="left"><sub>Additional information you would like to pass along with the transaction.</sub></td>
</tr>
<tr>
    <td><sub><i>paymentReference</i></sub></td>
    <td colspan=1><sub><b>Reference </b></sub></td>
    <td colspan=3><sub><i>false</i></sub></td>
    <td align="left"><sub>You can keep a reference to the transaction using this property</sub></td>
</tr>
<tr>
    <td><sub><i>paymentStatementDescriptor</i></sub></td>
    <td colspan=1><sub><b>String </b></sub></td>
    <td colspan=3><sub><i>false</i></sub></td>
    <td align="left"><sub>Statement descriptor.</sub></td>
</tr>
<tr>
    <td><sub><i>require3DSecure</i></sub></td>
    <td colspan=1><sub><b>Boolean </b></sub></td>
    <td colspan=3><sub><i>false</i></sub></td>
    <td align="left"><sub>Defines if 3D secure check is required. If not implemented, treated as <i>true</i>.<br><b>Note:</b> If you disable 3D secure check, it still may occure. Final decision is taken by Tap</sub></td>
</tr>
<tr>
    <td><sub><i>receiptSettings</i></sub></td>
    <td colspan=1><sub><b>Receipt </b></sub></td>
    <td colspan=3><sub><i>false</i></sub></td>
    <td align="left"><sub>Receipt recipient details.</sub></td>
</tr>
<tr>
    <td><sub><i>authorizeAction</i></sub></td>
    <td colspan=1><sub><b>AuthorizeAction </b></sub></td>
    <td><sub><i>false</i></sub></td><td><sub><i>true</i></sub></td><td><sub><i>false</i></sub></td>
    <td align="left"><sub>Action to perform after authorization succeeds.</sub></td>
</tr>
<tr>
    <td><sub><i>allowsToSaveSameCardMoreThanOnce</i></sub></td>
    <td colspan=1><sub><b>Boolean </b></sub></td>
    <td colspan=3><sub><i>false</i></sub></td>
    <td align="left"><sub>Defines if same card can be saved more than once.<br><b>Note:</b> Same cards means absolutely equal data set. For example, if customer specifies same card details, but different cardholder names, we will treat this like different cards.</sub></td>
</tr>
</table>

<a name="sdkTrigger_data_source_samples"></a>
## Samples
 SDKTrigger class supports you with all methods needed to setup PaymentDataSource object as following:

 1. Instantiate an instance of PaymentDataSource
 ```java
      sdkTrigger.instantiatePaymentDataSource();
 ```
 2. Set TransactionCurrency
 ```java
      sdkTrigger.setTransactionCurrency(TapCurrency);
 ```
 **Transaction Currency** model class:
 ```java
  public TapCurrency(@NonNull String isoCode) throws CurrencyException {
    String code = isoCode.toLowerCase();
    if(!LocaleCurrencies.checkUserCurrency(code)) {
      throw CurrencyException.getUnknown(code);
    }
    this.isoCode = code;
  }
 ```
  3. Set TransactionMode
     ```java
          sdkTrigger.setTransactionMode(TransactionMode); // PURCHASE,AUTHORIZE_CAPTURE, SAVE_CARD
     ```

**Transaction Mode** is an enum:
```java
    public enum TransactionMode {

        @SerializedName("PURCHASE")             PURCHASE,
        @SerializedName("AUTHORIZE_CAPTURE")    AUTHORIZE_CAPTURE,
        @SerializedName("SAVE_CARD")            SAVE_CARD
        }
```
   4. Set Customer
 ```android
      sdkTrigger.setCustomer(Customer);
 ```

 **Customer** model class:
```java
     class Customer {

                  @SerializedName("id")
                  @Expose
                  private String identifier;

                  @SerializedName("first_name")
                  @Expose
                  private String firstName;

                  @SerializedName("middle_name")
                  @Expose
                  private String middleName;

                  @SerializedName("last_name")
                  @Expose
                  private String lastName;

                  @SerializedName("email")
                  @Expose
                  private String email;

                  @SerializedName("phone")
                  @Expose
                  private PhoneNumber phone;

                  @SerializedName("metadata")
                  String metaData;
          }
```
    5. Set Payment Items
 ```java
      sdkTrigger.setPaymentItems(ArraList<Item>);
 ```

 **Item** model class:
```java
	class PaymentItem {

          @SerializedName("name")
          @Expose
          private String name;


          @SerializedName("description")
          @Expose
          @Nullable private String description;

          @SerializedName("quantity")
          @Expose
          private Quantity quantity;

          @SerializedName("amount_per_unit")
          @Expose
          private BigDecimal amountPerUnit;

          @SerializedName("discount")
          @Expose
          @Nullable private AmountModificator discount;

          @SerializedName("taxes")
          @Expose
          @Nullable private ArrayList<Tax> taxes;

          @SerializedName("total_amount")
          @Expose
          private BigDecimal totalAmount;
	}
```

 5. Set Taxes
 ```android
      sdkTrigger.setTaxes(ArraList<Tax>);
 ```
  **Tax** model class:
```java
	class class Tax {

            @SerializedName("name")
            @Expose
            private String name;

            @SerializedName("description")
            @Expose
            @Nullable private String description;

            @SerializedName("amount")
            @Expose
            private AmountModificator amount;
    }
```
  6. Set Shipping
 ```android
      sdkTrigger.setShipping(ArraList<Shipping>);
 ```
 **Tax** is a model class:
```java
	class class Shipping {

            @SerializedName("name")
            @Expose
            private String name;

            @SerializedName("description")
            @Expose
            @Nullable private String description;

            @SerializedName("amount")
            @Expose
            private BigDecimal amount;
    }
```
   7. Set Post URL
 ```android
      sdkTrigger.setPostURL(String);
 ```
 **Post URL**:
```java
   return  "https://tap.company";
```
 8. Set Payment Description
 ```android
      sdkTrigger.setPaymentDescription(String);
 ```
  **Description**:
```java
    return @"Awesome payment description will be here.";
```
 9. Set Payment Metadata
 ```android
      sdkTrigger.setPaymentMetadata(HashMap<String,String>);
 ```
 **Metadata** HashMap:
```java
    HashMap<String,String> paymentMetadata = new HashMap<>();
        paymentMetadata.put("metadata_key_1", "metadata value 1");
        return paymentMetadata;
```
  10. Set Payment Reference
 ```android
      sdkTrigger.setPaymentReference(Reference);
 ```
  **Reference** model class:
```java
	class class Reference {

            @SerializedName("acquirer")
            @Expose
            @Nullable private String acquirer;

            @SerializedName("gateway")
            @Expose
            @Nullable private String gateway;

            @SerializedName("payment")
            @Expose
            @Nullable private String payment;

            @SerializedName("track")
            @Expose
            @Nullable private String track;

            @SerializedName("transaction")
            @Expose
            @Nullable private String transaction;

            @SerializedName("order")
            @Expose
            @Nullable private String order;
    }
```
   11. Set Payment Descriptor
 ```android
      sdkTrigger.setPaymentStatementDescriptor(String);
 ```
   **Descriptor** String:
```java
    return @"Payment statement descriptor will be here.";
```
   12. Set 3DSecure
 ```android
      sdkTrigger.isRequires3DSecure(boolean);
 ```
    **3D Secure** Boolean:
```java
    return true;
```
   13. Set Receipt Settings
 ```android
      sdkTrigger.setReceiptSettings(Receipt);
 ```
   **Receipt** model class:
```java
	class class Receipt {

            @SerializedName("id")
            @Expose
            @Nullable private String id;

            @SerializedName("email")
            @Expose
            private boolean email;

            @SerializedName("sms")
            @Expose
            private boolean sms;
    }
```
    14. Set Authorize Action
 ```android
      sdkTrigger.setAuthorizeAction(AuthorizeAction);
 ```
    **AuthorizeAction** model class:
```java
	class AuthorizeAction {

            @SerializedName("type")
            @Expose
            private AuthorizeActionType type;

            @SerializedName("time")
            @Expose
            private int timeInHours;
    }
```

<a name="PaymentProcessDelegate"></a>
## Payment Process Delegate

**PaymentProcessDelegate** is a class which you may use to get payment/authorization/card saving status updates and update your user interface accordingly when payment window closes.

<a name="charge_result"></a>
## Charge Result
```java
    @SerializedName("INITIATED")    INITIATED,
    @SerializedName("IN_PROGRESS")  IN_PROGRESS,
    @SerializedName("ABANDONED")    ABANDONED,
    @SerializedName("CANCELLED")    CANCELLED,
    @SerializedName("FAILED")       FAILED,
    @SerializedName("DECLINED")     DECLINED,
    @SerializedName("RESTRICTED")   RESTRICTED,
    @SerializedName("CAPTURED")     CAPTURED,
    @SerializedName("VOID")         VOID,
    @SerializedName("UNKNOWN")      UNKNOWN,
    @SerializedName("AUTHORIZED")   AUTHORIZED,
    @SerializedName("TIMEDOUT")     TIMEDOUT,

    // save card status result
    @SerializedName("VALID")        VALID,
    @SerializedName("INVALID")      INVALID,
```

Below are listed down all available methods:

<a name="getPaymentResult"></a>
### Get Payment Result

Retreive the payment result.
```java
 Charge chargeOrAuthorizeResult = PaymentProcessDelegate.getInstance().getPaymentResult();
                if (chargeOrAuthorizeResult != null) {
                    switch (chargeOrAuthorizeResult.getStatus()) {
                        case CAPTURED:
			    // write you code
			    break;
                        case AUTHORIZED:
			// write you code
			    break;
                        case VALID:
                            // write you code
			    break;
                        case FAILED:
                            // write you code
			    break;
                        case INVALID:
                           // write you code
			    break;
                        case DECLINED:
                           // write you code
			    break;
                        case UNKNOWN:
                           // write you code
			    break;
                        case TIMEDOUT:
                           // write you code
			    break;
                    }

                }
```

-----
<a name="docs"></a>
# Documentation
Documentation is available at [github-pages][2].<br>
Also documented sources are attached to the library.

[1]:https://www.tap.company/developers/
[2]:https://tap-payments.github.io/goSellSDK-Android/