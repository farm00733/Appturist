package com.appturist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by suksun on 9/25/2016 AD.
 */

public class MenuuAdapter extends BaseAdapter {
    private Context context;

    private String[] mpictureStrings, mnameStrings;

    public MenuuAdapter(Context context, String[] mpictureStrings, String[] mnameStrings) {
        this.context = context;
        this.mpictureStrings = mpictureStrings;
        this.mnameStrings = mnameStrings;
        //  this.tphoneStrings = tphoneStrings;
    }

    @Override
    public int getCount() {
        return mpictureStrings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.menu_listview, viewGroup, false);
        TextView titleTextView = (TextView) view1.findViewById(R.id.textViewmenu);
        titleTextView.setText(mnameStrings[i]);

        ImageView iconImageView = (ImageView) view1.findViewById(R.id.imageViewmenu);
        Picasso.with(context).load(mpictureStrings[i]).resize(100, 140).into(iconImageView);
        return view1;
    }
}
