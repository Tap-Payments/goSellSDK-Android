package company.tap.gosellapi.internal.logger;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

public class lo {
    private static boolean isDebuggable;

    public static void init(Context c){
        isDebuggable = (0 != (c.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
    }

    public static void g(String message){
        if(isDebuggable){
            String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
            Log.d("goTap_logger: " + className + "." + methodName + "():" + lineNumber, (message == null ? "" : message));
        }
    }
}
