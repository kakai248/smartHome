package scmu.smarthome.com.smarthome.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.ui.DrawerFragment;
import scmu.smarthome.com.smarthome.util.SetHomeStatusTask;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private static final int VIEW_TYPE_SWITCH = 0;
    private static final int VIEW_TYPE_SEEKBAR = 1;

    private Context mContext;
    private List<DrawerFragment.GridSwitch> mItems;

    public GridAdapter(Context context) {
        super();
        mContext = context;
        mItems = new LinkedList<DrawerFragment.GridSwitch>();
    }

    public void addItem(DrawerFragment.GridSwitch item) {
        mItems.add(item);
        notifyItemInserted(mItems.size()-1);
    }

    public void clear() {
        mItems.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view;
        if(viewType == VIEW_TYPE_SEEKBAR)
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_item_seekbar, parent, false);
        else
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_item_switch, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.title.setText(mItems.get(position).mTitle);

        viewHolder.status.setChecked(mItems.get(position).mStatus);
        viewHolder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("status -> " + isChecked);

                String value = isChecked ? "true" : "false";

                // Run AsyncTask
                SetHomeStatusTask mHomeStatusTask = new SetHomeStatusTask(mContext);
                mHomeStatusTask.execute(mItems.get(position).mRoom, mItems.get(position).mDevice, "status", value);
            }
        });

        if(getItemViewType(position) == VIEW_TYPE_SEEKBAR) {
            viewHolder.seekBar.setProgress(((DrawerFragment.GridSeekbar)mItems.get(position)).mVolume);
            viewHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    System.out.println("seekbar -> " + seekBar.getProgress());

                    String value = "" + seekBar.getProgress();

                    // Run AsyncTask
                    SetHomeStatusTask mHomeStatusTask = new SetHomeStatusTask(mContext);
                    mHomeStatusTask.execute(mItems.get(position).mRoom, mItems.get(position).mDevice, "volume", value);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mItems.get(position) instanceof DrawerFragment.GridSeekbar)
            return VIEW_TYPE_SEEKBAR;

        return VIEW_TYPE_SWITCH;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        Switch status;
        SeekBar seekBar;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            status = (Switch) view.findViewById(R.id.switch1);
            seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        }
    }
}
