package com.incendiary.ambulanceapp.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> data = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    protected Action1<Integer> onItemClickListener;

    public void setOnItemClickListener(Action1<Integer> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null && holder.itemView != null) {
            if (onItemClickListener != null) {
                int finalPosition = holder.getAdapterPosition();
                if (finalPosition != RecyclerView.NO_POSITION) {
                    holder.itemView.setOnClickListener(view -> onItemClickListener.call(finalPosition));
                }
            }
        }
        onBind(holder, position);
    }

    protected abstract void onBind(RecyclerView.ViewHolder holder, int position);

    public BaseListAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void pushData(List<T> data) {
        pushData(data, true);
    }

    public void pushData(List<T> data, boolean isFirstPage) {
        if (isFirstPage) {
            this.data.clear();
        }
        this.data.addAll(data);
    }

    public T getItem(int position) {
        return data.get(position);
    }

    public List<T> getData() {
        return data;
    }
}
