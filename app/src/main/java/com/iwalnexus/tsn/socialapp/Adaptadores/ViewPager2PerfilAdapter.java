package com.iwalnexus.tsn.socialapp.Adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.iwalnexus.tsn.socialapp.Fragmentos.FriendsFragment;
import com.iwalnexus.tsn.socialapp.Fragmentos.PerfilFragment;


import static com.iwalnexus.tsn.socialapp.Activities.PerfilActivity.TABFRIENDS;
import static com.iwalnexus.tsn.socialapp.Activities.PerfilActivity.TABPERFIL;


public class ViewPager2PerfilAdapter extends FragmentStateAdapter {

    private int numeroDeTabs;

    public ViewPager2PerfilAdapter(@NonNull FragmentActivity fragmentActivity, int numeroDeTabs) {
        super(fragmentActivity);
        this.numeroDeTabs = numeroDeTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case TABPERFIL:
                return new PerfilFragment();
            case TABFRIENDS:
                return new FriendsFragment();
            default:
                    return null;
        }

    }

    @Override
    public int getItemCount() {
        return numeroDeTabs;
    }
}
