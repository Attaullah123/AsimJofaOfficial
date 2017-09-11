package com.cresset.asimjofaofficial.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;





/**
 * Created by attaullah.khizar on 3/13/2017.
 */

public class PagerAdapterAccount extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapterAccount(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

            case 1:
//                CompanyAccount CompanyAccount = new CompanyAccount();
//                return CompanyAccount;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}