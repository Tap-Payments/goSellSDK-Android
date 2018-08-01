package company.tap.gosellapi.internal.custom_views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import company.tap.gosellapi.R;

public class TapDialog {

    public interface TapDialogListener{
        void expirationDateSelected(String month, String year);
    }

    public static String expirationData = null;

    public static TapDialog expireDate(Context context, final TapDialogListener l){
        //init custom view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.gosellapi_layout_datepicker, null);

        final TextView tvMonth = view.findViewById(R.id.tvMonth);
        final TextView tvYear = view.findViewById(R.id.tvYear);
        final WheelPicker npMonth = view.findViewById(R.id.npMonthPicker);
        final WheelPicker npYear = view.findViewById(R.id.npYearPicker);

        List<String> months = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        npMonth.setData(months);
        npMonth.setSelectedItemPosition(currentMonth);

        List<String> years = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) + i);
            years.add(year);
        }
        npYear.setData(years);

        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title("Please select expiry date")
                .titleGravity(GravityEnum.CENTER)
                .cancelable(true)
                .customView(view, false)
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String month = npMonth.getData().get(npMonth.getCurrentItemPosition()).toString();
                        String year = npYear.getData().get(npYear.getCurrentItemPosition()).toString();

                        l.expirationDateSelected(month, year);
                    }
                })
                .negativeText("Cancel")
                .build();

        dialog.show();
        return null;
    }
}
