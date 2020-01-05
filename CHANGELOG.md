# CHANGELOG
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