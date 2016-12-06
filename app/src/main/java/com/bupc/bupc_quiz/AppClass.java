package com.bupc.bupc_quiz;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;


public class AppClass extends Application {

    private static AppClass mInstance;
    public static Context mContext;

    public static synchronized AppClass getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public void toaster(String msg) {
        toaster(msg, false);
    }

    public void toaster(String msg, boolean ifLong) {
        Toast toast = null;

        if (!TextUtils.isEmpty(msg)) {
            if (ifLong) {
                toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            }
        }

        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if (v != null) v.setGravity(Gravity.CENTER);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}
