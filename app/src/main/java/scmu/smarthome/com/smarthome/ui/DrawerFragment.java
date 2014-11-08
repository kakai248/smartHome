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
import scmu.smarthome.com.smarthome.entities.Division;
import scmu.smarthome.com.smarthome.util.GetHomeStatusTask;

public class DrawerFragment extends Fragment implements GetHomeStatusTask.OnTaskFinishedListener {

    private RecyclerView recyclerView;
    private String selectedItem;
    private boolean showDivisions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        selectedItem = args.getString("item_selected", "");
        showDivisions = args.getBoolean("show_divisions", false);

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
        mHomeStatusTask.execute(selectedItem, showDivisions);

        return view;
    }

    @Override
    public void onHomeStatusTaskFinished(Object result) {
        GridAdapter adapter = new GridAdapter(getActivity());

        if(showDivisions) {
            Division division = (Division) result;
            adapter.addItem( new GridSwitch(getString(R.string.type_1), division.light.status) );

            if(division.airconditioner != null)
                adapter.addItem( new GridSwitch(getString(R.string.type_2_single), division.airconditioner.status) );

            if(division.tv != null)
                adapter.addItem( new GridSeekbar(getString(R.string.type_3_single), division.tv.status, division.tv.volume) );

            if(division.windows != null)
                adapter.addItem( new GridSwitch(getString(R.string.type_4), division.windows.status) );

            if(division.soundsystem != null)
                adapter.addItem( new GridSeekbar(getString(R.string.type_5), division.soundsystem.status, division.soundsystem.volume) );
        }
        else {
            Division divisions[] = (Division[]) result;

            for(Division division : divisions) {

                if(division.light != null)
                    adapter.addItem( new GridSwitch(getString(R.string.type_1) +
                            " - " + division.name,division.light.status) );

                if(division.airconditioner != null)
                    adapter.addItem( new GridSwitch(getString(R.string.type_2_single) +
                            " - " + division.name, division.airconditioner.status) );

                if(division.tv != null)
                    adapter.addItem( new GridSeekbar(getString(R.string.type_3_single) +
                            " - " + division.name, division.tv.status, division.tv.volume) );

                if(division.windows != null)
                    adapter.addItem( new GridSwitch(getString(R.string.type_4) +
                            " - " + division.name, division.windows.status) );

                if(division.soundsystem != null)
                    adapter.addItem( new GridSeekbar(getString(R.string.type_5) +
                            " - " + division.name, division.soundsystem.status, division.soundsystem.volume) );
            }
        }

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
