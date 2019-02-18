# goSell Android SDK
Android SDK to use [goSell API][1].

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://tap-payments.github.io/goSellSDK-Android/)
[![Build Status](https://img.shields.io/badge/build-passing-success.svg?branch=development_2.0)](https://travis-ci.org/Tap-Payments/goSellSDK-Android/builds/494970284)
[![Documentation](https://img.shields.io/badge/documentation-100%25-bright%20green.svg)](https://tap-payments.github.io/goSellSDK-Android/)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-16-blue.svg)](https://stuff.mit.edu/afs/sipb/project/android/docs/reference/packages.html)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-28-informational.svg)](https://stuff.mit.edu/afs/sipb/project/android/docs/reference/packages.html)

# Install
---------
Add it in your **root** `build.gradle` at the end of repositories:
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

Basic usage
-------------
**Step 1.** Grab credentials from your dashboard:<br>
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
