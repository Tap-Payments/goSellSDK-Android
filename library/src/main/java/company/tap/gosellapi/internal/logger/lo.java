package company.tap.gosellapi.internal.logger;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * The type Lo.
 */
public class lo {
    private static boolean isDebuggable;

    /**
     * Init.
     *
     * @param c the c
     */
    public static void init(Context c){
        isDebuggable = (0 != (c.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
    }

    /**
     * G.
     *
     * @param message the message
     */
    public static void g(String message){
        if(isDebuggable){
            String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
            Log.d("goTap_logger: " + className + "." + methodName + "():" + lineNumber, (message == null ? "" : message));
        }
    }

    /**
     * Log request response time.
     *
     * @param operation the operation
     */
    public static void logRequestResponseTime(String operation){
        Log.d(operation +": ", System.nanoTime()+"");
    }
}
