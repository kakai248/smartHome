package scmu.smarthome.com.smarthome.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int DEFAULT_ITEMS_MARGIN = 10;

    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(DEFAULT_ITEMS_MARGIN, DEFAULT_ITEMS_MARGIN,
                DEFAULT_ITEMS_MARGIN, 0);
    }
}