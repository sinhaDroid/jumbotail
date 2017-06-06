package only.sinha.android.mausam.app.module.common.widget.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by deepanshu on 1/6/17.
 */

public class CustomLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled;

    public CustomLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
