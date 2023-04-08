package com.example.fbiprojet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.fbiprojet.R;
import com.example.fbiprojet.models.Wanted;
import com.squareup.picasso.Picasso;

/**
 * Fragment faisant apparaître les images d'une personne recherchée
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
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putSerializable("wanted", actual_wanted);
        // etc.

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            actual_wanted = (Wanted) savedInstanceState.getSerializable("wanted");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_images, container, false);
    }

    /**
     * Une fois la vue créée, toutes les images de la personne recherchée sont ajoutées au layout du fragment
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
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