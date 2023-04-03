package com.example.fbiprojet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WantedAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Wanted> wanteds;

    public WantedAdapter(Context c, ArrayList<Wanted> wanteds) {
        context = c;
        this.wanteds = wanteds;
    }

    @Override
    public int getCount() {
        return wanteds.size();
    }

    @Override
    public Object getItem(int i) {
        return wanteds.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public String getItemUid(int i) { return wanteds.get(i).getUid(); }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view = inflater.inflate(R.layout.grid_wanted_item, null);
        }

        TextView name = view.findViewById(R.id.grid_wanted_title);
        ImageView photo = view.findViewById(R.id.grid_wanted_image);

        name.setText(wanteds.get(i).getTitle());
//        System.out.println(wanteds.get(i).getImages().getImageUrl(0));
        Picasso.get().load(wanteds.get(i).getImages().getImageUrl(0)).resize(100,120).into(photo);
        return view;
    }
}
