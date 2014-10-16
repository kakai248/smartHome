package scmu.smarthome.com.smarthome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.ui.NavDrawerActivity.DrawerItem;

public class DrawerAdapter extends ArrayAdapter<Object> {
    private Context mContext;

    public DrawerAdapter(Context context) {
        super(context, 0);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DrawerItem item = (DrawerItem) getItem(position);
        viewHolder.name.setText(item.mTitle);

        return convertView;
    }

    static class ViewHolder {

        TextView name;
    }

}