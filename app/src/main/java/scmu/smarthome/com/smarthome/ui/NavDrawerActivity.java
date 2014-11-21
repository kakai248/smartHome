package scmu.smarthome.com.smarthome.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.List;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.adapters.DrawerAdapter;
import scmu.smarthome.com.smarthome.util.Settings;
import scmu.smarthome.com.smarthome.util.Utils;

public class NavDrawerActivity extends Activity implements AdapterView.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerAdapter drawerAdapter;

    private boolean showHouseDivisions;
    private ListView mDrawerList;
    private Switch switch1;

    private Handler handler;
    private Runnable run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        showHouseDivisions = true;

        // initialize auto location handler
        handler = new Handler();

        setupNavDrawer();

        // Set switch listener
        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showHouseDivisions = !isChecked;
                switchDrawerItemsList();
            }
        });

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);

        // Sets default fragment (its supposed to be item 0 on list)
        if (fragment == null) {
            fragment = new DrawerFragment();
            Bundle args = new Bundle();

            if(showHouseDivisions) {
                // Get selected room
                String selectedRoom = getResources().getStringArray(R.array.json_rooms_array)[0];

                args.putString("item_selected", selectedRoom);
            }
            else {
                // Get selected device
                String selectedDevice = getResources().getStringArray(R.array.json_devices_array)[0];

                args.putString("item_selected", selectedDevice);
            }

            args.putBoolean("show_divisions", showHouseDivisions);
            fragment.setArguments(args);
            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // setup location auto refresher
        setupLocationAutoRefresher();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(run == null)
            return;
        handler.removeCallbacks(run);
    }

    private void setupLocationAutoRefresher() {
        boolean autoLocation = Settings.getAutoLocation(this);

        // refresh auto is off, we do nothing
        if(!autoLocation)
            return;

        final int autoLocationRefreshRate = Settings.getAutoLocationRefreshRate(this);

        // set timed updater
        run = new Runnable() {
            @Override
            public void run() {
                updateRoom();
                handler.postDelayed(this, autoLocationRefreshRate * 1000);
            }
        };

        handler.postDelayed(run, autoLocationRefreshRate * 1000);
    }

    private static final int SPEECH_REQUEST_CODE = 0;

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            for(String i : results)
                System.out.println("text: " + i);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setupNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setFocusableInTouchMode(false);

        // Setup menu adapter
        drawerAdapter = new DrawerAdapter(this);
        switchDrawerItemsList();

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mDrawerList.setItemChecked(0, true);
        mDrawerList.setOnItemClickListener(this);

        // If the device is bigger than 7', don't open the drawer
        if(! getResources().getBoolean(R.bool.drawer_opened)) {

            // setup drawer indicator
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                    R.string.drawer_open, R.string.drawer_close) {
                public void onDrawerClosed(View view) {
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }

                public void onDrawerOpened(View drawerView) {
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }
    }

    private void switchDrawerItemsList() {
        drawerAdapter.clear();

        if(showHouseDivisions) {
            drawerAdapter.add(new DrawerItem(getString(R.string.room_1)));
            drawerAdapter.add(new DrawerItem(getString(R.string.room_2)));
            drawerAdapter.add(new DrawerItem(getString(R.string.room_3)));
            drawerAdapter.add(new DrawerItem(getString(R.string.room_4)));
            drawerAdapter.add(new DrawerItem(getString(R.string.room_5)));
            drawerAdapter.add(new DrawerItem(getString(R.string.room_6)));
        }
        else {
            drawerAdapter.add(new DrawerItem(getString(R.string.type_1)));
            drawerAdapter.add(new DrawerItem(getString(R.string.type_2)));
            drawerAdapter.add(new DrawerItem(getString(R.string.type_3)));
            drawerAdapter.add(new DrawerItem(getString(R.string.type_4)));
            drawerAdapter.add(new DrawerItem(getString(R.string.type_5)));
        }

        drawerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // If the device is bigger than 7', lock the drawer
        if(getResources().getBoolean(R.bool.drawer_opened)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }
        else {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // Sync the toggle state after onRestoreInstanceState has occurred.
            mDrawerToggle.syncState();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        if(getResources().getBoolean(R.bool.drawer_opened) ||
                !mDrawerLayout.isDrawerOpen(GravityCompat.START))
            finish();
        else
            mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = new DrawerFragment();

        Bundle args = new Bundle();
        if(showHouseDivisions) {
            // Get selected room
            String selectedRoom = getResources().getStringArray(R.array.json_rooms_array)[position];

            args.putString("item_selected", selectedRoom);
        }
        else {
            // Get selected device
            String selectedDevice = getResources().getStringArray(R.array.json_devices_array)[position];

            args.putString("item_selected", selectedDevice);
        }

        args.putBoolean("show_divisions", showHouseDivisions);
        fragment.setArguments(args);

        // Replace fragment and close drawer
        fm.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // If the device is bigger than 7', don't close the drawer
        if(! getResources().getBoolean(R.bool.drawer_opened))
            mDrawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.mic :
                displaySpeechRecognizer();
                return true;

            case R.id.myPlace :
                updateRoom();
                return true;

            case R.id.configure :
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default :
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_nav_drawer, menu);
        return true;
    }

    private void updateRoom() {
        int place = Utils.getPlace(this, Utils.getWifiList(this));

        if(place != -1) {

            if(switch1.isChecked())
                switch1.setChecked(false);

            mDrawerList.performItemClick(
                    mDrawerList.getChildAt(place),
                    place,
                    mDrawerList.getAdapter().getItemId(place));
        }
    }

    public class DrawerItem {
        public String mTitle;

        public DrawerItem(String title) {
            mTitle = title;
        }
    }

}
