package com.incendiary.ambulanceapp.features.profile.medicalhistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.adapters.BaseListAdapter;
import com.incendiary.ambulanceapp.common.adapters.BaseViewHolder;
import com.incendiary.ambulanceapp.data.model.RekamMedis;

import javax.inject.Inject;

import butterknife.BindView;

public class MedicalHistoryAdapter extends BaseListAdapter<RekamMedis> {

    @Inject
    public MedicalHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    public MedicalHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MedicalHistoryViewHolder(
                getInflater().inflate(R.layout.item_rekam_medis, parent, false)
        );
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder viewHolder, int position) {
        final RekamMedis item = getItem(position);
        final MedicalHistoryViewHolder holder = (MedicalHistoryViewHolder) viewHolder;

        holder.txtDate.setText(item.getTanggal());
        holder.txtJenisPenyakit.setText(item.getDiagnosa());
        holder.txtNameRs.setText(item.getRsPuskesmas());
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public class MedicalHistoryViewHolder extends BaseViewHolder {

        @BindView(R.id.txtDate) TextView txtDate;
        @BindView(R.id.txtNameRs) TextView txtNameRs;
        @BindView(R.id.txtJenisPenyakit) TextView txtJenisPenyakit;

        public MedicalHistoryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
