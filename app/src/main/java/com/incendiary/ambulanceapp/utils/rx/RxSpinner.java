package com.incendiary.ambulanceapp.utils.rx;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import rx.Emitter;
import rx.Observable;
import rx.android.MainThreadSubscription;

public class RxSpinner {

    public static Observable<Integer> position(Spinner spinner) {
        return Observable.create(emitter -> {

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    emitter.onNext(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            emitter.setSubscription(new MainThreadSubscription() {
                @Override
                protected void onUnsubscribe() {
                    spinner.setOnItemClickListener(null);
                }
            });

        }, Emitter.BackpressureMode.LATEST);
    }

}
