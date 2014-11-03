package scmu.smarthome.com.smarthome.ui;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import scmu.smarthome.com.smarthome.R;

public class WifiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        getLocation();
    }


    public void getLocation() {
        WifiManager wifi= (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi.startScan();

        // get list of access points found in the scan
        List<String> list = new LinkedList<String>();
        for(ScanResult result : wifi.getScanResults()) {
            list.add(result.SSID + " , " + result.level  + "dBm");
        }

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}
