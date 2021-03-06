package scmu.smarthome.com.smarthome.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.adapters.GridAdapter;
import scmu.smarthome.com.smarthome.entities.Division;
import scmu.smarthome.com.smarthome.util.GetHomeStatusTask;
import scmu.smarthome.com.smarthome.util.Settings;

public class DrawerFragment extends Fragment implements GetHomeStatusTask.OnTaskFinishedListener {

    private RecyclerView recyclerView;
    private GridAdapter adapter;
    private String selectedItem;
    private boolean showDivisions;

    private Handler fragmentHandler;
    private Runnable fragmentRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        selectedItem = args.getString("item_selected", "");
        showDivisions = args.getBoolean("show_divisions", false);

        // initialize fragment refresher handler
        fragmentHandler = new Handler();

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.gridview);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                getResources().getInteger(R.integer.num_columns),
                StaggeredGridLayoutManager.VERTICAL);

        // set items margins
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration();
        recyclerView.addItemDecoration(itemDecoration);

        // set recyclerView StaggeredGridLayout manager
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GridAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        getHomeStatus();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // setup auto fragment refresher
        setupFragmentRefresher();
    }

    @Override
    public void onPause() {
        super.onPause();

        // stop fragment auto refresher
        if(fragmentRunnable != null)
            fragmentHandler.removeCallbacks(fragmentRunnable);
    }

    public void getHomeStatus() {
        // Run AsyncTask
        GetHomeStatusTask mHomeStatusTask = new GetHomeStatusTask(getActivity(), DrawerFragment.this);
        mHomeStatusTask.execute(selectedItem, showDivisions);
    }

    private void setupFragmentRefresher() {
        final int fragmentRefreshRate = Settings.getFragmentRefreshRate(getActivity());

        // set timed updater
        fragmentRunnable = new Runnable() {
            @Override
            public void run() {
                getHomeStatus();
                fragmentHandler.postDelayed(this, fragmentRefreshRate * 1000);
            }
        };

        fragmentHandler.postDelayed(fragmentRunnable, fragmentRefreshRate * 1000);
    }

    @Override
    public void onHomeStatusTaskFinished(Object result) {
        if(result == null)
            return;

        adapter.clear();

        if(showDivisions) {
            Division division = (Division) result;

            if(division.light != null)
                adapter.addItem( new GridSwitch(selectedItem,
                        getActivity().getResources().getStringArray(R.array.json_devices_array)[0],
                        getString(R.string.type_1), division.light.status) );

            if(division.airconditioner != null)
                adapter.addItem( new GridSwitch(selectedItem,
                        getActivity().getResources().getStringArray(R.array.json_devices_array)[1],
                        getString(R.string.type_2_single), division.airconditioner.status) );

            if(division.tv != null)
                adapter.addItem( new GridSeekbar(selectedItem,
                        getActivity().getResources().getStringArray(R.array.json_devices_array)[2],
                        getString(R.string.type_3_single), division.tv.status, division.tv.volume) );

            if(division.windows != null)
                adapter.addItem( new GridSwitch(selectedItem,
                        getActivity().getResources().getStringArray(R.array.json_devices_array)[3],
                        getString(R.string.type_4), division.windows.status) );

            if(division.soundsystem != null)
                adapter.addItem( new GridSeekbar(selectedItem,
                        getActivity().getResources().getStringArray(R.array.json_devices_array)[4],
                        getString(R.string.type_5), division.soundsystem.status, division.soundsystem.volume) );
        }
        else {
            Division divisions[] = (Division[]) result;

            for(Division division : divisions) {

                if(division.light != null)
                    adapter.addItem( new GridSwitch(division.division,
                            getActivity().getResources().getStringArray(R.array.json_devices_array)[0],
                            getString(R.string.type_1) + " - " + division.name,
                            division.light.status) );

                if(division.airconditioner != null)
                    adapter.addItem( new GridSwitch(division.division,
                            getActivity().getResources().getStringArray(R.array.json_devices_array)[1],
                            getString(R.string.type_2_single) + " - " + division.name,
                            division.airconditioner.status) );

                if(division.tv != null)
                    adapter.addItem( new GridSeekbar(division.division,
                            getActivity().getResources().getStringArray(R.array.json_devices_array)[2],
                            getString(R.string.type_3_single) + " - " + division.name,
                            division.tv.status, division.tv.volume) );

                if(division.windows != null)
                    adapter.addItem( new GridSwitch(division.division,
                            getActivity().getResources().getStringArray(R.array.json_devices_array)[3],
                            getString(R.string.type_4) + " - " + division.name,
                            division.windows.status) );

                if(division.soundsystem != null)
                    adapter.addItem( new GridSeekbar(division.division,
                            getActivity().getResources().getStringArray(R.array.json_devices_array)[4],
                            getString(R.string.type_5) + " - " + division.name,
                            division.soundsystem.status, division.soundsystem.volume) );
            }
        }

        adapter.notifyDataSetChanged();
    }

    public class GridSwitch {
        public String mRoom;
        public String mDevice;
        public String mTitle;
        public boolean mStatus;

        public GridSwitch(String room, String device, String title, boolean status) {
            mRoom = room;
            mDevice = device;
            mTitle = title;
            mStatus = status;
        }
    }

    public class GridSeekbar extends GridSwitch {
        public int mVolume;

        public GridSeekbar(String room, String device, String title, boolean status, int volume) {
            super(room, device, title, status);
            mVolume = volume;
        }
    }

}
