package scmu.smarthome.com.smarthome.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.entities.WifiHotSpot;
import scmu.smarthome.com.smarthome.util.Settings;

public class ConfigureActivity extends Activity implements View.OnClickListener {
    public static final int MAX_SAVED_WIFI = 3;

    WifiManager wifi;
    ArrayAdapter<String> wifiAdapter;

    int roomSelected = 1;
    List<WifiHotSpot> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        // set onClickListener to the buttons
        findViewById(R.id.button_save_interval).setOnClickListener(this);
        findViewById(R.id.button_save_local).setOnClickListener(this);
        findViewById(R.id.button_clear_local).setOnClickListener(this);
        findViewById(R.id.button_info).setOnClickListener(this);
        findViewById(R.id.button_find_place).setOnClickListener(this);

        // if exist, set the dbmInterval value
        String dbmInterval = Settings.getDbmInterval(this);
        if(dbmInterval != null)
            ((EditText)findViewById(R.id.interval)).setText(dbmInterval);

        // set rooms spinner
        Spinner spinner = (Spinner) findViewById(R.id.roomsSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.rooms_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // set rooms spinner handler
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);

                if(selected.compareTo(getString(R.string.room_1)) == 0)
                    roomSelected = 1;
                else if(selected.compareTo(getString(R.string.room_2)) == 0)
                    roomSelected = 2;
                else if(selected.compareTo(getString(R.string.room_3)) == 0)
                    roomSelected = 3;
                else if(selected.compareTo(getString(R.string.room_4)) == 0)
                    roomSelected = 4;
                else if(selected.compareTo(getString(R.string.room_5)) == 0)
                    roomSelected = 5;
                else if(selected.compareTo(getString(R.string.room_6)) == 0)
                    roomSelected = 6;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // shouldnt happen
            }
        });

        // wifi
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(wifiAdapter);

        // set timed updater
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateWifiList();
                wifiAdapter.notifyDataSetChanged();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    public void updateWifiList() {
        wifi.startScan();

        // clear adapter
        wifiAdapter.clear();

        // get list of access points found in the scan
        list = new LinkedList<WifiHotSpot>();
        for(ScanResult result : wifi.getScanResults()) {
            WifiHotSpot hotSpot = new WifiHotSpot(result.SSID, result.BSSID, result.level);
            list.add(hotSpot);
        }

        // sort by dBm
        Collections.sort(list);

        // add items to adapter
        for(WifiHotSpot hotSpot : list)
            wifiAdapter.add(hotSpot.toString());

        wifiAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()) {
           case R.id.button_save_interval :
               saveInterval();
               break;
           case R.id.button_save_local :
               saveRoom();
               break;
           case R.id.button_clear_local :
               clearRoom();
               break;
           case R.id.button_info :
               showRoomInfo();
               break;
           case R.id.button_find_place :
               findPlace();
               break;
       }
    }

    private void saveInterval() {
        String interval = ((EditText)findViewById(R.id.interval)).getText().toString();

        // save new interval to sharedPreferences
        Settings.saveDbmInterval(this, interval);

        Toast.makeText(this, getString(R.string.configure_interval_saved) + " " + interval, Toast.LENGTH_SHORT).show();
    }

    private void saveRoom() {
        String local = "";

        for(int i = 0; i < Math.min(MAX_SAVED_WIFI, list.size()); i++) {
            WifiHotSpot item = list.get(i);
            if(!local.isEmpty())
                local += ";";

            local += item.getLevel() + "#" + item.getMac();
        }

        // save new room local to sharedPreferences
        Settings.saveRoom(this, roomSelected, local);

        Toast.makeText(this, getString(R.string.configure_room_saved) + " " + roomSelected, Toast.LENGTH_SHORT).show();
    }

    private void clearRoom() {
        // save new room local to sharedPreferences
        Settings.saveRoom(this, roomSelected, getString(R.string.configure_no_local_saved));

        Toast.makeText(this, getString(R.string.configure_room_cleared) + " " + roomSelected, Toast.LENGTH_SHORT).show();
    }

    private void showRoomInfo() {
        String title = "";

        if(roomSelected == 1)
            title = getString(R.string.room_1);
        else if(roomSelected == 2)
            title = getString(R.string.room_2);
        else if(roomSelected == 3)
            title = getString(R.string.room_3);
        else if(roomSelected == 4)
            title = getString(R.string.room_4);
        else if(roomSelected == 5)
            title = getString(R.string.room_5);
        else if(roomSelected == 6)
            title = getString(R.string.room_6);

        String message = Settings.getRoom(this, roomSelected);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title);
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void findPlace() {
        // obtain interval for the comparison
        int interval = Integer.parseInt(Settings.getDbmInterval(this));

        // for each room
        for(int room = 1; room <= 6; room++) {
            String roomLocal = Settings.getRoom(this, room);

            // if this local is not saved
            if(roomLocal.compareTo(getString(R.string.configure_no_local_saved)) == 0)
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
                Toast.makeText(this, getString(R.string.configure_current_local) + " " + room, Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // not a place we have saved
        Toast.makeText(this, getString(R.string.configure_unknown_place), Toast.LENGTH_SHORT).show();
    }
}
