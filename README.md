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
   1. [Installation with Gradle](#installation_with_gradle)
3. [Setup](#setup)
    1. [goSellSDK Class Properties](#setup_gosellsdk_class_properties)
    2. [Setup Steps](#setup_steps)
4. [Usage](#usage)
    1. [Pay Button](#pay_button)
        1. [Pay Button Placement](#pay_button_placement)
        2. [Properties](#pay_button_properties)
        3. [Methods](#pay_button_methods)
    2. [SDKTrigger](#sdkTrigger)
    	 1. [Properties](#sdkTrigger_properties)
    	 2. [Methods](#sdkTrigger_methods)
    3. [API Session](#api_sdkTrigger)
    	 1. [Properties](#api_sdkTrigger_properties)
    	 2. [Methods](#api_sdkTrigger_methods)
    4. [SDKTrigger Data Source](#sdkTrigger_data_source)
        1. [Structure](#sdkTrigger_data_source_structure)
        2. [Samples](#sdkTrigger_data_source_samples)
    5. [SDK Delegate](#sdk_delegate)
        1. [Payment Success Callback](#payment_success_callback)
        2. [Payment Failure Callback](#payment_failure_callback)
        3. [Authorization Success Callback](#authorization_success_callback)
        4. [Authorization Failure Callback](#authorization_failure_callback)
        5. [Card Saving Success Callback](#card_saving_success_callback)
        6. [Card Saving Failure Callback](#card_saving_failure_callback)
        7. [Session Is Starting Callback](#session_is_starting_callback)
        8. [Session Has Started Callback](#session_has_started_callback)
        9. [Session Has Failed to Start Callback](#session_has_failed_to_start_callback)
        10. [Session Cancel Callback](#session_cancel_callback)
    6. [SDK Appearance](#sdk_appearance)

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
### Include goSellSDK library source code to your project
---
1. Clone goSellSDK library from Tap repository 
   ```
       git@github.com:Tap-Payments/goSellSDK-Android.git 
    ```   
2. Add goSellSDK library to your project settings.gradle file as following
    ```Android
        include ':library', ':YourAppName'
    ```  
3. Setup your project to include goSellSDK as a dpenedecy Module.
   1. File -> Project Structure -> Modules -> << your project name >>
   2. Dependencies -> click on **+** icon in the screen bottom -> add Module Dependency
   3. select goSellSDK library
   
   
   
<a name="installation_with_gradle"></a>
# Installation with Gradle
---
To integrate goSellSDK into your project add it in your **root** `build.gradle` at the end of repositories:
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```groovy
	dependencies {
	        compile 'com.github.Tap-Payments:goSellSDK-Android:1.0.2'
	}
```

Usage
-------------

Setup
--------------
First of all, `goSellSDK` should be set up. To set it up, add the following lines of code somewhere in your project and make sure they will be called before any usage of `goSellSDK`.

Android
```java
    GoSellSDK.init(context, "sk_test_kovrMB0mupFJXfNZWx6Etg5y"); // Authentication key
```
1. **`authToken`** - to authorize your requests.
2. **`encryptionKey`** - to protect sensitive card details.

**Step 2.** Obtain an instance of **`GoSellAPI`** class via **`getInstance(authToken)`** method.

**Step 3.** Now you can call API methods with this instance, providing necessary parameters and **`APIRequestCallback<>`** class, instantiated with corresponding response type.

Usage examples
-------------
**Create token**

```java
GoSellAPI.getInstance(AUTH_TOKEN).createToken(
    new CardRequest
            .Builder("4111111111111111", "10", "20", "123", YOUR_ENCRYPTION_KEY)
            .address_city("Some city")
            .address_line1("First line")
            .address_line2("Second line")
            .address_state("Royal State")
            .address_zip("007")
            .address_country("Some country")
            .name("Testing VISA card")
            .build(),
    new APIRequestCallback<Token>() {
        @Override
        public void onSuccess(int responseCode, Token serializedResponse) {
            synchronized (this) {
                Log.d(TAG, "onSuccess createToken serializedResponse:" + serializedResponse);
            }
        }

        @Override
        public void onFailure(GoSellError errorDetails) {
            Log.d(TAG, "onFailure createToken, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
        }
    });
```


**Create charge**

```java
HashMap<String, String> chargeMetadata = new HashMap<>();
chargeMetadata.put("Order Number", "ORD-1001");

GoSellAPI.getInstance(AUTH_TOKEN).createCharge(
    new CreateChargeRequest
            .Builder(1000, "KWD", new Redirect("your_return_url", "your_post_url"))
            .source(new Source("tok_XXXXXXXXXXXXXXXXXXXXXXXX"))
            .statement_descriptor("Test Txn 001")
            .description("Test Transaction")
            .metadata(chargeMetadata)
            .receipt_sms("96598989898")
            .receipt_email("test@test.com")
            .build(),
    new APIRequestCallback<Charge>() {
        @Override
        public void onSuccess(int responseCode, Charge serializedResponse) {
            Log.d(TAG, "onSuccess createCharge: serializedResponse:" + serializedResponse);
        }

        @Override
        public void onFailure(GoSellError errorDetails) {
            Log.d(TAG, "onFailure createCharge, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
        }
    }
);
```
**Create redirect**<br><br>
Redirect instance can be created in two ways - providing both return and post url, or only return one.
   
```java
Redirect redirect = new Redirect("your_return_url", "your_post_url");
Redirect redirect = new Redirect("your_return_url");
```

**Create source**<br><br>
Source instance can be created in a multiple ways - providing token id, static id or card details.
   
```java
Source source = new Source("your_token_id");
Source source = new Source("src_kw.knet");
Source source = new Source("card", "12", "20", "4242424242424242", "123");
```


Also see `company.tap.gosellapi.TestActivity` class for usage examples.

Documentation
-------------
Documentation is available at [github-pages][2].<br>
Also documented sources are attached to the library.

[2]:https://tap-payments.github.io/goSellSDK-Android/

[1]:https://www.tap.company/developers/
