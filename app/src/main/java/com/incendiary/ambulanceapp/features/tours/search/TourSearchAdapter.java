package com.incendiary.ambulanceapp.features.tours.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.adapters.BaseListAdapter;
import com.incendiary.ambulanceapp.common.adapters.BaseViewHolder;
import com.incendiary.ambulanceapp.data.model.tours.TourPlace;

import javax.inject.Inject;

import butterknife.BindView;

public class TourSearchAdapter extends BaseListAdapter<TourPlace> {

    @Inject
    public TourSearchAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        SearchViewHolder searchViewHolder = (SearchViewHolder) holder;
        searchViewHolder.txt.setText(getItem(position).getNamaDestinasiWisata());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(
                getInflater().inflate(R.layout.item_tour_search, parent, false)
        );
    }

    static class SearchViewHolder extends BaseViewHolder {

        @BindView(R.id.item_tour_search_txt) TextView txt;

        public SearchViewHolder(View itemView) {
            super(itemView);
        }
    }
}
