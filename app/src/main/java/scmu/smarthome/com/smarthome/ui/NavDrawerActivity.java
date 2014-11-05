package scmu.smarthome.com.smarthome.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.adapters.DrawerAdapter;

public class NavDrawerActivity extends Activity implements AdapterView.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerAdapter drawerAdapter;

    private boolean showHouseDivisions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        showHouseDivisions = true;

        setupNavDrawer();

        // Set switch listener
        ((Switch) findViewById(R.id.switch1))
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

            // tell fragment which adapter to load
            Bundle args = new Bundle();
            args.putString("mode", showHouseDivisions ? "r" : "t");
            args.putString("position", "0");
            fragment.setArguments(args);

            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    public void setupNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setFocusableInTouchMode(false);

        // Setup menu adapter
        drawerAdapter = new DrawerAdapter(this);
        switchDrawerItemsList();

        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(drawerAdapter);
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

        // Check which mode are we on
        // type mode
        if(showHouseDivisions) {
            // tell fragment which adapter to load
            Bundle args = new Bundle();
            args.putString("mode", "r");
            args.putString("position", String.valueOf(position));
            fragment.setArguments(args);
        }
        // room mode
        else {
            // tell fragment which adapter to load
            Bundle args = new Bundle();
            args.putString("mode", "t");
            args.putString("position", String.valueOf(position));
            fragment.setArguments(args);
        }

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
            case R.id.configure :
                startActivity(new Intent(this, ConfigureActivity.class));
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

    public class DrawerItem {
        public String mTitle;

        public DrawerItem(String title) {
            mTitle = title;
        }
    }

}
