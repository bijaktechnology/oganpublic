package com.incendiary.ambulanceapp.features.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.adapters.BaseListAdapter;
import com.incendiary.ambulanceapp.common.adapters.BaseViewHolder;

import javax.inject.Inject;

import butterknife.BindView;
import rx.functions.Action2;

public class OtherSettingsListAdapter extends BaseListAdapter<String> {

    private static final int SWITCH_POSITION = 0;

    private Action2<Boolean, Integer> onSwitch;
    private boolean isSwitchOn;

    @Inject
    public OtherSettingsListAdapter(Context context) {
        super(context);
    }

    void setOnSwitch(Action2<Boolean, Integer> onSwitch) {
        this.onSwitch = onSwitch;
    }

    void setSwitch(boolean isOn) {
        isSwitchOn = isOn;
        notifyItemChanged(SWITCH_POSITION);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        SettingViewHolder viewHolder = (SettingViewHolder) holder;
        viewHolder.txt.setText(getItem(position));

        viewHolder.switchCompat.setOnCheckedChangeListener(null);
        viewHolder.switchCompat.setChecked(isSwitchOn);
        viewHolder.switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (onSwitch != null) {
                onSwitch.call(isChecked, position);
            }
        });

        viewHolder.switchCompat.setVisibility(position == SWITCH_POSITION
                ? View.VISIBLE
                : View.GONE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SettingViewHolder(
                getInflater().inflate(R.layout.item_settings_other, parent, false)
        );
    }

    static class SettingViewHolder extends BaseViewHolder {

        @BindView(R.id.item_settings_other_txt) TextView txt;
        @BindView(R.id.item_settings_other_switch) SwitchCompat switchCompat;

        public SettingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
