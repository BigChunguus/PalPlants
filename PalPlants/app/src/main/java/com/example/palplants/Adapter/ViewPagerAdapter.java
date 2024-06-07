package com.example.palplants.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.palplants.Fragment.LoginTabFragment;
import com.example.palplants.Fragment.SignupTabFragment;

// Este adaptador de ViewPager se utiliza para manejar las pestañas de inicio de sesión y registro en una actividad.
// Proporciona los fragmentos correspondientes a cada pestaña y controla la cantidad total de pestañas.
public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1)
            return new SignupTabFragment();

        return new LoginTabFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
