package scmu.smarthome.com.smarthome.adapters.type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import scmu.smarthome.com.smarthome.R;

public class Type1GridAdapter extends BaseAdapter {
    private Context mContext;

    public Type1GridAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 6;
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
            viewHolder.name.setText(R.string.room_1);
        else if(position == 1)
            viewHolder.name.setText(R.string.room_2);
        else if(position == 2)
            viewHolder.name.setText(R.string.room_3);
        else if(position == 3)
            viewHolder.name.setText(R.string.room_4);
        else if(position == 4)
            viewHolder.name.setText(R.string.room_5);
        else if(position == 5)
            viewHolder.name.setText(R.string.room_6);

        return convertView;
    }

    static class ViewHolder {

        TextView name;
    }
}
