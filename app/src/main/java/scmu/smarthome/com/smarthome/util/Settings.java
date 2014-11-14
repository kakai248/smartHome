package scmu.smarthome.com.smarthome.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import scmu.smarthome.com.smarthome.R;

public class Settings {
    private static final String DEFAULT_IP = "195.154.70.147:3000";

    private final static String DBM_INTERVAL = "scmu.smarthome.com.smarthome.dbminterval";
    private final static String ROOM0 = "scmu.smarthome.com.smarthome.room0";
    private final static String ROOM1 = "scmu.smarthome.com.smarthome.room1";
    private final static String ROOM2 = "scmu.smarthome.com.smarthome.room2";
    private final static String ROOM3 = "scmu.smarthome.com.smarthome.room3";
    private final static String ROOM4 = "scmu.smarthome.com.smarthome.room4";
    private final static String ROOM5 = "scmu.smarthome.com.smarthome.room5";
    private final static String IP = "scmu.smarthome.com.smarthome.ip";

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

    public static String getRoom(Context context, int room) {
        switch(room) {
            case 0:
                return get(context).getString(ROOM0, context.getString(R.string.configure_no_local_saved));
            case 1:
                return get(context).getString(ROOM1, context.getString(R.string.configure_no_local_saved));
            case 2:
                return get(context).getString(ROOM2, context.getString(R.string.configure_no_local_saved));
            case 3:
                return get(context).getString(ROOM3, context.getString(R.string.configure_no_local_saved));
            case 4:
                return get(context).getString(ROOM4, context.getString(R.string.configure_no_local_saved));
            case 5:
                return get(context).getString(ROOM5, context.getString(R.string.configure_no_local_saved));
            default:
                return "";
        }
    }

    public static void saveRoom(Context context, int room, String value) {
        switch(room) {
            case 0:
                edit(context).putString(ROOM0, value).commit();
                break;
            case 1:
                edit(context).putString(ROOM1, value).commit();
                break;
            case 2:
                edit(context).putString(ROOM2, value).commit();
                break;
            case 3:
                edit(context).putString(ROOM3, value).commit();
                break;
            case 4:
                edit(context).putString(ROOM4, value).commit();
                break;
            case 5:
                edit(context).putString(ROOM5, value).commit();
                break;
            default:
                break;
        }
    }

    public static String getIp(Context context) {
        return get(context).getString(IP, DEFAULT_IP);
    }

    public static void saveIp(Context context, String ip) {
        edit(context).putString(IP, ip).commit();
    }
}
