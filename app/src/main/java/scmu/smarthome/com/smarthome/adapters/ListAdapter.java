package scmu.smarthome.com.smarthome.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

import scmu.smarthome.com.smarthome.R;
import scmu.smarthome.com.smarthome.ui.OverviewDialogFrament;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private static final int VIEW_TYPE_TITLE = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int STATUS_ON = 0xFF99CC00;
    private static final int STATUS_OFF = 0xFFFF4444;

    private Context mContext;
    private LinkedList<OverviewDialogFrament.DialogItemTitle> mItems;

    public ListAdapter(Context context) {
        super();
        mContext = context;
        mItems = new LinkedList<OverviewDialogFrament.DialogItemTitle>();
    }

    public void addItem(OverviewDialogFrament.DialogItemTitle item) {
        mItems.add(item);
        notifyItemInserted(mItems.size()-1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view;
        if(viewType == VIEW_TYPE_TITLE)
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_dialog_title, parent, false);
        else
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_dialog_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.name.setText(mItems.get(position).name);

        if(getItemViewType(position) == VIEW_TYPE_ITEM) {
            OverviewDialogFrament.DialogItem item = ((OverviewDialogFrament.DialogItem) mItems.get(position));
            viewHolder.status.setText(item.status);

            if(item.status.compareTo(OverviewDialogFrament.DIALOG_STATUS_ON) == 0)
                viewHolder.status.setTextColor(STATUS_ON);
            else
                viewHolder.status.setTextColor(STATUS_OFF);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mItems.get(position) instanceof OverviewDialogFrament.DialogItem)
            return VIEW_TYPE_ITEM;

        return VIEW_TYPE_TITLE;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView status;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            status = (TextView) view.findViewById(R.id.status);
        }
    }
}
