package com.incendiary.ambulanceapp.features.report.bookmark;

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
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.data.model.report.Reports;

import javax.inject.Inject;

import butterknife.BindView;

public class BookmarkListAdapter extends BaseListAdapter<Report> {

    @Inject
    public BookmarkListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        final ReportViewHolder viewHolder = (ReportViewHolder) holder;
        final Report report = getItem(position);

        viewHolder.txtTitle.setText(report.getJudul());
        viewHolder.txtDate.setText(report.getReportTime());
        viewHolder.txtStatus.setText(Reports.getStatusText(report));
        viewHolder.txtStatus.setBackgroundResource(Reports.getStatusBackground(report));

        Glide.with(getContext())
                .load(report.getFotoLaporan())
                .into(viewHolder.imgBookmark);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReportViewHolder(
                getInflater().inflate(R.layout.item_report_bookmark, parent, false)
        );
    }

    static class ReportViewHolder extends BaseViewHolder {

        @BindView(R.id.item_bookmark_image) ImageView imgBookmark;
        @BindView(R.id.item_bookmark_txt_title) TextView txtTitle;
        @BindView(R.id.item_bookmark_txt_date) TextView txtDate;
        @BindView(R.id.item_bookmark_txt_status) TextView txtStatus;


        public ReportViewHolder(View itemView) {
            super(itemView);
        }
    }
}
