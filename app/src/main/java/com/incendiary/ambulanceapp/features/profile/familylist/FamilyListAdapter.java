package com.incendiary.ambulanceapp.features.profile.familylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.adapters.BaseListAdapter;
import com.incendiary.ambulanceapp.common.adapters.BaseViewHolder;
import com.incendiary.ambulanceapp.data.model.family.Family;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

public class FamilyListAdapter extends BaseListAdapter<Family> {

    private Action1<Integer> onDelete;
    private Action1<Integer> onEdit;

    public void setOnDelete(Action1<Integer> onDelete) {
        this.onDelete = onDelete;
    }

    public void setOnEdit(Action1<Integer> onEdit) {
        this.onEdit = onEdit;
    }

    @Inject
    public FamilyListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        final FamilyViewHolder viewHolder = (FamilyViewHolder) holder;
        viewHolder.bind(getItem(position), onDelete, onEdit);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FamilyViewHolder(
                getInflater().inflate(R.layout.item_family, parent, false)
        );
    }

    public void remove(Family family) {
        Observable.from(getData())
                .filter(f -> !f.getAnggotaId().equalsIgnoreCase(family.getAnggotaId()))
                .toList()
                .subscribe(families -> {
                    pushData(families);
                    notifyDataSetChanged();
                }, ErrorHandler::handleError);
    }

    static class FamilyViewHolder extends BaseViewHolder {

        @BindView(R.id.item_family_txt_name) TextView txtName;
        @BindView(R.id.item_family_txt_status) TextView txtStatus;
        @BindView(R.id.item_family_txt_ktp) TextView txtKtp;
        @BindView(R.id.item_family_txt_dob) TextView txtDob;
        @BindView(R.id.item_family_txt_mother_name) TextView txtMotherName;

        @BindView(R.id.item_family_btn_delete) View btnDelete;
        @BindView(R.id.item_family_btn_edit) View btnEdit;

        public FamilyViewHolder(View itemView) {
            super(itemView);
        }

        void bind(Family family, Action1<Integer> onDelete, Action1<Integer> onEdit) {
            txtName.setText(family.getNamaLengkap());
            txtStatus.setText(family.getStatusKeluarga());
            txtKtp.setText(family.getNik());
            txtDob.setText(family.getTglLahir());
            txtMotherName.setText(family.getNamaIbuKandung());

            btnDelete.setOnClickListener(v -> onDelete.call(getAdapterPosition()));
            btnEdit.setOnClickListener(v -> onEdit.call(getAdapterPosition()));
        }
    }
}
