package com.incendiary.ambulanceapp.features.profile.bookinghistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.adapters.BaseListAdapter;
import com.incendiary.ambulanceapp.common.adapters.BaseViewHolder;
import com.incendiary.ambulanceapp.data.model.HistoryBooking;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingHistoryAdapter extends BaseListAdapter<HistoryBooking> {

    @Inject
    public BookingHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder viewHolder, int position) {
        final HistoryBooking item = getItem(position);
        final HistoryViewHolder holder = (HistoryViewHolder) viewHolder;

        holder.itemHistoryBookingTxtTitle.setText(item.getType());
        holder.itemHistoryBookingTxtTime.setText(item.getTimestamp());
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(
                getInflater().inflate(R.layout.item_history_booking, parent, false)
        );
    }

    public class HistoryViewHolder extends BaseViewHolder {

        @BindView(R.id.item_history_booking_txt_title) TextView itemHistoryBookingTxtTitle;
        @BindView(R.id.item_history_booking_txt_time) TextView itemHistoryBookingTxtTime;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
