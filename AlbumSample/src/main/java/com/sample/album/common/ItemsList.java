package com.sample.album.common;

import android.support.v4.app.ListFragment;
import android.os.Bundle;

import com.sample.album.AlbumSampleMain;

import java.util.ArrayList;
import java.util.List;

public class ItemsList extends ListFragment{

    private List<Item> dummies = new ArrayList<Item>();
    private ItemsAdapter itemAdapter = null;

    public String item_type;

    public ItemsList(String selectedDrawerLabel) {
        this.item_type = selectedDrawerLabel;
    }

    enum ITEM_TYPE{
        ALBUM("Albums"), SONG("Songs"), ARTIST("Artists");

        private String header;

        // Constructor
        ITEM_TYPE(String param) {
            header = param;
        }

        String getHeader() {
            return header;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Generate a list of 1000 dummy items
        loadMoreDummyItems(0);
        itemAdapter = new ItemsAdapter(dummies, getActivity());
        setListAdapter(itemAdapter);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getListView().setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMoreItems(int lastItem) {
                loadMoreDummyItems(lastItem);
                ItemsList.this.itemAdapter.notifyDataSetChanged();
            }
        });
    }

    private void loadMoreDummyItems(int currentIndex){
        int limit = currentIndex + ItemsAdapter.MAX_NUMBER_OF_ITEMS;
        for (int i = currentIndex; i < limit; i++) {
            Item item = new Item();
            // Reaplaced + to concat
            item.setMainHeader(this.item_type.toString().concat(" ").concat(String.valueOf(i)));
            item.setSecondaryHeader("Gender ".concat(String.valueOf(i)));
            dummies.add(item);
        }
    }
}