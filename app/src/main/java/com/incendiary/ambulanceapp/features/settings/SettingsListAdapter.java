package com.incendiary.ambulanceapp.features.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.adapters.BaseListAdapter;
import com.incendiary.ambulanceapp.common.adapters.BaseViewHolder;

import javax.inject.Inject;

import butterknife.BindView;

public class SettingsListAdapter extends BaseListAdapter<String> {

    @Inject
    public SettingsListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        SettingViewHolder settingViewHolder = (SettingViewHolder) holder;
        settingViewHolder.txt.setText(getItem(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SettingViewHolder(
                getInflater().inflate(R.layout.item_settings, parent, false)
        );
    }

    static class SettingViewHolder extends BaseViewHolder {

        @BindView(R.id.item_settings_txt) TextView txt;

        public SettingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
