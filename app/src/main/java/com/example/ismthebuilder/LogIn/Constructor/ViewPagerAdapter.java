package com.example.ismthebuilder.LogIn.Constructor;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.ismthebuilder.LogIn.Constructor.Available.AvailableFragment;
import com.example.ismthebuilder.LogIn.Constructor.MyContract.MyContractFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0: return new AvailableFragment();
            case 1: return new MyContractFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0: return "Available";
            case 1: return "Assigned to Me";
            default: return null;
        }
    }
}
