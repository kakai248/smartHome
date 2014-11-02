package scmu.smarthome.com.smarthome.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.adapters.GridAdapter;

public class DrawerFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridview, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridview);

        Bundle args = getArguments();
        char mode = args.getChar("mode", 'r');
        int position = args.getInt("position", 0);

        GridAdapter adapter = new GridAdapter(getActivity());

        // type mode
        if(mode == 'r') {
            if(position == 0 || position == 1 || position == 2 || position == 3) {
                adapter.add( new GridSwitch(getString(R.string.type_1), false) );
                adapter.add( new GridSwitch(getString(R.string.type_2_single), false) );
                adapter.add( new GridSeekbar(getString(R.string.type_3_single), false, 10) );
                adapter.add( new GridSwitch(getString(R.string.type_4), false) );
                adapter.add( new GridSeekbar(getString(R.string.type_5), false, 20) );
            }
            else if(position == 4 || position == 5) {
                adapter.add( new GridSwitch(getString(R.string.type_1), false) );
                adapter.add( new GridSwitch(getString(R.string.type_2_single), false) );
                adapter.add( new GridSwitch(getString(R.string.type_4), false) );
                adapter.add( new GridSeekbar(getString(R.string.type_5), false, 20) );
            }
        }

        // room mode
        else {
            if(position == 0 || position == 1 || position == 3) {
                adapter.add( new GridSwitch(getString(R.string.room_1), false) );
                adapter.add( new GridSwitch(getString(R.string.room_2), false) );
                adapter.add( new GridSwitch(getString(R.string.room_3), false) );
                adapter.add( new GridSwitch(getString(R.string.room_4), false) );
                adapter.add( new GridSwitch(getString(R.string.room_5), false) );
                adapter.add( new GridSwitch(getString(R.string.room_6), false) );
            }
            else if(position == 2) {
                adapter.add( new GridSeekbar(getString(R.string.room_1), false, 20) );
                adapter.add( new GridSeekbar(getString(R.string.room_2), false, 20) );
                adapter.add( new GridSeekbar(getString(R.string.room_3), false, 20) );
                adapter.add( new GridSeekbar(getString(R.string.room_4), false, 20) );
            }
            else if(position == 4) {
                adapter.add( new GridSeekbar(getString(R.string.room_1), false, 20) );
                adapter.add( new GridSeekbar(getString(R.string.room_2), false, 20) );
                adapter.add( new GridSeekbar(getString(R.string.room_3), false, 20) );
                adapter.add( new GridSeekbar(getString(R.string.room_4), false, 20) );
                adapter.add( new GridSeekbar(getString(R.string.room_5), false, 20) );
                adapter.add( new GridSeekbar(getString(R.string.room_6), false, 20) );
            }
        }

        gridView.setAdapter(adapter);
        return view;
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
