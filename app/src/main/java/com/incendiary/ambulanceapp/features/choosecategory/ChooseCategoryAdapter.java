package com.incendiary.ambulanceapp.features.choosecategory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.adapters.BaseListAdapter;
import com.incendiary.ambulanceapp.common.adapters.BaseViewHolder;
import com.incendiary.ambulanceapp.data.model.report.ReportCategory;

import javax.inject.Inject;

import butterknife.BindView;

public class ChooseCategoryAdapter extends BaseListAdapter<ReportCategory> {

    @Inject
    public ChooseCategoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        final ReportViewHolder viewHolder = (ReportViewHolder) holder;
        final ReportCategory reportcategory = getItem(position);

        viewHolder.txtName.setText(reportcategory.getNamaKategori());

        Glide.with(viewHolder.itemView.getContext())
                .load(reportcategory.getIcon())
                .into(viewHolder.imgCategory);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReportViewHolder(
                getInflater().inflate(R.layout.item_choose_category, parent, false)
        );
    }

    static class ReportViewHolder extends BaseViewHolder {

        @BindView(R.id.item_choose_category_img) ImageView imgCategory;
        @BindView(R.id.item_choose_category_txt_category) TextView txtName;

        public ReportViewHolder(View itemView) {
            super(itemView);
        }
    }
}
