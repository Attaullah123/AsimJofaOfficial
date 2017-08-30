package com.cresset.asimjofaofficial.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cresset.asimjofaofficial.fragments.BillingAddress;
import com.cresset.asimjofaofficial.fragments.OnlineFragment;
import com.cresset.asimjofaofficial.fragments.ShippingAddress;
import com.cresset.asimjofaofficial.fragments.StoreFragment;

/**
 * Created by attaullahkhizar on 8/29/17.
 */

public class ContactAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ContactAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                OnlineFragment onlineFragment = new OnlineFragment();
                return onlineFragment;
            case 1:
                StoreFragment storeFragment = new StoreFragment();
                return storeFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
