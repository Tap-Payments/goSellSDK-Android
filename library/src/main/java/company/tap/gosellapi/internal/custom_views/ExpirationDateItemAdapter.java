package company.tap.gosellapi.internal.custom_views;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.utils.ExpirationDateDialogTheme;
import company.tap.gosellapi.internal.utils.VibrationHelper;

/**
 * The type Expiration date item adapter.
 */
public class ExpirationDateItemAdapter extends ArrayAdapter<String> {

    private ExpirationDateDialogTheme mTheme;
    private ShapeDrawable mSelectedItemBackground;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private int mSelectedPosition = -1;
    private List<Integer> mDisabledPositions = new ArrayList<>();

    /**
     * Instantiates a new Expiration date item adapter.
     *
     * @param context  the context
     * @param resource the resource
     */
    public ExpirationDateItemAdapter(Context context, int resource) {
        super(context, resource);
    }

    /**
     * Instantiates a new Expiration date item adapter.
     *
     * @param context            the context
     * @param resource           the resource
     * @param textViewResourceId the text view resource id
     */
    public ExpirationDateItemAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    /**
     * Instantiates a new Expiration date item adapter.
     *
     * @param context  the context
     * @param resource the resource
     * @param objects  the objects
     */
    public ExpirationDateItemAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    /**
     * Instantiates a new Expiration date item adapter.
     *
     * @param context            the context
     * @param resource           the resource
     * @param textViewResourceId the text view resource id
     * @param objects            the objects
     */
    public ExpirationDateItemAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Instantiates a new Expiration date item adapter.
     *
     * @param context  the context
     * @param resource the resource
     * @param objects  the objects
     */
    public ExpirationDateItemAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    /**
     * Instantiates a new Expiration date item adapter.
     *
     * @param context            the context
     * @param resource           the resource
     * @param textViewResourceId the text view resource id
     * @param objects            the objects
     */
    public ExpirationDateItemAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Instantiates a new Expiration date item adapter.
     *
     * @param context the context
     * @param theme   the theme
     * @param objects the objects
     */
    public ExpirationDateItemAdapter(Context context, ExpirationDateDialogTheme theme, List<String> objects) {
        super(context, R.layout.bt_expiration_date_item, objects);
        mTheme = theme;

        float radius = context.getResources().getDimension(R.dimen.bt_expiration_date_item_selected_background_radius);
        float[] radii = new float[] { radius, radius, radius, radius, radius, radius, radius, radius };
        mSelectedItemBackground = new ShapeDrawable(new RoundRectShape(radii, null, null));
        mSelectedItemBackground.getPaint().setColor(mTheme.getSelectedItemBackground());
//        mSelectedItemBackground.getPaint().setColor(Color.parseColor("#F2F2F2"));
    }

    /**
     * Sets on item click listener.
     *
     * @param listener the listener
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * Sets selected.
     *
     * @param position the position
     */
    public void setSelected(int position) {
        mSelectedPosition = position;
        notifyDataSetChanged();
    }

    /**
     * Sets disabled.
     *
     * @param disabledPositions the disabled positions
     */
    public void setDisabled(List<Integer> disabledPositions) {
        mDisabledPositions = disabledPositions;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setEnabled(true);
        if (mSelectedPosition == position) {
            view.setBackgroundDrawable(mSelectedItemBackground);
            view.setTextColor(mTheme.getItemInvertedTextColor());
        } else {
            view.setBackgroundResource(android.R.color.transparent);

            if (mDisabledPositions.contains(position)) {
                view.setTextColor(mTheme.getItemDisabledTextColor());
                view.setEnabled(false);
            } else {
                view.setTextColor(mTheme.getItemTextColor());
            }
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPosition = position;
                notifyDataSetChanged();
                VibrationHelper.vibrate(getContext(), 10);

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(null, v, position, position);
                }
            }
        });

        return view;
    }
}
