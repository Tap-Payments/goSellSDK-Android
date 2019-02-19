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
    2. [Setup Steps](#setup_steps)
4. [Usage](#usage)
    2. [SDKTrigger](#sdkTrigger)
         1.[Start SDK with Pay Button](#start_sdk_with_pay_button)
           1. [Init Pay Button](#pay_button_init)
           2. [Appearance](#pay_button_appearance)
           3. [Methods](#pay_button_methods)
    	   4. [Properties](#sdkTrigger_properties)
    3. [API SDKTrigger](#api_sdkTrigger)
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
<a name="setup_steps"></a>
## Setup Steps
### With SDKTrigger
...
...
...
<a name="usage"></a>
#Usage
---
<a name="sdkTrigger"></a>
### SDKTrigger
---
<a name="start_sdk_with_pay_button"></a>
you can initialize goSellSDK with pay button as following:


