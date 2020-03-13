package com.incendiary.ambulanceapp.features.report;

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
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.functions.Action1;

public class ReportListAdapter extends BaseListAdapter<Report> {

    private Action1<Report> onCommentClick;
    private Action1<Report> onLikeClick;
    private Action1<Report> onStatusClick;

    @Inject
    public ReportListAdapter(Context context) {
        super(context);
    }

    public void setOnCommentClick(Action1<Report> onCommentClick) {
        this.onCommentClick = onCommentClick;
    }

    public void setOnLikeClick(Action1<Report> onLikeClick) {
        this.onLikeClick = onLikeClick;
    }

    public void setOnStatusClick(Action1<Report> onStatusClick) {
        this.onStatusClick = onStatusClick;
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        final ReportViewHolder viewHolder = (ReportViewHolder) holder;
        final Report report = getItem(position);

        viewHolder.txtLove.setText(report.getJumlahLike());
        viewHolder.txtComment.setText(report.getJumlahComment());
        viewHolder.txtUser.setText(report.getNamaLengkap());
        viewHolder.txtLocation.setText(Reports.getReportLocation(report));
        viewHolder.txtTime.setText(Reports.getRelativeTime(report));
        viewHolder.txtCategory.setText(report.getNamaKategori());

        viewHolder.txtStatus.setText(Reports.getStatusText(report));
        viewHolder.txtStatus.setBackgroundResource(Reports.getStatusBackground(report));
        viewHolder.txtStatus.setOnClickListener(v -> {
            if (onStatusClick != null) {
                onStatusClick.call(report);
            }
        });

        viewHolder.txtTitle.setText(report.getJudul());
        viewHolder.txtDesc.setText(report.getKeterangan());

        viewHolder.txtLove.setCompoundDrawablesWithIntrinsicBounds(0, report.isStatusLike()
                ? R.drawable.ic_love_red :
                R.drawable.ic_love, 0, 0);

        viewHolder.txtLove.setClickable(true);
        viewHolder.txtLove.setOnClickListener(v -> {
            if (onLikeClick != null) {
                onLikeClick.call(report);
            }
        });

        viewHolder.txtComment.setClickable(true);
        viewHolder.txtComment.setOnClickListener(v -> {
            if (onCommentClick != null) {
                onCommentClick.call(report);
            }
        });

        Glide.with(getContext())
                .load(report.getFotoLaporan())
                .into(viewHolder.imgReport);

        Glide.with(getContext())
                .load(report.getIcon())
                .into(viewHolder.imgCategory);

        Glide.with(getContext())
                .load(report.getAvatar())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(viewHolder.imgAvatar);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReportViewHolder(
                getInflater().inflate(R.layout.item_report, parent, false)
        );
    }

    void updateLikeStatus(Report report, boolean isLike) {
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).getLaporanId().equalsIgnoreCase(report.getLaporanId())) {
                final int likeCount = Integer.valueOf(report.getJumlahLike());
                report.setStatusLike(isLike);
                report.setJumlahLike(String.valueOf(isLike ? likeCount + 1 : likeCount - 1));
                getData().set(i, report);
                notifyItemChanged(i);
                break;
            }
        }
    }

    static class ReportViewHolder extends BaseViewHolder {

        @BindView(R.id.item_report_txt_love) TextView txtLove;
        @BindView(R.id.item_report_txt_comment) TextView txtComment;
        @BindView(R.id.item_report_txt_user) TextView txtUser;
        @BindView(R.id.item_report_txt_location) TextView txtLocation;
        @BindView(R.id.item_report_txt_time) TextView txtTime;
        @BindView(R.id.item_report_txt_category) TextView txtCategory;
        @BindView(R.id.item_report_img_category) ImageView imgCategory;
        @BindView(R.id.item_report_img_report) ImageView imgReport;
        @BindView(R.id.item_task_txt_status) TextView txtStatus;
        @BindView(R.id.item_report_txt_title) TextView txtTitle;
        @BindView(R.id.item_report_txt_desc) TextView txtDesc;
        @BindView(R.id.item_report_txt_see_more) TextView txtSeeMore;
        @BindView(R.id.item_report_img_avatar) ImageView imgAvatar;

        public ReportViewHolder(View itemView) {
            super(itemView);
        }
    }
}
