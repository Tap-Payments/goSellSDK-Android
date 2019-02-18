# goSellSDK-Android

<img src="https://camo.githubusercontent.com/0c96fa3c59c1956329e63f517c47dcb75abda150/68747470733a2f2f696d672e736869656c64732e696f2f636f636f61706f64732f702f676f53656c6c53444b2e7376673f7374796c653d666c6174" alt="Platform" data-canonical src="https://img.shields.io/cocoapods/p/goSellSDK.svg?style=flat" style="max-width:100%;">

####### A library that fully covers payment/authorization/card saving process inside your iOS application.

Install
--------
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
        compile 'com.github.Tap-Payments:goSellSDK-Android:1.1.2'
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
            Log.d(TAG, "onSuccess createToken serializedResponse:" + serializedResponse);
        }

        @Override
        public void onFailure(GoSellError errorDetails) {
            Log.d(TAG, "onFailure createToken, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
        }
    }
);
```


**Create charge**

```java
HashMap<String, String> chargeMetadata = new HashMap<>();
chargeMetadata.put("Order Number", "ORD-1001");

GoSellAPI.getInstance(AUTH_TOKEN).createCharge(
    new CreateChargeRequest
        .Builder(10, 
            "KWD", 
            new CreateChargeRequest.Customer("some email", "33333333", "first name"),
            new Redirect("http://return.com/returnurl", "http://return.com/posturl"))
        .threeDSecure(true)
        .transaction_reference("Trans ref")
        .order_reference("Order ref")
        .statement_descriptor("Test Txn 001")
        .receipt(new CreateChargeRequest.Receipt(true, true))
        .source(new CreateChargeRequest.Source(token.getId()))
        .description("Test Transaction")
        .metadata(chargeMetadata)
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

**Create customer**

```java
HashMap<String, String> customerMetadata = new HashMap<>();
customerMetadata.put("Gender", "Male");
customerMetadata.put("Nationality", "Kuwaiti");
GoSellAPI.getInstance(AUTH_TOKEN).createCustomer(
    new CustomerRequest
            .Builder("Test User", "0096598989898", "test@test.com")
            .description("test description")
            .metadata(customerMetadata)
            .currency("KWT")
            .build(),
    new APIRequestCallback<Customer>() {
        @Override
        public void onSuccess(int responseCode, Customer serializedResponse) {
            Log.d(TAG, "onSuccess createCustomer: ");
        }

        @Override
        public void onFailure(GoSellError errorDetails) {
            Log.d(TAG, "onFailure createCustomer, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
        }
    }
);
```

**Create card**

```java
GoSellAPI.getInstance(AUTH_TOKEN).createCard(
    customer.getId(),
    "tok_XXXXXXXXXXXXXXXXXXXXXXXX",
    new APIRequestCallback<Card>() {
        @Override
        public void onSuccess(int responseCode, Card serializedResponse) {
            Log.d(TAG, "onSuccess createCard: ");
        }

        @Override
        public void onFailure(GoSellError errorDetails) {
            Log.d(TAG, "onFailure createCard, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
        }
    }
);
```

**Get bin number details**

```java
GoSellAPI.getInstance(AUTH_TOKEN).getBINNumberDetails(
    "516874",
    new APIRequestCallback<BIN>() {
        @Override
        public void onSuccess(int responseCode, BIN serializedResponse) {
            Log.d(TAG, "onSuccess getBINNumberDetails: serializedResponse:" + serializedResponse);
        }

        @Override
        public void onFailure(GoSellError errorDetails) {
            Log.d(TAG, "onFailure getBINNumberDetails, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
        }
    }
);
```

Also see `company.tap.gosellapi.TestActivity` class for usage examples.

Documentation
-------------
Documentation is available at [github-pages][2].<br>
Also documented sources are attached to the library.

[2]:https://tap-payments.github.io/goSellSDK-Android/

[1]:https://www.tap.company/developers/
