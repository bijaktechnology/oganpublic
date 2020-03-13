package com.incendiary.ambulanceapp.features.report;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.adapters.BaseListAdapter;
import com.incendiary.ambulanceapp.common.adapters.BaseViewHolder;
import com.incendiary.ambulanceapp.data.model.report.Report;

import javax.inject.Inject;

import butterknife.BindView;

public class ReportPopularListAdapter extends BaseListAdapter<Report> {

    @Inject
    public ReportPopularListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        final ReportViewHolder viewHolder = (ReportViewHolder) holder;
        final Report report = getItem(position);

        Glide.with(holder.itemView.getContext())
                .load(report.getFotoLaporan())
                .into(viewHolder.imgReport);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReportViewHolder(
                getInflater().inflate(R.layout.item_report_popular, parent, false)
        );
    }

    static class ReportViewHolder extends BaseViewHolder {

        @BindView(R.id.item_report_popular_img_report) ImageView imgReport;

        public ReportViewHolder(View itemView) {
            super(itemView);
        }
    }
}
