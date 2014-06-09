package com.sample.album.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sample.album.AlbumSampleMain;
import com.sample.album.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import android.graphics.Bitmap;
import com.nostra13.universalimageloader.*;

public class ItemsAdapter extends BaseAdapter implements View.OnClickListener {
    private List<Item> items;
    private Context mContext;

    protected ImageLoader imageLoader;

    public static final int MAX_NUMBER_OF_ITEMS = 25;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private DisplayImageOptions options;

    // Replaced ArrayList to List to provide flexibility
    public ItemsAdapter(List<Item> items, Context mContext) {
        imageLoader = ImageLoader.getInstance();
        this.items = items;
        this.mContext = mContext;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.placeholder)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(10))
                .build();

        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        Item item = (Item) getItem(position);
        if (convertView == null) {
            view = ((AlbumSampleMain)mContext).getLayoutInflater().inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.main_header);
            holder.image = (ImageView) view.findViewById(R.id.icon);
            holder.secondaryHeader = (TextView) view.findViewById(R.id.secondary_header);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        view.setOnClickListener(this);
        holder.text.setText(item.getMainHeader());
        holder.secondaryHeader.setText(item.getSecondaryHeader());
       // ((TextView) view.findViewById(R.id.main_header)).setText(item.getMainHeader());
        //((TextView) view.findViewById(R.id.secondary_header)).setText(item.getSecondaryHeader());
        imageLoader.displayImage(Constants.IMAGES[position  % Constants.IMAGES.length], holder.image, options, animateFirstListener);

        return view;
    }

    @Override
    public void onClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        ((AlbumSampleMain)this.mContext).reloadActionBarTitle(viewHolder.text.getText().toString());
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    private static class ViewHolder {
        public TextView text;
        public ImageView image;
        public TextView secondaryHeader;
    }
}
