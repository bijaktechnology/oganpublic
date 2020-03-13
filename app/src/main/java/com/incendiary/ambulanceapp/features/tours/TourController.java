package com.incendiary.ambulanceapp.features.tours;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.features.tours.search.TourSearchController;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.conductor.changehandlers.CircularRevealChangeHandler;

import javax.inject.Inject;

import butterknife.BindView;

public class TourController extends AbsController {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tours_tab_layout) TabLayout tabLayout;
    @BindView(R.id.tours_viewpager) ViewPager viewPager;

    @Inject ToursPagerAdapter pagerAdapter;

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_tours;
    }

    @Override
    protected void onViewBound(View view) {
        toolbar.setTitle(R.string.menu_destinasi_wisata);
        toolbar.setNavigationOnClickListener(v -> finishActivity());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.inflateMenu(R.menu.menu_tour);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_search:
                    goToTourSearch();
                    break;
            }
            return true;
        });

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
    }

    private void goToTourSearch() {
        getRouter().pushController(Routes.simpleTransaction(
                new TourSearchController(),
                new CircularRevealChangeHandler(toolbar, ((View) toolbar.getParent()))
        ));
    }
}
