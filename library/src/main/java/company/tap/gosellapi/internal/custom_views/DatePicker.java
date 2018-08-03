package company.tap.gosellapi.internal.custom_views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class DatePicker {

    public interface DatePickerListener {
        void dateSelected(String month, String year);
    }

    public static String expirationData = null;

    public static void showInContext(Context context, @Nullable String selectedMonth, @Nullable String selectedYear, final DatePickerListener listener){
        //init custom view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.gosellapi_layout_datepicker, null);

        if (selectedMonth == null ) { selectedMonth = String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1); }
        if (selectedYear  == null ) { selectedYear  = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)); }

        final WheelPicker monthPicker = view.findViewById(R.id.npMonthPicker);
        ArrayList<String> monthValues = getMonthPickerValues();
        fillWheelPickerWithData(monthPicker, monthValues, selectedMonth);

        final WheelPicker yearPicker = view.findViewById(R.id.npYearPicker);
        ArrayList<String> yearValues = getYearPickerValues();
        fillWheelPickerWithData(yearPicker, yearValues, selectedYear);

        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title("Please select expiry date")
                .titleGravity(GravityEnum.CENTER)
                .cancelable(true)
                .customView(view, false)
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        String month = getSelectedValueFromPicker(monthPicker);
                        String year  = getSelectedValueFromPicker(yearPicker);

                        listener.dateSelected(month, year);
                    }
                })
                .negativeText("Cancel")
                .build();

        dialog.show();
    }

    private static void fillWheelPickerWithData(WheelPicker picker, List<String> data, String selectedValue) {

        picker.setData(data);

        int selectedPosition = data.indexOf(selectedValue);
        if (selectedPosition != -1) {

            picker.setSelectedItemPosition(selectedPosition);
        }
    }

    private static ArrayList<String> getMonthPickerValues() {

        ArrayList<String> result = new ArrayList<String>();
        for (int index = 1; index < MONTHS_COUNT + 1; index++ ) {

            String monthString = String.format("%02d", index);
            result.add(monthString);
        }

        return result;
    }

    private static ArrayList<String> getYearPickerValues() {

        ArrayList<String> result = new ArrayList<String>();

        int currentYearIndex = Calendar.getInstance().get(Calendar.YEAR);
        for (int index = 0; index < YEARS_COUNT + 1; index++) {

            String yearString = String.valueOf(currentYearIndex + index);
            result.add(yearString);
        }

        return result;
    }

    private static String getSelectedValueFromPicker(WheelPicker picker) {

        int index = picker.getCurrentItemPosition();
        String result = (String)picker.getData().get(index);

        return result;
    }

    private static int MONTHS_COUNT = 12;
    private static int YEARS_COUNT  = 50;
}
