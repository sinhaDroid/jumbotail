package only.sinha.android.mausam.app.module.common.widget.drawerlayout;

import android.support.annotation.Nullable;
import android.view.View;

public interface FantasyListener {
    boolean onHover(@Nullable View view, int index);

    boolean onSelect(View view, int index);

    void onCancel();
}
