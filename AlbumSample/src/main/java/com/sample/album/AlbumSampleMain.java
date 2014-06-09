package com.sample.album;

import android.app.Activity;

import android.app.ActionBar;
import android.support.v4.app.Fragment;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sample.album.common.ItemsList;

public class AlbumSampleMain extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private String selectedDrawerLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_sample_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(mNavigationDrawerFragment ==null){
           mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        }
        this.selectedDrawerLabel = mNavigationDrawerFragment.getSelectedDrawer(position);
        reloadActionBarTitle("");
        ItemsList itemsList = new ItemsList(this.selectedDrawerLabel);
        fragmentManager.beginTransaction()
                .replace(R.id.container, itemsList)
                .commit();
    }

    public void reloadActionBarTitle(String itemName){
        String actionBarLabel;
        if(itemName.isEmpty()){
            actionBarLabel = this.selectedDrawerLabel;
        }else{
            actionBarLabel = this.selectedDrawerLabel.concat(" - ").concat(itemName);
        }
        this.getSupportActionBar().setTitle(actionBarLabel);
    }

}