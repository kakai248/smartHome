package scmu.smarthome.com.smarthome.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.adapters.GridAdapter;
import scmu.smarthome.com.smarthome.entities.Device;
import scmu.smarthome.com.smarthome.entities.Division;
import scmu.smarthome.com.smarthome.util.GetHomeStatusTask;

public class DrawerFragment extends Fragment implements GetHomeStatusTask.OnTaskFinishedListener {

    private RecyclerView recyclerView;
    private String roomSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        roomSelected = args.getString("room_selected", "livingroom");

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.gridview);

        // set recyclerView StaggeredGridLayout manager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(
                getResources().getInteger(R.integer.num_columns),
                StaggeredGridLayoutManager.VERTICAL));

        // Run AsyncTask
        GetHomeStatusTask mHomeStatusTask = new GetHomeStatusTask(getActivity(),
                DrawerFragment.this);
        mHomeStatusTask.execute(roomSelected);

        return view;
    }

    @Override
    public void onHomeStatusTaskFinished(Device result) {
        GridAdapter adapter = new GridAdapter(getActivity());
        adapter.addItem( new GridSwitch(getString(R.string.type_1), result.light.status) );

        if(result.airconditioner != null)
            adapter.addItem( new GridSwitch(getString(R.string.type_2_single), result.airconditioner.status) );

        if(result.tv != null)
            adapter.addItem( new GridSeekbar(getString(R.string.type_3_single), result.tv.status, result.tv.volume) );

        if(result.windows != null)
            adapter.addItem( new GridSwitch(getString(R.string.type_4), result.windows.status) );

        if(result.soundsystem != null)
            adapter.addItem( new GridSeekbar(getString(R.string.type_5), result.soundsystem.status, result.soundsystem.volume) );

        recyclerView.setAdapter(adapter);
    }

    public class GridSwitch {
        public String mTitle;
        public boolean mStatus;

        public GridSwitch(String title, boolean status) {
            mTitle = title;
            mStatus = status;
        }
    }

    public class GridSeekbar extends GridSwitch {
        public int mVolume;

        public GridSeekbar(String title, boolean status, int volume) {
            super(title, status);
            mVolume = volume;
        }
    }

}
