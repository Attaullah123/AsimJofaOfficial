package com.cresset.asimjofaofficial.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cresset.asimjofaofficial.CompanyAccount;
import com.cresset.asimjofaofficial.IndiviualAccount;
import com.cresset.asimjofaofficial.fragments.BillingAddress;
import com.cresset.asimjofaofficial.fragments.ShippingAddress;


/**
 * Created by cresset on 17/06/2017.
 */

public class PagerShippingSelection extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerShippingSelection(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ShippingAddress shippingAddress = new ShippingAddress();
                return shippingAddress;
            case 1:
                BillingAddress billingAddress = new BillingAddress();
                return billingAddress;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
