package scmu.smarthome.com.smarthome.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.entities.WifiHotSpot;

public class Utils {

    public static List<WifiHotSpot> getWifiList(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi.startScan();

        // get list of access points found in the scan
        LinkedList<WifiHotSpot> list = new LinkedList<WifiHotSpot>();
        for(ScanResult result : wifi.getScanResults()) {
            WifiHotSpot hotSpot = new WifiHotSpot(result.SSID, result.BSSID, result.level);
            list.add(hotSpot);
        }

        // sort by dBm
        Collections.sort(list);

        return list;
    }

    public static void saveInterval(Context context) {
        String interval = ((EditText) ((Activity)context).findViewById(R.id.interval)).getText().toString();

        // save new interval to sharedPreferences
        Settings.saveDbmInterval(context, interval);

        Toast.makeText(context, context.getString(R.string.configure_interval_saved) +
                " " + interval, Toast.LENGTH_SHORT).show();
    }

    public static void saveRoom(Context context, List<WifiHotSpot> list, int roomSelected) {
        final int MAX_SAVED_WIFI = 3;
        String local = "";

        for(int i = 0; i < Math.min(MAX_SAVED_WIFI, list.size()); i++) {
            WifiHotSpot item = list.get(i);
            if(!local.isEmpty())
                local += ";";

            local += item.getLevel() + "#" + item.getMac();
        }

        // save new room local to sharedPreferences
        Settings.saveRoom(context, roomSelected, local);

        String roomName = context.getResources().getStringArray(R.array.rooms_array)[roomSelected];

        Toast.makeText(context, context.getString(R.string.configure_room_saved) +
                " " + roomName, Toast.LENGTH_SHORT).show();
    }

    public static void clearRoom(Context context, int roomSelected) {
        // save new room local to sharedPreferences
        Settings.saveRoom(context, roomSelected, context.getString(R.string.configure_no_local_saved));

        Toast.makeText(context, context.getString(R.string.configure_room_cleared) +
                " " + roomSelected, Toast.LENGTH_SHORT).show();
    }

    public static void showRoomInfo(Context context, int roomSelected) {
        String title = "";

        if(roomSelected == 0)
            title = context.getString(R.string.room_1);
        else if(roomSelected == 1)
            title = context.getString(R.string.room_2);
        else if(roomSelected == 2)
            title = context.getString(R.string.room_3);
        else if(roomSelected == 3)
            title = context.getString(R.string.room_4);
        else if(roomSelected == 4)
            title = context.getString(R.string.room_5);
        else if(roomSelected == 5)
            title = context.getString(R.string.room_6);

        String message = Settings.getRoom(context, roomSelected);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title);
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public static int getPlace(Context context, List<WifiHotSpot> list) {
        // obtain interval for the comparison
        int interval = Integer.parseInt(Settings.getDbmInterval(context));

        // for each room
        for(int room = 0; room <= 5; room++) {
            String roomLocal = Settings.getRoom(context, room);

            // if this local is not saved
            if(roomLocal.compareTo(context.getString(R.string.configure_no_local_saved)) == 0)
                continue;

            boolean matches = true;

            System.out.println("----- Room: " + room);

            // process each wifi saved for this room
            String[] roomWifis = roomLocal.split(";");
            for(String savedWifi : roomWifis) {
                String[] wifi = savedWifi.split("#");

                boolean exists = false;

                System.out.println("----- savedWifi: " + savedWifi);

                // for each wifi that we're capturing right now
                for(WifiHotSpot item : list) {
                    int capturedWifiStrength = item.getLevel();
                    String capturedWifiMac = item.getMac();

                    // if its not the same ssid we dont bother
                    if(wifi[1].compareTo(capturedWifiMac) != 0) {
                        continue;
                    }
                    exists = true;

                    System.out.println("----- Captured wifi: " + capturedWifiMac + " - " + capturedWifiStrength);

                    int strength = Integer.parseInt(wifi[0]);

                    // if the captured wifi doesnt match the saved wifi strength
                    if(!(capturedWifiStrength >= strength - interval
                            && capturedWifiStrength <= strength + interval)) {
                        matches = false;
                    }
                    break;
                }

                // we didnt find this wifi in this place
                if(!exists) {
                    matches = false;
                    break;
                }

                // we found it but the strength was wrong, we must be in the wrong room
                if(!matches)
                    break;
            }

            // we found the place!
            if(matches) {
                String roomName = context.getResources().getStringArray(R.array.rooms_array)[room];

                Toast.makeText(context, context.getString(R.string.configure_current_local) +
                        " " + roomName, Toast.LENGTH_SHORT).show();

                return room;
            }
        }

        // not a place we have saved
        Toast.makeText(context, context.getString(R.string.configure_unknown_place), Toast.LENGTH_SHORT).show();

        return -1;
    }

}
