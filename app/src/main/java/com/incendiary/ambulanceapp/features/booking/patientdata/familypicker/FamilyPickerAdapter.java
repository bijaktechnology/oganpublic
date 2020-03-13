package com.incendiary.ambulanceapp.features.booking.patientdata.familypicker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.adapters.BaseListAdapter;
import com.incendiary.ambulanceapp.common.adapters.BaseViewHolder;
import com.incendiary.ambulanceapp.data.model.family.Family;

import javax.inject.Inject;

import butterknife.BindView;

public class FamilyPickerAdapter extends BaseListAdapter<Family> {

    @Inject
    public FamilyPickerAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        final PickerViewHolder pickerViewHolder = (PickerViewHolder) holder;
        final Family family = getItem(position);

        pickerViewHolder.txtName.setText(family.getNamaLengkap());
        pickerViewHolder.txtStatus.setText(family.getStatusKeluarga());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PickerViewHolder(
                getInflater().inflate(R.layout.item_family_picker, parent, false)
        );
    }

    static class PickerViewHolder extends BaseViewHolder {

        @BindView(R.id.item_family_picker_txt_name) TextView txtName;
        @BindView(R.id.item_family_picker_txt_status) TextView txtStatus;

        public PickerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
