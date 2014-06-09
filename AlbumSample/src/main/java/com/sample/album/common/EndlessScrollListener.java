package com.sample.album.common;

import android.widget.AbsListView;

/**
 * Created by Carlos Jr on 07/06/2014.
 */
public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    public boolean stillLoading;

    public int itemsPerPage;

    public int currentPage;

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        final int lastItem = firstVisibleItem + visibleItemCount;
        if(lastItem == totalItemCount) {
            onLoadMoreItems(lastItem);
        }
    }

    public abstract void onLoadMoreItems(int lastItem);
}