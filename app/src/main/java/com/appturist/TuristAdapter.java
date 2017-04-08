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
 * Created by suksun on 9/22/2016 AD.
 */
public class TuristAdapter extends BaseAdapter {
    private Context context;

    private String[] tpictureStrings, tnameStrings;

    public  TuristAdapter(Context context,String[] tpictureStrings,String[] tnameStrings){
        this.context = context;
        this.tpictureStrings = tpictureStrings;
        this.tnameStrings = tnameStrings;
      //  this.tphoneStrings = tphoneStrings;
    }

    @Override
    public int getCount() {
        return tpictureStrings.length;
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
        View view1 = layoutInflater.inflate(R.layout.turist_listview,viewGroup,false);
        TextView titleTextView = (TextView)view1.findViewById(R.id.textView4);
        titleTextView.setText(tnameStrings[i]);

        ImageView iconImageView = (ImageView)view1.findViewById(R.id.imageView2);
        Picasso.with(context).load(tpictureStrings[i]).resize(100,140).into(iconImageView);
        return view1;
    }
}
