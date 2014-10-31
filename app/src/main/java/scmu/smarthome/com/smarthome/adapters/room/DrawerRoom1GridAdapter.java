package scmu.smarthome.com.smarthome.adapters.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import scmu.smarthome.com.smarthome.R;

public class DrawerRoom1GridAdapter extends BaseAdapter {
    private Context mContext;

    public DrawerRoom1GridAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(position == 0)
            viewHolder.name.setText(R.string.drawer_room_1_lights);
        else if(position == 1)
            viewHolder.name.setText(R.string.drawer_room_1_ac);
        else if(position == 2)
            viewHolder.name.setText(R.string.drawer_room_1_tv);
        else if(position == 3)
            viewHolder.name.setText(R.string.drawer_room_1_windows);
        else if(position == 4)
            viewHolder.name.setText(R.string.drawer_room_1_sound);

        return convertView;
    }

    static class ViewHolder {

        TextView name;
    }
}
