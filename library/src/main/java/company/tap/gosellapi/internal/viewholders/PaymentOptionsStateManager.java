package company.tap.gosellapi.internal.viewholders;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import company.tap.gosellapi.internal.adapters.PaymentOptionsRecyclerViewAdapter;

public class PaymentOptionsStateManager {
    private final String TOP_FOCUSED_POSITION = "TopFocusedPosition";
    private final String TOP_LAYOUT_STATE = "TopLayoutState";

    private final String FOCUSED_RECENT_POSITION = "FocusedRecentPosition";
//    private final String FOCUSED_RECENT_POSITION = "FocusedRecentPosition";

    private Bundle state;

    private PaymentOptionsStateManager() { }

    private static class SingletonHolder {
        private static final PaymentOptionsStateManager INSTANCE = new PaymentOptionsStateManager();
    }

    public static PaymentOptionsStateManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void saveState(RecyclerView recyclerView, PaymentOptionsRecyclerViewAdapter paymentOptionsRecyclerViewAdapter) {
        state = new Bundle();
//        state.putInt(TOP_FOCUSED_POSITION, paymentOptionsRecyclerViewAdapter.getFocusedPosition());

    }

    public void restoreState(PaymentOptionsRecyclerViewAdapter paymentOptionsRecyclerViewAdapter) {

    }

    public void clearState() {
        state = null;
    }
}
