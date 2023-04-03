package com.example.fbiprojet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    Wanted actual_wanted;

    public PageAdapter(@NonNull FragmentManager fm, int behavior, Wanted actual_wanted) {
        super(fm, behavior);
        this.actual_wanted = actual_wanted;
    }

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

    @Override
    public int getCount() {
        return 2;
    }

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
