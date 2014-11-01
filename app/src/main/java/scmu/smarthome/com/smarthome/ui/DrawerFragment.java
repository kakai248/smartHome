package scmu.smarthome.com.smarthome.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.adapters.room.Room1GridAdapter;
import scmu.smarthome.com.smarthome.adapters.room.Room2GridAdapter;
import scmu.smarthome.com.smarthome.adapters.room.Room3GridAdapter;
import scmu.smarthome.com.smarthome.adapters.room.Room4GridAdapter;
import scmu.smarthome.com.smarthome.adapters.room.Room5GridAdapter;
import scmu.smarthome.com.smarthome.adapters.room.Room6GridAdapter;
import scmu.smarthome.com.smarthome.adapters.type.Type1GridAdapter;
import scmu.smarthome.com.smarthome.adapters.type.Type2GridAdapter;
import scmu.smarthome.com.smarthome.adapters.type.Type3GridAdapter;
import scmu.smarthome.com.smarthome.adapters.type.Type4GridAdapter;
import scmu.smarthome.com.smarthome.adapters.type.Type5GridAdapter;

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

        // type mode
        if(mode == 'r') {

            if(position == 0)
                gridView.setAdapter(new Room1GridAdapter(getActivity()));
            else if(position == 1)
                gridView.setAdapter(new Room2GridAdapter(getActivity()));
            else if(position == 2)
                gridView.setAdapter(new Room3GridAdapter(getActivity()));
            else if(position == 3)
                gridView.setAdapter(new Room4GridAdapter(getActivity()));
            else if(position == 4)
                gridView.setAdapter(new Room5GridAdapter(getActivity()));
            else if(position == 5)
                gridView.setAdapter(new Room6GridAdapter(getActivity()));
        }
        // room mode
        else {
            if(position == 0)
                gridView.setAdapter(new Type1GridAdapter(getActivity()));
            else if(position == 1)
                gridView.setAdapter(new Type2GridAdapter(getActivity()));
            else if(position == 2)
                gridView.setAdapter(new Type3GridAdapter(getActivity()));
            else if(position == 3)
                gridView.setAdapter(new Type4GridAdapter(getActivity()));
            else if(position == 4)
                gridView.setAdapter(new Type5GridAdapter(getActivity()));
        }

        return view;
    }

}
