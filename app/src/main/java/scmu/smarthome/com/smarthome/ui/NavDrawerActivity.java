package scmu.smarthome.com.smarthome.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        setupNavDrawer();

        // Set switch listener
        ((Switch) findViewById(R.id.switch1))
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                    switchDrawerItemsList(false);
                else
                    switchDrawerItemsList(true);
            }
        });

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = new ExampleFragment();
            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    public void setupNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setFocusableInTouchMode(false);

        // Setup menu adapter
        drawerAdapter = new DrawerAdapter(this);
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_1)));
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_2)));
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_3)));
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_4)));
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_5)));
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_6)));

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

    private void switchDrawerItemsList(boolean showHouseDivisions) {
        drawerAdapter.clear();

        if(showHouseDivisions) {
            drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_1)));
            drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_2)));
            drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_3)));
            drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_4)));
            drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_5)));
            drawerAdapter.add(new DrawerItem(getString(R.string.drawer_room_6)));
        }
        else {
            drawerAdapter.add(new DrawerItem(getString(R.string.drawer_type_1)));
            drawerAdapter.add(new DrawerItem(getString(R.string.drawer_type_2)));
            drawerAdapter.add(new DrawerItem(getString(R.string.drawer_type_3)));
            drawerAdapter.add(new DrawerItem(getString(R.string.drawer_type_4)));
            drawerAdapter.add(new DrawerItem(getString(R.string.drawer_type_5)));
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

        Fragment fragment = null;

        // Check which mode are we on
        // type mode
        if(((Switch) findViewById(R.id.switch1)).isActivated()) {
            if(position == 0)
                fragment = new ExampleFragment();
            else if(position == 1)
                fragment = new ExampleFragment();
            else if(position == 2)
                fragment = new ExampleFragment();
            else if(position == 3)
                fragment = new ExampleFragment();
            else if(position == 4)
                fragment = new ExampleFragment();
        }
        // room mode
        else {
            if(position == 0)
                fragment = new DrawerRoom1Fragment();
            else if(position == 1)
                fragment = new ExampleFragment();
            else if(position == 2)
                fragment = new ExampleFragment();
            else if(position == 3)
                fragment = new ExampleFragment();
            else if(position == 4)
                fragment = new ExampleFragment();
            else if(position == 5)
                fragment = new ExampleFragment();
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class DrawerItem {
        public String mTitle;

        public DrawerItem(String title) {
            mTitle = title;
        }
    }

}
