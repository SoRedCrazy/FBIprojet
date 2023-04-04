package com.example.fbiprojet.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fbiprojet.models.Wanted;
import com.example.fbiprojet.fragments.ImagesFragment;
import com.example.fbiprojet.fragments.InfosFragment;

/**
 * Classe Adapter qui a pour but d'afficher correctement le TabLayout et les 2 Fragment
 */
public class PageAdapter extends FragmentPagerAdapter {

    Wanted actual_wanted;

    public PageAdapter(@NonNull FragmentManager fm, int behavior, Wanted actual_wanted) {
        super(fm, behavior);
        this.actual_wanted = actual_wanted;
    }

    /**
     * Ici on attribue les positions des 2 Fragment dans le TabLayout
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return InfosFragment.newInstance(actual_wanted);
            case 1:
                return ImagesFragment.newInstance(actual_wanted);
            default:
                return null;
        }
    }

    /**
     * Fonction servant à l'affichage des onglets du TabLayout
     * @return 2, le nombre d'onglets total
     */
    @Override
    public int getCount() {
        return 2;
    }

    /**
     * Fonction servant à l'affichage des titres des onglets du TabLayout
     * @param position La position du titre recherché
     * @return le titre recherché
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Infos";
            case 1:
                return "Autres images";
            default:
                return null;
        }
    }
}
