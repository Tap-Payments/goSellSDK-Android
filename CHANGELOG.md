# CHANGELOG
## [2.9.0] - 2020-07-16
### Version release with New Feature:
- Merchant can now enable or disable defaultCardHolderName (Optional) and pass through the sdk session.This allows him from editing the name .
- Update Readme and ChangeLog.
## [2.8.0] - 2020-06-08
### Version release with New Feature:
- Merchant can now set defaultCardHolderName (Optional) and pass through the sdk session.This allows him from typing the name repeatedly.
- Update Readme and ChangeLog.
## [2.7.8] - 2020-04-19
### Version release HotFix:
- CardType validation on Scanning clear on cancel
- Update Readme and ChangeLog.
## [2.7.7] - 2020-04-19
### Version release HotFix:
- CardType validation on Scanning
- Update Readme and ChangeLog.
## [2.7.6] - 2020-04-12
### Version release Enhancement:
- Updated Expiry object in Card Object
- Add getter for lastfour
- Update Readme and ChangeLog
## [2.7.5] - 2020-04-12
### Version release HotFixes:
- Reloading of 3D securepage stopped
- Keyboard Listener Issue resolved
- Asynchronous missing as in X added
- Update Readme and ChangeLog
### Impact on existing integrations:
- Always good to update to latest release
## [2.7.4] - 2020-04-07
### Version release HotFixes:
- Reloading of 3D securepage stopped
- Update Readme and ChangeLog
### Impact on existing integrations:
- The new update will not affect current integration.
### Required changes for existing integrations before updating:
- Any one facing the reloading issue of the 3d secure page must switch to latest release.
## [2.7.3] - 2020-04-06
### Hot Fix:
- Upgraded TapGLkit.
### Impact on existing integrations:
- The new update will not affect current integration.
### Required changes for existing integrations before updating:
- If the Merchant faces issue in release apk then they can use the above version.
## [2.7.2] - 2020-03-08
### Hot Fix:
- Bug Fix with proguardRules.
### Impact on existing integrations:
- The new update will not affect current integration.
### Required changes for existing integrations before updating:
- If the Merchant faces issue in release apk then they can use the above version.
## [2.7.1] - 2020-03-04
### Hot Fix:
- Added a parameter(gosell_id) in Refrence model class exposed to Merchant.
### Impact on existing integrations:
- The new update will not affect current integration.
### Required changes for existing integrations before updating:
- If the Merchant wishes to use the above feature can take the above version.
## [2.7.0] - 2020-03-04
### Version release additional feature:
- Listing CardTypes based on Merchant choice.
### Impact on existing integrations:
- The new update will not affect current integration.
### Required changes for existing integrations before updating:
- If the Merchant wishes to use the above feature can take the above version.
## [2.6.1] - 2020-03-04
### Version release additional feature:
- 3DS handling on the SDK
### Impact on existing integrations:
- The new update will not affect current integration.
### Required changes for existing integrations before updating:
- Not req
## [2.6.0] - 2020-02-23
### Version release  with new feature:
- Merchant can now set cardType[CREDIT/DEBIT] and pass through the sdk session.
- sdk allows theming of dialog alert.
- extra fees check as min fees and max fees.
- payment Type added in request.
- check for application_verified.
### Impact on existing integrations:
- The new update will not affect current integration.
### Required changes for existing integrations before updating:
- If the merchant wants to use the latest feature then update to the above version.
## [2.5.0] - 2020-02-03
### Version release for supporting additional languages:
- Sdk release to support new languages German and Turkish

### Impact on existing integrations:
- The new update will not affect current integration.

### Required changes for existing integrations before updating:
- Not req
## [2.4.12] - 2020-01-26
### Version release to fix minor Bugs:
- Fix for multiple looping issue

### Impact on existing integrations:
- The new update will not affect current integration.

### Required changes for existing integrations before updating:
- Not req
## [2.4.11] - 2020-01-14
### Version release to fix minor Bugs:
- Fix for currency code

### Impact on existing integrations:
- The new update will not affect current integration.

### Required changes for existing integrations before updating:
- Not req
## [2.4.10] - 2020-01-12
### Version release to fix minor Bugs:
- Fix for date field formatting

### Impact on existing integrations:
- The new update will not affect current integration.

### Required changes for existing integrations before updating:
- Not req
## [2.4.9] - 2020-01-05
### Version release to fix minor Bugs:
- SDK serialization handling

### Impact on existing integrations:
- The new update will not affect current integration.

### Required changes for existing integrations before updating:
- Not required
## [2.4.8] - 2019-12-18
### Version release to fix minor Bugs:
- Allow hosting app to pass language to SDK through setting up SDK Session.
- Disable SDK UI click actions if user clicks pay button
- Send device information along with request header to track devices that has issues with our SDK UI and functionality
- Minor fixes of arabic Ui.
- Considers Supported currency if transaction currency is not supported

### Impact on existing integrations:
- The new update will not affect current integration.

### Required changes for existing integrations before updating:
-The hosting app that integrates with SDK has to pass local directly after configuring SDK.

### Recommended changes for existing integrations before updating:
- Merchant needs to set language as arabic in main activty .
  By default it  will consider it as english.

## [2.4.7] - 2019-11-21
### Minor version release to validate ThemeObject:
- If Merchant doesnot configure ThemeObject then it will take default.
- SDK will validate for customized Theme Object.

### Impact on existing integrations:
- The update does not impact existing integrations.

### Required changes for existing integrations before updating:
- None.

### Recommended changes for existing integrations before updating:
- None.

## [2.4.3] - 2019-10-26
### Minor version release to make fix mobile configuration change:
- Merchant has to force portrait mode for the activity that launches SDK.
- SDK internally force portrait orientation for its own activities.

### Impact on existing integrations:
- The update does not impact existing integrations.

### Required changes for existing integrations before updating:
- Double-check that activity that launches SDK is Portrait mode.

### Recommended changes for existing integrations before updating:
- None.