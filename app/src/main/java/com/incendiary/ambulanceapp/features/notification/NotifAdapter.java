package com.incendiary.ambulanceapp.features.notification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.adapters.BaseListAdapter;
import com.incendiary.ambulanceapp.common.adapters.BaseViewHolder;
import com.incendiary.ambulanceapp.data.model.notification.SemarNotification;

import javax.inject.Inject;

import butterknife.BindView;

public class NotifAdapter extends BaseListAdapter<SemarNotification> {

    @Inject
    public NotifAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        final NotifViewHolder viewHolder = (NotifViewHolder) holder;
        final SemarNotification notification = getItem(position);

        viewHolder.txtTitle.setText(notification.getNotificationBody());
        viewHolder.txtDate.setText(notification.getNotificationTime());
        viewHolder.imgNotif.setImageResource(R.drawable.bg_splash);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotifViewHolder(
                getInflater().inflate(R.layout.item_notif, parent, false)
        );
    }

    static class NotifViewHolder extends BaseViewHolder {

        @BindView(R.id.item_notif_image) ImageView imgNotif;
        @BindView(R.id.item_notif_txt_title) TextView txtTitle;
        @BindView(R.id.item_notif_txt_date) TextView txtDate;

        public NotifViewHolder(View itemView) {
            super(itemView);
        }
    }
}
