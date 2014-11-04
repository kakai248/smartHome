package scmu.smarthome.com.smarthome.ui;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import scmu.smarthome.com.smarthome.R;

public class ConfigureActivity extends Activity {

    WifiManager wifi;
    ArrayAdapter<String> wifiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        // set rooms spinner
        Spinner spinner = (Spinner) findViewById(R.id.roomsSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rooms_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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

        // get list of access points found in the scan
        List<String> list = new LinkedList<String>();
        for(ScanResult result : wifi.getScanResults()) {
            list.add(result.level + " dBm | " + result.SSID);
        }

        // sort by dBm
        Collections.sort(list);

        wifiAdapter.clear();
        wifiAdapter.addAll(list);
        wifiAdapter.notifyDataSetChanged();
    }
}
