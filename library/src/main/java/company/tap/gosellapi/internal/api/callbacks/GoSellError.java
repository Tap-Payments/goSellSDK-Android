package company.tap.gosellapi.internal.api.callbacks;

import com.google.gson.JsonObject;

import android.support.annotation.RestrictTo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Model for response errors
 * <br>
 * If {@link #getThrowable()} returns null, then {@link #getErrorCode()} and {@link #getErrorBody()} will give you an explanation about error.
 * <br>
 * And vice versa, if {@link #getErrorCode()} or {@link #getErrorBody()} return {@link #ERROR_CODE_UNAVAILABLE} and null accordingly, then use {@link #getThrowable()} to obtain error details.
 */

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class GoSellError {
    public static final int ERROR_CODE_UNAVAILABLE = -1;

    private int errorCode;
    private String errorBody;
    private Throwable throwable;

    GoSellError(int errorCode, String errorBody, Throwable throwable) {
        this.errorCode = errorCode;
        this.errorBody = errorBody;
        this.throwable = throwable;
    }


  public String getErrorMessage(){
    String res = "";
    if (errorBody != null && !"".equals(errorBody.trim())) {
      try {
        Object json = new JSONTokener(errorBody).nextValue();
        JSONObject jsonObject = new JSONObject(errorBody);

        if(json instanceof JSONArray){
          JSONArray jsonArray = jsonObject.getJSONArray("errors");
          if(jsonArray!=null && jsonArray.length()>0)
          {
            JSONObject jsonObj =  jsonArray.getJSONObject(0);
            if(jsonObj!=null) res = (String)jsonObj.get("description");
          }
        }else if(json instanceof JSONObject){
          JSONObject jsonObject1 = jsonObject.getJSONObject("error");
          if(jsonObject1!=null) res = (String)jsonObject1.get("description");
        }
      }catch(JSONException errorMessage2){
        errorMessage2.printStackTrace();
      }
    }
    return res;
  }

    /**
     * @return HTTP error response code (4x, 5x)
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @return Json string returned by server, containing error explanation
     */
    public String getErrorBody() {
        return errorBody;
    }

    /**
     * @return {@link Throwable}, returned by connection (timeout, failed to connect etc.)
     */
    public Throwable getThrowable() {
        return throwable;
    }
}
