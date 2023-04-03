package com.example.fbiprojet;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A fragment representing a list of Items.
 */
public class InfosFragment extends Fragment {

    private Wanted actual_wanted;

    TextView description;
    TextView characteristic;

    public InfosFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InfosFragment newInstance(Wanted actual_wanted) {
        InfosFragment fragment = new InfosFragment();
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

        return inflater.inflate(R.layout.fragment_infos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        description = view.findViewById(R.id.description);
        characteristic = view.findViewById(R.id.characteristic);

        String desc = "Description brute: "+actual_wanted.getDescription();
        description.setText(desc);
        characteristic.setText(Html.fromHtml(
    "<b>Avertissement du FBI: </b>"  + actual_wanted.getWarning() + "<br /><br />"
        + "<b>Nationalité : </b> "  + actual_wanted.getNationalite() + "<br /><br />"
        + "<b>Catégorie : </b>"  + actual_wanted.getCategories() + " <br /><br />"
        + "<b>Remarques : </b>"  + actual_wanted.getRemarques() + "<br /><br />"
));
    }

}