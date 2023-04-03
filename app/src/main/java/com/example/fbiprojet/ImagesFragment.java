package com.example.fbiprojet;

import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

/**
 * A fragment representing a list of Items.
 */
public class ImagesFragment extends Fragment {


    private Wanted actual_wanted;

    public ImagesFragment() {
        // Required empty public constructor
    }

    public static ImagesFragment newInstance(Wanted actual_wanted) {
        ImagesFragment fragment = new ImagesFragment();
        fragment.actual_wanted = actual_wanted;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_images, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.images_layout);
        for(int i=0; i<actual_wanted.getImages().images.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            layout.addView(imageView);
            Picasso.get().load(actual_wanted.getImages().getImageUrl(i)).into(imageView);
        }
    }

}