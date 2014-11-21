package scmu.smarthome.com.smarthome.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.entities.WifiHotSpot;
import scmu.smarthome.com.smarthome.util.Settings;
import scmu.smarthome.com.smarthome.util.Utils;

public class LocationConfigureActivity extends Activity implements View.OnClickListener {
    ArrayAdapter<String> wifiAdapter;

    int roomSelected = 1;
    List<WifiHotSpot> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_configure);

        // set onClickListener to the buttons
        findViewById(R.id.button_save_interval).setOnClickListener(this);
        findViewById(R.id.button_save_local).setOnClickListener(this);
        findViewById(R.id.button_clear_local).setOnClickListener(this);
        findViewById(R.id.button_info).setOnClickListener(this);
        findViewById(R.id.button_find_place).setOnClickListener(this);

        // if exist, set the dbmInterval value
        int dbmInterval = Settings.getDbmInterval(this);
        ((EditText)findViewById(R.id.interval)).setText(dbmInterval+"");

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
                    roomSelected = 0;
                else if(selected.compareTo(getString(R.string.room_2)) == 0)
                    roomSelected = 1;
                else if(selected.compareTo(getString(R.string.room_3)) == 0)
                    roomSelected = 2;
                else if(selected.compareTo(getString(R.string.room_4)) == 0)
                    roomSelected = 3;
                else if(selected.compareTo(getString(R.string.room_5)) == 0)
                    roomSelected = 4;
                else if(selected.compareTo(getString(R.string.room_6)) == 0)
                    roomSelected = 5;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // shouldnt happen
            }
        });

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

        // clear adapter
        wifiAdapter.clear();

        // get list of access points found in the scan
        list = Utils.getWifiList(this);

        // add items to adapter
        for(WifiHotSpot hotSpot : list)
            wifiAdapter.add(hotSpot.toString());

        wifiAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()) {
           case R.id.button_save_interval :
               Utils.saveInterval(this);
               break;
           case R.id.button_save_local :
               Utils.saveRoom(this, list, roomSelected);
               break;
           case R.id.button_clear_local :
               Utils.clearRoom(this, roomSelected);
               break;
           case R.id.button_info :
               Utils.showRoomInfo(this, roomSelected);
               break;
           case R.id.button_find_place :
               Utils.getPlace(this, list);
               break;
       }
    }

}
