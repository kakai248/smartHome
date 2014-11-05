package scmu.smarthome.com.smarthome.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {

    private final static String DBM_INTERVAL = "scmu.smarthome.com.smarthome.dbminterval";

    private static SharedPreferences get(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static SharedPreferences.Editor edit(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    public static String getDbmInterval(Context context) {
        return get(context).getString(DBM_INTERVAL, null);
    }

    public static void saveDbmInterval(Context context, String dbmInterval) {
        edit(context).putString(DBM_INTERVAL, dbmInterval).commit();
    }

}
