package com.cresset.asimjofaofficial.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cresset.asimjofaofficial.fragments.BillingAddressDetail;
import com.cresset.asimjofaofficial.fragments.OnlineFragment;
import com.cresset.asimjofaofficial.fragments.ShippingAddressDetail;
import com.cresset.asimjofaofficial.fragments.StoreFragment;

/**
 * Created by attaullahkhizar on 8/29/17.
 */

public class AddressHistoryAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public AddressHistoryAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ShippingAddressDetail  shippingAddressDetail = new ShippingAddressDetail();
                return shippingAddressDetail;
            case 1:
                BillingAddressDetail billingAddressDetail = new BillingAddressDetail();
                return billingAddressDetail;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
