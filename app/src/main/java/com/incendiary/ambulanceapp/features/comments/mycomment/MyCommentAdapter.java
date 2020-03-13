package com.incendiary.ambulanceapp.features.comments.mycomment;

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

public class MyCommentAdapter extends BaseListAdapter<Comment> {

    @Inject
    public MyCommentAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        final CommentViewHolder vh = (CommentViewHolder) holder;
        final Comment comment = getItem(position);

        vh.txtTitle.setText(comment.getComment());
        vh.txtDate.setText(Comments.getRelativeTime(comment));

        Glide.with(getContext())
                .load(comment.getPhoto())
                .into(vh.img);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(
                getInflater().inflate(R.layout.item_my_comment, parent, false)
        );
    }

    static class CommentViewHolder extends BaseViewHolder {

        @BindView(R.id.item_my_comment_image) ImageView img;
        @BindView(R.id.item_my_comment_txt_title) TextView txtTitle;
        @BindView(R.id.item_my_comment_txt_date) TextView txtDate;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
