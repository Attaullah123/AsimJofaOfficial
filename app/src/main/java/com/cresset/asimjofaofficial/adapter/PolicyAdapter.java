package com.cresset.asimjofaofficial.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cresset.asimjofaofficial.PrivacyPolicy;
import com.cresset.asimjofaofficial.PrivacyPolicyNew;
import com.cresset.asimjofaofficial.fragments.OnlineFragment;
import com.cresset.asimjofaofficial.fragments.OrderAndTracking;
import com.cresset.asimjofaofficial.fragments.ReturnAndExchange;
import com.cresset.asimjofaofficial.fragments.ShippingPolicy;
import com.cresset.asimjofaofficial.fragments.StoreFragment;
import com.cresset.asimjofaofficial.fragments.TermsAndConditions;


public class PolicyAdapter  extends FragmentStatePagerAdapter {
    String[] tabs;

    public PolicyAdapter(FragmentManager fm, String[] tabs) {
        super(fm);
        this.tabs = tabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TermsAndConditions termsAndConditions = new TermsAndConditions();
                return termsAndConditions;
            case 1:
                PrivacyPolicyNew privacyPolicy = new PrivacyPolicyNew();
                return privacyPolicy;
            case 2:
                ShippingPolicy shippingPolicy = new ShippingPolicy();
                return shippingPolicy;
            case 3:
                OrderAndTracking orderAndTracking = new OrderAndTracking();
                return orderAndTracking;
            case 4:
                ReturnAndExchange returnAndExchange = new ReturnAndExchange();
                return returnAndExchange;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
