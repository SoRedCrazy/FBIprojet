package com.example.fbiprojet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fbiprojet.R;
import com.example.fbiprojet.models.Wanted;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Classe Adapter qui permet d'afficher toutes les personnes recherchées dans une GridView
 */
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

    /**
     * Fonction qui insère la photo et l'image de la personne recherchée dans un layout item
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
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

        try {
            name.setText(wanteds.get(i).getTitle());
            //        System.out.println(wanteds.get(i).getImages().getImageUrl(0));
            Picasso.get().load(wanteds.get(i).getImages().getImageUrl(0)).resize(100, 120).into(photo);
        } catch (Exception e) {
            System.out.println("Doucement avec la GridView !");
        }
        return view;
    }
}
