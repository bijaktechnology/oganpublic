package com.incendiary.ambulanceapp.utils.rx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.utils.Dates;
import com.incendiary.androidcommon.android.AndroidUtils;
import com.incendiary.androidcommon.android.ContextProvider;
import com.incendiary.androidcommon.android.text.Strings;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import rx.Emitter;
import rx.Observable;

import static rx.Observable.create;

public class RxEditText {

    public static Observable<String> bind(EditText editText) {
        return Observable.create(stringEmitter -> {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    stringEmitter.onNext(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }, Emitter.BackpressureMode.LATEST);
    }

    public static Observable<String> bindDebounce(EditText editText) {
        return bind(editText).debounce(400, TimeUnit.MILLISECONDS);
    }

    public static Observable<String> bindDob(Activity activity, EditText editText) {
        return create(stringEmitter -> {

            editText.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) return;

                AndroidUtils.hideKeyboard(editText);

                final String text = editText.getText().toString();

                Calendar calendar = Strings.isEmpty(text)
                        ? Calendar.getInstance()
                        : Dates.parseDob(text);

                if (calendar == null) {
                    calendar = Calendar.getInstance();
                }

                int year = calendar.get(Calendar.YEAR);
                int monthOfYear = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT,
                        (view, year1, month, dayOfMonth1) -> {

                            editText.setFocusable(false);
                            editText.setFocusable(true);
                            editText.setFocusableInTouchMode(true);

                            Calendar resultCalendar = Calendar.getInstance();
                            resultCalendar.set(Calendar.YEAR, year1);
                            resultCalendar.set(Calendar.MONTH, month);
                            resultCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth1);

                            stringEmitter.onNext(Dates.formatDob(resultCalendar));

                        }, year, monthOfYear, dayOfMonth);

                datePickerDialog.setTitle(ContextProvider.getString(R.string.profile_dob));
                datePickerDialog.show();

            });

        }, Emitter.BackpressureMode.LATEST);
    }

}
