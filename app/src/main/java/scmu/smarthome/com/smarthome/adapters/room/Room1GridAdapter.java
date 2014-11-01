package scmu.smarthome.com.smarthome.adapters.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import scmu.smarthome.com.smarthome.R;

public class Room1GridAdapter extends BaseAdapter {
    private Context mContext;

    public Room1GridAdapter(Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_switch, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(position == 0)
            viewHolder.name.setText(R.string.type_1);
        else if(position == 1)
            viewHolder.name.setText(R.string.type_2_single);
        else if(position == 2)
            viewHolder.name.setText(R.string.type_3_single);
        else if(position == 3)
            viewHolder.name.setText(R.string.type_4);
        else if(position == 4)
            viewHolder.name.setText(R.string.type_5);

        return convertView;
    }

    static class ViewHolder {

        TextView name;
    }
}
