package company.tap.gosellapi.internal.api.api_service;

final class API_Constants {
    static final String BASE_URL = "https://api.tap.company/v2/";
    static final String AUTH_TOKEN_KEY = "Authorization";
    static final String AUTH_TOKEN_PREFIX = "Bearer ";
    static final String APPLICATION = "Application";
    static final String CONTENT_TYPE_KEY = "content-type";
    static final String CONTENT_TYPE_VALUE = "application/json";
    static final String ACCEPT_KEY = "Accept";
    static final String ACCEPT_VALUE = "application/json";

    //url parts
    static final String INIT = "init";

    static final String TOKEN = "tokens";
    static final String TOKENS = "tokens";
    static final String TOKEN_ID = "token_id";

    static final String AUTHORIZE = "authorize";
    static final String AUTHORIZE_ID = "authorize_id";

    static final String CHARGES = "charges";
    static final String CHARGE_ID = "charge_id";


    static final String BIN = "bin";
    static final String BIN_LOOKUP = "bin_number";

    static final String BILLING_ADDRESS = "billing_address";

    static final String PAYMENT_TYPES = "payment/types";
}