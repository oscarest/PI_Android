package com.studium.xxracso40xx.pi_android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private Fragment[] childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[]
                {
                        new primero(), //0
                        new segundo() //1
                };
    }
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new primero(); //ChildFragment1 at position 0
            case 1:
                return new segundo(); //ChildFragment2 at position 1

        }
        return null; //does not happen
    }
    @Override
    public int getCount() {
        return 2; //two fragments
    }
}
