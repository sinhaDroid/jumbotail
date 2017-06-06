package only.sinha.android.mausam.app.module.navbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by deepanshu on 5/6/17.
 */

public class NavBarFragment extends Fragment {

    public static NavBarFragment newInstance(Bundle bundle) {

        Bundle args = new Bundle();

        NavBarFragment fragment = new NavBarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnNavBarListener {

        void openNavDrawer();

        void closeNavDrawer();
    }
}
