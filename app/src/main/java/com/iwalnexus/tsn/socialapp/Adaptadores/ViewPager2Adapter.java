package com.iwalnexus.tsn.socialapp.Adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.iwalnexus.tsn.socialapp.Fragmentos.PostFragment;
import com.iwalnexus.tsn.socialapp.Fragmentos.UsersFragment;

import static com.iwalnexus.tsn.socialapp.Activities.MainActivity.TABPOST;
import static com.iwalnexus.tsn.socialapp.Activities.MainActivity.TABUSER;

public class ViewPager2Adapter extends FragmentStateAdapter {

    private int numeroDeTabs;

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity, int numeroDeTabs) {
        super(fragmentActivity);
        this.numeroDeTabs = numeroDeTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case TABPOST:
                return new PostFragment();
            case TABUSER:
                return new UsersFragment();
            default:
                    return null;
        }

    }

    @Override
    public int getItemCount() {
        return numeroDeTabs;
    }
}
