package scmu.smarthome.com.smarthome.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.adapters.ListAdapter;
import scmu.smarthome.com.smarthome.entities.Division;
import scmu.smarthome.com.smarthome.util.GetOverviewTask;

public class OverviewDialogFrament extends DialogFragment implements GetOverviewTask.OnTaskFinishedListener {
    public static final String DIALOG_STATUS_ON = "ON";
    public static final String DIALOG_STATUS_OFF = "OFF";
    private static final String DIALOG_LIGHT = "Luz";
    private static final String DIALOG_AIR_CONDITIONER = "Air Condicionado";
    private static final String DIALOG_WINDOWS = "Janelas";

    private RecyclerView recyclerView;
    private ListAdapter adapter;

    static OverviewDialogFrament newInstance() {
        OverviewDialogFrament fragment = new OverviewDialogFrament();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.listview);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        // set recyclerView LinearLayoutManager manager
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new ListAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        // Run AsyncTask
        GetOverviewTask mOverviewTask = new GetOverviewTask(getActivity(), OverviewDialogFrament.this);
        mOverviewTask.execute();

        return view;
    }

    @Override
    public void onOverviewTaskFinished(Division[] divisions) {

        for(Division division : divisions) {
            adapter.addItem(new DialogItemTitle(division.name));
            adapter.addItem(new DialogItem(DIALOG_LIGHT, division.light.status
                    ? DIALOG_STATUS_ON : DIALOG_STATUS_OFF));

            if(division.airconditioner != null)
                adapter.addItem(new DialogItem(DIALOG_AIR_CONDITIONER, division.airconditioner.status
                        ? DIALOG_STATUS_ON : DIALOG_STATUS_OFF));

            if(division.windows != null)
                adapter.addItem(new DialogItem(DIALOG_WINDOWS, division.windows.status
                        ? DIALOG_STATUS_ON : DIALOG_STATUS_OFF));
        }

    }

    public class DialogItemTitle {
        public String name;

        public DialogItemTitle(String name) {
            this.name = name;
        }
    }

    public class DialogItem extends DialogItemTitle {
        public String status;

        public DialogItem(String name, String status) {
            super(name);
            this.status = status;
        }
    }

}