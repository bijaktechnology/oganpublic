package com.incendiary.ambulanceapp.features.comments.reportcomment;

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
import com.incendiary.ambulanceapp.data.model.comment.Comment;
import com.incendiary.ambulanceapp.data.model.comment.Comments;

import javax.inject.Inject;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ReportCommentAdapter extends BaseListAdapter<Comment> {

    @Inject
    public ReportCommentAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        final CommentViewHolder vh = (CommentViewHolder) holder;
        final Comment comment = getItem(position);

        vh.txtName.setText(comment.getNamaLengkap());
        vh.txtTime.setText(Comments.getRelativeTime(comment));
        vh.txtContent.setText(comment.getComment());

        Glide.with(getContext())
                .load(comment.getAvatar())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(vh.imgAvatar);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(
                getInflater().inflate(R.layout.item_report_comment, parent, false)
        );
    }

    static class CommentViewHolder extends BaseViewHolder {

        @BindView(R.id.item_report_comment_img_user) ImageView imgAvatar;
        @BindView(R.id.item_report_comment_txt_user) TextView txtName;
        @BindView(R.id.item_report_comment_txt_comment) TextView txtContent;
        @BindView(R.id.item_report_comment_txt_time) TextView txtTime;


        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
