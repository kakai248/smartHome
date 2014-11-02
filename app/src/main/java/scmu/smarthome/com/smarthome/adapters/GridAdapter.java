package scmu.smarthome.com.smarthome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.ui.DrawerFragment;

public class GridAdapter extends ArrayAdapter<Object> {
    private static final int VIEW_TYPE_SWITCH = 0;
    private static final int VIEW_TYPE_SEEKBAR = 1;

    private Context mContext;

    public GridAdapter(Context context) {
        super(context, 0);
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position) instanceof DrawerFragment.GridSeekbar)
            return VIEW_TYPE_SEEKBAR;

        return VIEW_TYPE_SWITCH;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if(convertView == null) {
            if(getItemViewType(position) == VIEW_TYPE_SWITCH)
                convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_switch, parent, false);

            else if(getItemViewType(position) == VIEW_TYPE_SEEKBAR) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_seekbar, parent, false);
                viewHolder.seekBar = (SeekBar) convertView.findViewById(R.id.seekBar);
            }

            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.status = (Switch) convertView.findViewById(R.id.switch1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(getItemViewType(position) == VIEW_TYPE_SWITCH) {
            DrawerFragment.GridSwitch item = (DrawerFragment.GridSwitch) getItem(position);

            viewHolder.title.setText(item.mTitle);
            viewHolder.status.setChecked(item.mStatus);
        }
        else {
            DrawerFragment.GridSeekbar item = (DrawerFragment.GridSeekbar) getItem(position);

            viewHolder.title.setText(item.mTitle);
            viewHolder.status.setChecked(item.mStatus);
            viewHolder.seekBar.setProgress(item.mVolume);
        }

        return convertView;
    }

    static class ViewHolder {

        TextView title;
        Switch status;
        SeekBar seekBar;
    }
}
