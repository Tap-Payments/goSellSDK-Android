# goSell Android SDK
Android SDK to use [goSell API][1].

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://tap-payments.github.io/goSellSDK-Android/)
[![Build Status](https://travis-ci.org/Tap-Payments/goSellSDK-Android.svg?branch=master)](https://travis-ci.org/Tap-Payments/goSellSDK-Android/builds/508932837)
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
   3. [Proguard Rules](#proguard_rules)
3. [Setup](#setup)
    1. [goSellSDK Class Properties](#setup_gosellsdk_class_properties)
4. [Usage](#usage)
    1. [Configure SDK with Required Data](#configure_sdk_with_required_data)
    2. [Configure SDK Look and Feel](#configure_sdk_look_and_feel)
    3. [Configure SDK Session](#configure_sdk_Session)
    4. [Configure SDK Transaction Mode](#configure_sdk_transaction_mode)
    5. [Use Tap PayButton](#init_pay_button)
    6. [List Saved Cards](#list_saved_cards)
    6. [Open SDK Interfaces](#sdk_open_interfaces)
    7. [Open SDK ENUMs](#sdk_open_enums)
    8. [Open SDK Models](#sdk_open_models)
5. [SDKSession Delegate](#sdk_delegate)
    1. [Payment Success Callback](#payment_success_callback)
    2. [Payment Failure Callback](#payment_failure_callback)
    3. [Authorization Success Callback](#authorization_success_callback)
    4. [Authorization Failure Callback](#authorization_failure_callback)
    5. [Card Saving Success Callback](#card_saving_success_callback)
    6. [Card Saving Failure Callback](#card_saving_failure_callback)
    7. [Card Tokenized Success Callback](#card_tokenized_success_callback)
    8. [Saved Cards List Callback](#saved_cards_list_callback)
    9. [Session Other Failure Callback](#session_error_callback)
    10. [Invalid Card Details](#invalid_card_details)
    11. [Backend Un-known Error](#backecnd_unknow_error)
    12. [Invalid Transaction Mode](#invalid_transaction_mode)
    13. [Session Is Starting Callback](#session_is_starting_callback)
    14. [Session Has Started Callback](#session_has_started_callback)
    15. [Session Failed To Start Callback](#session_failed_to_start_callback)
    16. [Session Cancel Callback](#session_cancel_callback)
    17. [User Enabled Save CARD](#user_enabled_save_card_option)
6. [Documentation](#docs)


<a name="requirements"></a>
# Requirements
---
To use the SDK the following requirements must be met:

1. **Android Studio 3.3** or newer
2. **Android SDK Tools 26.1.1** or newer
3. **Android Platform Version: API 28: Android 9.0 (Pie) revision 6** or later
4. **Build gradle:3.5.0

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
    ```java
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
```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```java
	dependencies {
	        implementation 'com.github.Tap-Payments:goSellSDK-Android:2.4.7'
	}
```

<a name="proguard_rules"></a>
### Proguard Rules
---
  Proguard rules for SDK Library are: 
```java
    -keepattributes Signature
    -keepclassmembernames,allowobfuscation interface * {
        @retrofit2.http.* <methods>;
    }
    -dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

    #########################################################################
    # OkHttp
    #########################################################################
    -dontwarn okhttp3.**
    -dontwarn okhttp2.**
    -dontwarn okio.**
    -dontwarn javax.annotation.**
    -dontwarn org.conscrypt.**
    -keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
```  

# Note
---
Regarding to this Issue " Google throws this exception on Activity's onCreate method after v27, their meaning is if an Activity is translucent or floating, its orientation should be relied on parent(background) so, Activity can't make      decision on itself. Even if you remove android:screenOrientation="portrait" from the floating or translucent Activity In android Oreo (API 26) you can not change orientation for Activity that offer translucent.

Your Activity that launches the SDK has to set orientation to Portrait, becuase our SDK is just a child to your actvity in case of translucent mode.


<a name="setup"></a>
# Setup
---
First of all, `goSellSDK` should be set up. In this section secret key and application ID are required.

<a name="setup_gosellsdk_class_properties"></a>
## goSellSDK Class Properties
First of all, `goSellSDK` should be set up. To set it up, add the following lines of code somewhere in your project and make sure they will be called before any usage of `goSellSDK`.

Below is the list of properties in goSellSDK class you can manipulate. Make sure you do the setup before any usage of the SDK.

<a name="setup_gosellsdk_class_properties_secret_key"></a>
### Secret Key and Application ID

To set it up, add the following line of code somewhere in your project and make sure it will be called before any usage of `goSellSDK`, otherwise an exception will be thrown. **Required**.

Android
```java
    GoSellSDK.init(context, "sk_XXXXXXXXXXXXXXXXXXXXXXXX","app_id");
```
1. **`authToken`** - to authorize your requests.// Secret key (format: "sk_XXXXXXXXXXXXXXXXXXXXXXXX")
2. **`app_id`** - replace it using your application ID "Application main package".

Don't forget to import the class at the beginning of the file:

*Android*:

```java
 import company.tap.gosellapi.GoSellSDK;
```
<a name="setup_gosellsdk_class_properties_mode"></a>
### Mode

SDK mode defines which mode SDK is operating in, either **sandbox** or **production**.

SDK Mode is automatically identified in the backend based on the secrete key you defined earlier in setup process.

<a name="setup_gosellsdk_class_properties_language"></a>

Localization language of the UI part of the SDK. This is locale identifier.

Make sure it consists only from 2 lowercased letters and is presented in the list of **availableLanguages** property of *goSellSDK* class.

**Notice:** SDK user interface layout direction is behave similar to your App. There is no effect come form the SDK back to your application locale. 

<a name="usage"></a>
#Usage
---
<a name="configure_sdk_with_required_data"></a>
### Configure SDK With Required Data

`goSellSDK` should be set up. To set it up, add the following lines of code somewhere in your project and make sure they will be called before any usage of `goSellSDK`.

*Android*:
```java

    /**
     * Integrating SDK.
     */
    private void startSDK(){
        /**
         * Required step.
         * Configure SDK with your Secret API key and App Bundle name registered with tap company.
         */
        configureApp();

        /**
         * Optional step
         * Here you can configure your app theme (Look and Feel).
         */
        configureSDKThemeObject();

        /**
         * Required step.
         * Configure SDK Session with all required data.
         */
        configureSDKSession();

        /**
         * Required step.
         * Choose between different SDK modes
         */
        configureSDKMode();

        /**
         * If you included Tap Pay Button then configure it first, if not then ignore this step.
         */
        initPayButton();
    }



```

Below is the list of properties in goSellSDK class you can manipulate. Make sure you do the setup before any usage of the SDK.

<a name="setup_gosellsdk_class_properties_secret_key"></a>
### Configure SDK Secret Key and Application ID

To set it up, add the following line of code somewhere in your project and make sure it will be called before any usage of `goSellSDK`, otherwise an exception will be thrown. **Required**.

Android
```java
     /**
         * Required step.
         * Configure SDK with your Secret API key and App Bundle name registered with tap company.
         */
        private void configureApp(){
            GoSellSDK.init(this, "sk_test_kovrMB0mupFJXfNZWx6Etg5y","company.tap.goSellSDKExample");  // to be replaced by merchant
        }
```
1. **`authToken`** - to authorize your requests.// Secret key (format: "sk_XXXXXXXXXXXXXXXXXXXXXXXX")
2. **`app_id`** - replace it using your application ID "Application main package".

Don't forget to import the class at the beginning of the file:

*Android*:

```java
 import company.tap.gosellapi.GoSellSDK;
```

<a name="configure_sdk_look_and_feel"></a>
### Configure SDK Look and Feel
 To customize the SDK look and feel you must use **ThemeObject** and populate it as following:

 ```java
       private void configureSDKThemeObject() {

          ThemeObject.getInstance()

          // set Appearance mode [Full Screen Mode - Windowed Mode]
          .setAppearanceMode(AppearanceMode.WINDOWED_MODE) // **Required**

          // Setup header font type face **Make sure that you already have asset folder with required fonts**
          .setHeaderFont(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf"))//**Optional**

          //Setup header text color
          .setHeaderTextColor(getResources().getColor(R.color.black1))  // **Optional**

          // Setup header text size
          .setHeaderTextSize(17) // **Optional**

          // setup header background
          .setHeaderBackgroundColor(getResources().getColor(R.color.french_gray_new))//**Optional**

          // setup card form input font type
          .setCardInputFont(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf"))//**Optional**

          // setup card input field text color
          .setCardInputTextColor(getResources().getColor(R.color.black))//**Optional**

          // setup card input field text color in case of invalid input
          .setCardInputInvalidTextColor(getResources().getColor(R.color.red))//**Optional**

          // setup card input hint text color
          .setCardInputPlaceholderTextColor(getResources().getColor(R.color.black))//**Optional**

          // setup Switch button Thumb Tint Color in case of Off State
          .setSaveCardSwitchOffThumbTint(getResources().getColor(R.color.gray)) // **Optional**

          // setup Switch button Thumb Tint Color in case of On State
          .setSaveCardSwitchOnThumbTint(getResources().getColor(R.color.vibrant_green)) // **Optional**

          // setup Switch button Track Tint Color in case of Off State
          .setSaveCardSwitchOffTrackTint(getResources().getColor(R.color.gray)) // **Optional**

          // setup Switch button Track Tint Color in case of On State
          .setSaveCardSwitchOnTrackTint(getResources().getColor(R.color.green)) // **Optional**

          // change scan icon
          .setScanIconDrawable(getResources().getDrawable(R.drawable.btn_card_scanner_normal)) // **Optional**

          // setup pay button selector [ background - round corner ]
          .setPayButtonResourceId(R.drawable.btn_pay_selector)

          // setup pay button font type face
          .setPayButtonFont(Typeface.createFromAsset(getAssets(),"fonts/roboto_light.ttf")) // **Optional**

          // setup pay button disable title color
          .setPayButtonDisabledTitleColor(getResources().getColor(R.color.black)) // **Optional**

          // setup pay button enable title color
          .setPayButtonEnabledTitleColor(getResources().getColor(R.color.White)) // **Optional**

          //setup pay button text size
          .setPayButtonTextSize(14) // **Optional**

          // show/hide pay button loader
          .setPayButtonLoaderVisible(true) // **Optional**

          // show/hide pay button security icon
          .setPayButtonSecurityIconVisible(true) // **Optional**
      ;

          }
 ```

<a name="configure_sdk_Session"></a>
## Configure SDK Session
**SDKSession** is the main interface for goSellSDK library from you application, so you can use it to start SDK with pay button or without pay button.

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
	<td> Activity. used as a context to setup sdk.</td>
   <tr>

   <tr>
	<td> sessionDelegate  </td>
	<td> Activity </td>
	<td> Activity. it is used to notify Merchant application with all SDK Events </td>
   <tr>


</table>

### Methods

<table style="text-align:center">
    <th colspan=1>Property</th>
    <th colspan=1>Type</th>
    <tr>
	 <td> addSessionDelegate  </td>
	 <td> pass your activity that implements SessionDelegate interface . you have to override all methods available through this interface </td>
    </tr>
    <tr>
	 <td> instantiatePaymentDataSource  </td>
	 <td> Payment Data Source Object is the main object that is responsible of holding all data required from our backend to return all payment options [ Debit Cards - Credit Cards ] available for this merchant . </td>
    </tr>
    <tr>
   	 <td> setTransactionCurrency  </td>
   	 <td> Set the transaction currency associated to your account. Transaction currency must be of type TapCurrency("currency_iso_code"). i.e new TapCurrency("KWD") </td>
    </tr>
    <tr>
	 <td> setTransactionMode  </td>
	 <td> SDK offers different transaction modes such as [ TransactionMode.PURCHASE - TransactionMode.AUTHORIZE_CAPTURE - TransactionMode.SAVE_CARD - TransactionMode.TOKENIZE_CARD]   </td>
    </tr>
    <tr>
	 <td> setCustomer </td>
	 <td> Pass your customer data. Customer must be of type Tap Customer. You can create Tap Customer as following
	  new Customer.CustomerBuilder(customerRefNumber).email("cut_email").firstName("cust_firstName")
                      .lastName("cust_lastName").metadata("").phone(new PhoneNumber("country_code","mobileNo"))
                      .middleName("cust_middleName").build();</td>
    </tr>
    <tr>
	 <td> setAmount </td>
	 <td> Set Total Amount. Amount value must be of type BigDecimal i.e new BigDecimal(40) </td>
    </tr>
    <tr>
	 <td> setPaymentItems </td>
	 <td> ArrayList that contains payment items. each item of this array must be of type PaymentItem. in case of SAVE_CARD or TOKENIZE_CARD you can pass it null</td>
    </tr>
    <tr>
  	 <td> setTaxes </td>
  	 <td> ArrayList that contains Tax items. each item of this array must be of type Tax. in case of SAVE_CARD or TOKENIZE_CARD you can pass it null</td>
  	</tr>
  	<tr>
  	 <td> setShipping </td>
  	 <td> ArrayList that contains Shipping items. each item of this array must be of type Shipping. in case of SAVE_CARD or TOKENIZE_CARD you can pass it null</td>
  	</tr>
  	<tr>
  	 <td> setPostURL </td>
  	 <td> POST URL. </td>
  	</tr>
  	<tr>
  	 <td> setPaymentDescription </td>
  	 <td> Payment description. </td>
  	</tr>
  	<tr>
  	 <td> setPaymentMetadata </td>
  	 <td> HashMap that contains any other payment related data. </td>
  	</tr>
  	<tr>
  	 <td> setPaymentReference </td>
  	 <td> Payment reference. it must be of type Reference object or null </td>
  	</tr>
  	<tr>
  	 <td> setPaymentStatementDescriptor </td>
  	 <td> Payment Statement Description </td>
  	</tr>
  	<tr>
  	 <td> isRequires3DSecure </td>
  	 <td> Enable or Disable 3D Secure </td>
  	</tr>
  	<tr>
  	 <td> setReceiptSettings </td>
  	 <td> Identify Receipt Settings. You must pass  Receipt object or null </td>
  	</tr>
  	<tr>
  	 <td> setAuthorizeAction </td>
  	 <td> Identify AuthorizeAction. You must pass AuthorizeAction object or null </td>
  	</tr>
  	<tr>
  	 <td> setDestination </td>
  	 <td> Identify Array of destination. You must pass Destinations object or null </td>
  	</tr>
  	<tr>
  	 <td> start </td>
  	 <td> Start SDK Without using Tap Pay button. You must call this method whereever you want to show TAP Payment screen. Also, you must pass your activity as a context   </td>
  	</tr>
  	<tr>
  	 <td> setButtonView </td>
  	 <td> If you included TAP PayButton in your activity then you need to configure it and then pass it to SDKSession through this method.</td>
    </tr>
</table>


**Configure SDK Session Example**

```java

          /**
           * Configure SDK Session
           */
           private void configureSDKSession() {
            
            // Instantiate SDK Session
            if(sdkSession==null) sdkSession = new SDKSession();   //** Required **
    
            // pass your activity as a session delegate to listen to SDK internal payment process follow
            sdkSession.addSessionDelegate(this);    //** Required **
    
            // initiate PaymentDataSource
            sdkSession.instantiatePaymentDataSource();    //** Required **
    
            // set transaction currency associated to your account
            sdkSession.setTransactionCurrency(new TapCurrency("KWD"));    //** Required **
    
            // Using static CustomerBuilder method available inside TAP Customer Class you can populate TAP Customer object and pass it to SDK
            sdkSession.setCustomer(getCustomer());    //** Required **
    
            // Set Total Amount. The Total amount will be recalculated according to provided Taxes and Shipping
            sdkSession.setAmount(new BigDecimal(40));  //** Required **
    
            // Set Payment Items array list
            sdkSession.setPaymentItems(new ArrayList<PaymentItem>());// ** Optional ** you can pass empty array list
    
            // Set Taxes array list
            sdkSession.setTaxes(new ArrayList<Tax>());// ** Optional ** you can pass empty array list
    
            // Set Shipping array list
            sdkSession.setShipping(new ArrayList<>());// ** Optional ** you can pass empty array list
    
            // Post URL
            sdkSession.setPostURL(""); // ** Optional **
    
            // Payment Description
            sdkSession.setPaymentDescription(""); //** Optional **
    
            // Payment Extra Info
            sdkSession.setPaymentMetadata(new HashMap<>());// ** Optional ** you can pass empty array hash map
    
            // Payment Reference
            sdkSession.setPaymentReference(null); // ** Optional ** you can pass null
    
            // Payment Statement Descriptor
            sdkSession.setPaymentStatementDescriptor(""); // ** Optional **
            
             // Enable or Disable Saving Card
             sdkSession.isUserAllowedToSaveCard(true); //  ** Required ** you can pass boolean
                    
            // Enable or Disable 3DSecure
            sdkSession.isRequires3DSecure(true);
    
            //Set Receipt Settings [SMS - Email ]
            sdkSession.setReceiptSettings(null); // ** Optional ** you can pass Receipt object or null
    
            // Set Authorize Action
            sdkSession.setAuthorizeAction(null); // ** Optional ** you can pass AuthorizeAction object or null
    
            sdkSession.setDestination(null); // ** Optional ** you can pass Destinations object or null
            
            sdkSession.setMerchantID(null); // ** Optional ** you can pass merchant id or null
    
            /**
             * Use this method where ever you want to show TAP SDK Main Screen.
             * This method must be called after you configured SDK as above
             * This method will be used in case of you are not using TAP PayButton in your activity.
             */
            sdkSession.start(this);
                }
 ```
 
 <a name="configure_sdk_transaction_mode"></a>
  **Configure SDK Transaction Mode**
  
  You have to choose only one Mode of the following modes:
  
   **Note:-**
     - In case of using PayButton, then don't call sdkSession.start(this); because the SDK will start when user clicks the tap pay button.

 ```java
        /**
           * Configure SDK Theme
           */
          private void configureSDKMode(){
      
              /**
               * You have to choose only one Mode of the following modes:
               * Note:-
               *      - In case of using PayButton, then don't call sdkSession.start(this); because the SDK will start when user clicks the tap pay button.
               */
              //////////////////////////////////////////////////////    SDK with UI //////////////////////
              /**
               * 1- Start using  SDK features through SDK main activity (With Tap CARD FORM)
               */
              startSDKWithUI();
      
              //////////////////////////////////////////////////////    SDK Tokenization without UI //////////////////////
              /**
               * 2- Start using  SDK to tokenize your card without using SDK main activity (Without Tap CARD FORM)
               * After the SDK finishes card tokenization, it will notify this activity with tokenization result in either
               * cardTokenizedSuccessfully(@NonNull String token) or sdkError(@Nullable GoSellError goSellError)
               */
      //          startSDKTokenizationWithoutUI();
      //        sdkSession.start(this);
      
              //////////////////////////////////////////////////////    SDK Saving card without UI //////////////////////
              /**
               *  3- Start using  SDK to save your card without using SDK main activity ((Without Tap CARD FORM))
               *  After the SDK finishes card tokenization, it will notify this activity with save card result in either
               *  cardSaved(@NonNull Charge charge) or sdkError(@Nullable GoSellError goSellError)
               *
               */
      //         startSDKSavingCardWithoutUI();
      //       sdkSession.start(this);
          }
 ```
 
 <a name="init_pay_button"></a>
   If you included Tap Pay Button then configure it first, if not then ignore this step.
   
  **Use Tap PayButton**
  ```java
     /**
          * Include pay button in merchant page
          */
         private void initPayButton() {
     
             payButtonView = findViewById(R.id.payButtonId);
     
             payButtonView.setupFontTypeFace(ThemeObject.getInstance().getPayButtonFont());
     
             payButtonView.setupTextColor(ThemeObject.getInstance().getPayButtonEnabledTitleColor(),
                     ThemeObject.getInstance().getPayButtonDisabledTitleColor());
     //
             payButtonView.getPayButton().setTextSize(ThemeObject.getInstance().getPayButtonTextSize());
     //
             payButtonView.getSecurityIconView().setVisibility(ThemeObject.getInstance().isPayButtSecurityIconVisible()?View.VISIBLE:View.INVISIBLE);
     
             payButtonView.setBackgroundSelector(ThemeObject.getInstance().getPayButtonResourceId());
     
             if(sdkSession!=null){
                 TransactionMode trx_mode = sdkSession.getTransactionMode();
                 if(trx_mode!=null){
     
                     if (TransactionMode.SAVE_CARD == trx_mode  || TransactionMode.SAVE_CARD_NO_UI ==trx_mode) {
                         payButtonView.getPayButton().setText(getString(company.tap.gosellapi.R.string.save_card));
                     }
                     else if(TransactionMode.TOKENIZE_CARD == trx_mode || TransactionMode.TOKENIZE_CARD_NO_UI == trx_mode){
                         payButtonView.getPayButton().setText(getString(company.tap.gosellapi.R.string.tokenize));
                     }
                     else {
                         payButtonView.getPayButton().setText(getString(company.tap.gosellapi.R.string.pay));
                     }
                 }else{
                     configureSDKMode();
                 }
                 sdkSession.setButtonView(payButtonView, this, SDK_REQUEST_CODE);
             }
     
     
         }
 
  ```         
  
<a name="list_saved_cards"></a>
  **List Saved Cards**
  ```java
       /**
          * retrieve list of saved cards from the backend.
          */
         private void listSavedCards(){
             if(sdkSession!=null)
                 sdkSession.listAllCards("CUSTOMER_ID",this);
         } 
  ```                  
**To populate TAP Customer object**
```java
     private Customer getCustomer() {
                         return new Customer.CustomerBuilder(null).email("abc@abc.com").firstName("firstname")
                                 .lastName("lastname").metadata("").phone(new PhoneNumber("965","65562630"))
                                 .middleName("middlename").build();
     }
```

<a name="sdkSession_data_source"></a>
### SDKSession Payment Data Source

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

</table>

<a name="sdk_open_interfaces"></a>
## SDK Open Interfaces
 SDK open Interfaces available for implementation through Merchant Project:

 1. SessionDelegate
 ```java
      public interface SessionDelegate {
      
                      void paymentSucceed(@NonNull Charge charge);
                      void paymentFailed(@Nullable Charge charge);
              
                      void authorizationSucceed(@NonNull Authorize authorize);
                      void authorizationFailed(Authorize authorize);
              
              
                      void cardSaved(@NonNull Charge charge);
                      void cardSavingFailed(@NonNull Charge charge);
              
                      void cardTokenizedSuccessfully(@NonNull Token token);
              
                      void sdkError(@Nullable GoSellError goSellError);
              
                      void sessionIsStarting();
                      void sessionHasStarted();
                      void sessionCancelled();
                      void sessionFailedToStart();
              
                      void invalidCardDetails();
              
                      void backendUnknownError();
              
                      void invalidTransactionMode();

                      void invalidCustomerID();

                      void userEnabledSaveCardOption(boolean saveCardEnabled);
      }
 ```
 2. PaymentDataSource
 ```java
      public interface PaymentDataSource {
      
          /**
           * Transaction currency. @return the currency
           */
          @NonNull     TapCurrency            getCurrency();
      
          /**
           * Customer. @return the customer
           */
          @NonNull     Customer                getCustomer();
      
          /**
           * Amount. Either amount or items should return nonnull value. If both return nonnull, then items is preferred. @return the amount
           */
          @Nullable    BigDecimal              getAmount();
      
          /**
           * List of items to pay for. Either amount or items should return nonnull value. If both return nonnull, then items is preferred. @return the items
           */
          @Nullable    ArrayList<PaymentItem>  getItems();
      
          /**
           * Transaction mode. If you return null in this method, it will be treated as PURCHASE. @return the transaction mode
           */
          @Nullable    TransactionMode         getTransactionMode();
      
          /**
           * List of taxes. Optional. Note: specifying taxes will affect total payment amount. @return the taxes
           */
          @Nullable    ArrayList<Tax>          getTaxes();
      
          /**
           * Shipping list. Optional. Note: specifying shipping will affect total payment amount. @return the shipping
           */
          @Nullable    ArrayList<Shipping>     getShipping();
      
          /**
           * Tap will post to this URL after transaction finishes. Optional. @return the post url
           */
          @Nullable    String                  getPostURL();
      
          /**
           * Description of the payment. @return the payment description
           */
          @Nullable    String                  getPaymentDescription();
      
          /**
           * If you would like to pass additional information with the payment, pass it here. @return the payment metadata
           */
          @Nullable    HashMap<String, String> getPaymentMetadata();
      
          /**
           * Payment reference. Implement this property to keep a reference to the transaction on your backend. @return the payment reference
           */
          @Nullable    Reference               getPaymentReference();
      
          /**
           * Payment statement descriptor. @return the payment statement descriptor
           */
          @Nullable    String                  getPaymentStatementDescriptor();
      
          /**
           * Defines if 3D secure check is required. @return the requires 3 d secure
           */
          @Nullable    boolean                 getRequires3DSecure();
      
          /**
           * Receipt dispatch settings. @return the receipt settings
           */
          @Nullable    Receipt                 getReceiptSettings();
      
          /**
           * Action to perform after authorization succeeds. Used only if transaction mode is AUTHORIZE_CAPTURE. @return the authorize action
           */
          @Nullable    AuthorizeAction         getAuthorizeAction();
      
          /**
           *  The Destination array contains list of Merchant desired destinations accounts to receive money from payment transactions
           */
      
          @Nullable
          Destinations getDestination();
      }
 ```
 
<a name="sdk_open_enums"></a>
## SDK Open ENUMs
 SDK open Enums available for implementation through Merchant Project:

 1. AppearanceMode 
 ```java
    public enum AppearanceMode {
    
        /**
         * Windowed mode with translucent
         */
        @SerializedName("WINDOWED_MODE")            WINDOWED_MODE,
        /**
         * Full screen mode
         */
        @SerializedName("FULLSCREEN_MODE")          FULLSCREEN_MODE,
    }

 ```
 2. TransactionMode 
 ```java
    public enum TransactionMode {
    
            /**
             * Purchase transaction mode.
             */
            @SerializedName("PURCHASE")                           PURCHASE,
            /**
             * Authorize capture transaction mode.
             */
            @SerializedName("AUTHORIZE_CAPTURE")                   AUTHORIZE_CAPTURE,
            /**
             * Save card transaction mode.
             */
            @SerializedName("SAVE_CARD")                           SAVE_CARD,
            /**
             * Tokenize card mode.
             */
            @SerializedName("TOKENIZE_CARD")                       TOKENIZE_CARD,
        
        /////////////////////////////////////  APIs exposer without UI ////////////////////////////
            /**
             * Tokenize card mode no UI.
             */
            @SerializedName("TOKENIZE_CARD_NO_UI")                 TOKENIZE_CARD_NO_UI,
        
            /**
             * Save card transaction mode no UI.
             */
            @SerializedName("SAVE_CARD_NO_UI")                      SAVE_CARD_NO_UI,
    }
 ```
 <a name="sdk_open_models"></a>
 ## SDK Open Models
  SDK open Models available for implementation through Merchant Project: 
  
  1. Customer 
   ```java
      public final class Customer implements Serializable {
      
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
      
        /**
         * The Meta data.
         */
        @SerializedName("metadata")
        String metaData;
     }
   ```
   2. TapCurrency 
   ```java
      public class TapCurrency {
      
        @NonNull
        private String isoCode;
      
          /**
           * Instantiates a new Tap currency.
           *
           * @param isoCode the iso code
           * @throws CurrencyException the currency exception
           */
          public TapCurrency(@NonNull String isoCode) throws CurrencyException {
          String code = isoCode.toLowerCase();
          if(!LocaleCurrencies.checkUserCurrency(code)) {
            throw CurrencyException.getUnknown(code);
          }
          this.isoCode = code;
        }
      
      
          /**
           * Gets iso code.
           *
           * @return the iso code
           */
          @NonNull
        public String getIsoCode() {
          return isoCode;
        }
      
      }
   ```
   3. AuthorizeAction 
      ```java
         public final class AuthorizeAction {
         
             @SerializedName("type")
             @Expose
             private AuthorizeActionType type;
         
             @SerializedName("time")
             @Expose
             private int timeInHours;
         
             /**
              * Gets default.
              *
              * @return the default
              */
             public static AuthorizeAction getDefault() {
         
                 return new AuthorizeAction(AuthorizeActionType.VOID, 168);
             }
         
             /**
              * Gets type.
              *
              * @return the type
              */
             public AuthorizeActionType getType() {
                 return type;
             }
         
             /**
              * Gets time in hours.
              *
              * @return the time in hours
              */
             public int getTimeInHours() {
                 return timeInHours;
             }
         
             /**
              * Instantiates a new Authorize action.
              *
              * @param type        the type
              * @param timeInHours the time in hours
              */
             public AuthorizeAction(AuthorizeActionType type, int timeInHours) {
         
                 this.type = type;
                 this.timeInHours = timeInHours;
             }
         }
      ```
      4. Destination 
         ```java
           public class Destination implements Serializable {
           
               @SerializedName("id")
               @Expose
               private String id;                              // Destination unique identifier (Required)
           
               @SerializedName("amount")
               @Expose
               private BigDecimal amount;                      // Amount to be transferred to the destination account (Required)
           
               @SerializedName("currency")
               @Expose
               private String currency;                   // Currency code (three digit ISO format) (Required)
           
               @SerializedName("description")
               @Expose
               private String description;                    //  Description about the transfer (Optional)
           
               @SerializedName("reference")
               @Expose
               private String reference;                     //   Merchant reference number to the destination (Optional)
           
           
               /**
                * Create an instance of destination
                * @param id
                * @param amount
                * @param currency
                * @param description
                * @param reference
                */
               public Destination(String id, BigDecimal amount, TapCurrency currency, String description, String reference) {
                   this.id = id;
                   this.amount = amount;
                   this.currency = (currency!=null)? currency.getIsoCode().toUpperCase():null;
                   this.description =description;
                   this.reference = reference;
               }
           
               /**
                *
                * @return Destination unique identifier
                */
               public String getId() {
                   return id;
               }
           
               /**
                *
                * @return Amount to be transferred to the destination account
                */
               public BigDecimal getAmount() {
                   return amount;
               }
           
               /**
                *
                * @return Currency code (three digit ISO format)
                */
               public String getCurrency() {
                   return currency;
               }
           
               /**
                *
                * @return Description about the transfer
                */
               public String getDescription() {
                   return description;
               }
           
               /**
                *  Merchant reference number to the destination
                * @return
                */
               public String getReference() {
                   return reference;
               }
           }

         ```
      5. Destinations 
         ```java
            public class Destinations  implements Serializable {
                @SerializedName("amount")
                @Expose
                private BigDecimal             amount;
            
                @SerializedName("currency")
                @Expose
                private String                currency;
            
                @SerializedName("count")
                @Expose
                private int                     count;
            
                @SerializedName("destination")
                @Expose
                private ArrayList<Destination>  destination;
            
                /**
                 * @param amount
                 * @param currency
                 * @param count
                 * @param destinations
                 */
                public Destinations(@NonNull  BigDecimal amount, @NonNull TapCurrency currency, int count, @NonNull ArrayList<Destination> destinations)
                {
                    this.amount = amount;
                    this.currency = (currency!=null)? currency.getIsoCode().toUpperCase():null;
                    this.count = count;
                    this.destination = destinations;
                }
            
                /**
                 * Total amount, transferred to the destination account
                 * @return
                 */
                public BigDecimal getAmount() {
                    return amount;
                }
            
                /**
                 * Tap transfer currency code
                 * @return
                 */
                public String getCurrency() {
                    return currency;
                }
            
                /**
                 * Total number of destinations transfer involved
                 * @return
                 */
                public int getCount() {
                    return count;
                }
            
                /**
                 * List of destinations object
                 * @return
                 */
                public ArrayList<Destination> getDestination() {
                    return destination;
                }
            }
         ```
      6. PaymentItem 
         ```java
            public class PaymentItem {
            
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
      6. Receipt 
           ```java
              public final class Receipt implements Serializable {
              
                  @SerializedName("id")
                  @Expose
                  @Nullable private String id;
              
                  @SerializedName("email")
                  @Expose
                  private boolean email;
              
                  @SerializedName("sms")
                  @Expose
                  private boolean sms;
              
                  /**
                   * Instantiates a new Receipt.
                   *
                   * @param email the email
                   * @param sms   the sms
                   */
                  public Receipt(boolean email, boolean sms) {
                      this.email = email;
                      this.sms = sms;
                  }
              
                  /**
                   * Gets id.
                   *
                   * @return the id
                   */
                  public String getId() {
                      return id;
                  }
              
                  /**
                   * Is email boolean.
                   *
                   * @return the boolean
                   */
                  public boolean isEmail() {
                      return email;
                  }
              
                  /**
                   * Is sms boolean.
                   *
                   * @return the boolean
                   */
                  public boolean isSms() {
                      return sms;
                  }
              }
           ```
       6. Reference 
            ```java
               public final class Reference implements Serializable {
               
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
               
                   /**
                    * Gets acquirer.
                    *
                    * @return the acquirer
                    */
                   @Nullable public String getAcquirer() {
                       return acquirer;
                   }
               
                   /**
                    * Gets gateway.
                    *
                    * @return the gateway
                    */
                   @Nullable public String getGateway() {
                       return gateway;
                   }
               
                   /**
                    * Gets payment.
                    *
                    * @return the payment
                    */
                   @Nullable public String getPayment() {
                       return payment;
                   }
               
                   /**
                    * Gets track.
                    *
                    * @return the track
                    */
                   @Nullable public String getTrack() {
                       return track;
                   }
               
                   /**
                    * Gets transaction.
                    *
                    * @return the transaction
                    */
                   @Nullable public String getTransaction() {
                       return transaction;
                   }
               
                   /**
                    * Gets order.
                    *
                    * @return the order
                    */
                   @Nullable public String getOrder() {
                       return order;
                   }
               
                   /**
                    * Instantiates a new Reference.
                    *
                    * @param acquirer    the acquirer
                    * @param gateway     the gateway
                    * @param payment     the payment
                    * @param track       the track
                    * @param transaction the transaction
                    * @param order       the order
                    */
                   public Reference(@Nullable String acquirer, @Nullable String gateway, @Nullable String payment, @Nullable String track, @Nullable String transaction, @Nullable String order) {
               
                       this.acquirer = acquirer;
                       this.gateway = gateway;
                       this.payment = payment;
                       this.track = track;
                       this.transaction = transaction;
                       this.order = order;
                   }
               }

            ```
        6. Shipping 
             ```java
                public final class Shipping {
                
                    @SerializedName("name")
                    @Expose
                    private String name;
                
                    @SerializedName("description")
                    @Expose
                    @Nullable private String description;
                
                    @SerializedName("amount")
                    @Expose
                    private BigDecimal amount;
                
                    /**
                     * Instantiates a new Shipping.
                     *
                     * @param name        the name
                     * @param description the description
                     * @param amount      the amount
                     */
                    public Shipping(String name, @Nullable String description, BigDecimal amount) {
                
                        this.name           = name;
                        this.description    = description;
                        this.amount         = amount;
                    }
                
                    /**
                     * Gets amount.
                     *
                     * @return the amount
                     */
                    public BigDecimal getAmount() {
                
                        return amount;
                    }
                }
             ```                   
      6. Tax 
           ```java
             public final class Tax {
             
                 @SerializedName("name")
                 @Expose
                 private String name;
             
                 @SerializedName("description")
                 @Expose
                 @Nullable private String description;
             
                 @SerializedName("amount")
                 @Expose
                 private AmountModificator amount;
             
                 /**
                  * Instantiates a new Tax.
                  *
                  * @param name        the name
                  * @param description the description
                  * @param amount      the amount
                  */
                 public Tax(String name, @Nullable String description, AmountModificator amount) {
                     this.name = name;
                     this.description = description;
                     this.amount = amount;
                 }
             
                 /**
                  * Gets name.
                  *
                  * @return the name
                  */
                 public String getName() {
                     return name;
                 }
             
                 /**
                  * Gets description.
                  *
                  * @return the description
                  */
                 @Nullable public String getDescription() {
                     return description;
                 }
             
                 /**
                  * Gets amount.
                  *
                  * @return the amount
                  */
                 public AmountModificator getAmount() {
                     return amount;
                 }
             }
           ```
      7. CardsList
           ```java
            public class CardsList {
            
                private int responseCode;
                private String object;
                private boolean has_more;
                private ArrayList<SavedCard> cards;
            
            
                public CardsList( int responseCode,String object,boolean has_more,ArrayList<SavedCard> data){
                    this.responseCode = responseCode;
                    this.object  = object;
                    this.has_more = has_more;
                    this.cards = data;
                }
            
                /**
                 * Gets Response Code
                  * @return responseCode
                 */
                public int getResponseCode(){
                    return responseCode;
                }
            
                /**
                 *  Gets Object type
                 * @return object
                 */
                public String getObject() {
                    return object;
                }
            
                /**
                 *  Check if customer has more cards
                 * @return has_more
                 */
                public boolean isHas_more() {
                    return has_more;
                }
            
                /**
                 * Gets cards.
                 *
                 * @return the cards
                 */
                  public ArrayList<SavedCard> getCards() {
            
                    if ( cards == null ) {
            
                        cards = new ArrayList<>();
                    }
            
                    return cards;
                }
            }
           ```        
<a name="sdk_delegate"></a>
## SDKSession Delegate

**SessionDelegate** is an interface which you may want to implement to receive payment/authorization/card saving status updates and update your user interface accordingly when payment window closes.

Below are listed down all available callbacks:

<a name="payment_success_callback"></a>
### Payment Success Callback

Notifies the receiver that payment has succeed.

#### Declaration

```java
- void paymentSucceed(@NonNull Charge charge);
```

#### Arguments

**charge**: Successful charge object.

<a name="payment_failure_callback"></a>
### Payment Failure Callback

Notifies the receiver that payment has failed.

#### Declaration

*Android:*

```java
- void paymentFailed(@Nullable Charge charge);
```

#### Arguments

**charge**: Charge object that has failed (if reached the stage of charging).

<a name="authorization_success_callback"></a>
### Authorization Success Callback

Notifies the receiver that authorization has succeed.

#### Declaration

*Android:*

```java
- void authorizationSucceed(@NonNull Authorize authorize);
```

#### Arguments

**authorize**: Successful authorize object.

<a name="authorization_failure_callback"></a>
### Authorization Failure Callback

Notifies the receiver that authorization has failed.

#### Declaration

*Android*

```java
- void authorizationFailed(Authorize authorize);
```

#### Arguments

**authorize**: Authorize object that has failed (if reached the stage of authorization).

<a name="card_saving_success_callback"></a>
### Card Saving Success Callback

Notifies the receiver that the customer has successfully saved the card.

#### Declaration

*Android*

```java
- void cardSaved(@NonNull Charge charge); // you have to cast Charge object to SaveCard object first to get card info 
```

#### Arguments

**Charge**: Charge object with the details.

<a name="card_saving_failure_callback"></a>
### Card Saving Failure Callback

Notifies the receiver that the customer failed to save the card.

#### Declaration

*Android:

```java
- void cardSavingFailed(@NonNull Charge charge);
```

#### Arguments

**Charge**: Charge object with the details (if reached the stage of card saving).

<a name="card_tokenized_success_callback"></a>
### Card Tokenized Success Callback

Notifies the receiver that the card has successfully tokenized.

#### Declaration

*Android*

```java
- void cardTokenizedSuccessfully(@NonNull Token token);
```

#### Arguments

**token**: card token object.

<a name="saved_cards_list_callback"></a>
### Saved Cards List Callback

Notifies the receiver with list of saved cards for a customer. If customer has no cards then you will receive the same response but with empty cards array. 

#### Declaration

*Android*

```java
-  void savedCardsList(@NonNull CardsList cardsList);
```

#### Arguments

**cardsList**: CardsList model that holds the response.


<a name="session_error_callback"></a>
### Session Other Failure Callback

Notifies the receiver if any other error occurred.

#### Declaration

*Android:

```java
- void sdkError(@Nullable GoSellError goSellError);
```

#### Arguments

**GoSellError**: GoSellError object with details of error.


<a name="invalid_card_details"></a>
### Invalid Card details

Notifies the client that card data passed are invalid

#### Declaration

*Android:

```java
- void invalidCardDetails();
```


<a name="backecnd_unknow_error"></a>
### Backend Unknown Error

Notifies the client that an unknown error has occurred in the backend

#### Declaration

*Android:

```java
-  void backendUnknownError();
```

<a name="invalid_transaction_mode"></a>
### Invalid Transaction Mode

Notifies the client that Transaction Mode not configured.

#### Declaration

*Android:

```java
-  void invalidTransactionMode();
```

<a name="user_enabled_save_card_option"></a>
### User Enabled Save Card call back

Notifies the receiver (Merchant Activity) that the user wants to save his card.

#### Declaration

*Android:*

```java
- void userEnabledSaveCardOption(boolean saveCardEnabled);
```

<a name="session_is_starting_callback"></a>
### Session Is Starting Callback

Notifies the receiver that session is about to start, but hasn't yet shown the SDK UI. You might want to use this method if you are not using `PayButton` in your application and want to show a loader before SDK UI appears on the screen.

#### Declaration

*Android:*

```java
- void sessionIsStarting();
```

<a name="session_has_started_callback"></a>
### Session Has Started Callback

Notifies the receiver that session has successfully started and shown the SDK UI on the screen. You might want to use this method if you are not using `PayButton` in your application and want to hide a loader after SDK UI has appeared on the screen.

#### Declaration

*Android:*

```java
-  void sessionHasStarted();
```

<a name="session_failed_to_start_callback"></a>
### Session Failed To Start Callback

Notifies the receiver that session has failed to start.

#### Declaration

*Android:*

```java
-  void sessionFailedToStart();
```

<a name="session_cancel_callback"></a>
### Session Cancel Callback

Notifies the receiver (Merchant Activity) that the user cancelled payment process, clicked on soft back button, clicked hard back button or clicked Header cancel button. 

#### Declaration

*Android:*

```java
- void sessionCancelled();
```

-----
<a name="docs"></a>
# Documentation
Documentation is available at [github-pages][2].<br>
Also documented sources are attached to the library.

[1]:https://www.tap.company/developers/
[2]:https://tap-payments.github.io/goSellSDK-Android/
