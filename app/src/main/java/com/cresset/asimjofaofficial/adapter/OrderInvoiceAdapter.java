package com.cresset.asimjofaofficial.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.cresset.asimjofaofficial.PrivacyPolicyNew;
import com.cresset.asimjofaofficial.fragments.InvoiceBilling;
import com.cresset.asimjofaofficial.fragments.InvoiceCart;
import com.cresset.asimjofaofficial.fragments.InvoiceShipping;
import com.cresset.asimjofaofficial.fragments.OrderAndTracking;
import com.cresset.asimjofaofficial.fragments.ReturnAndExchange;
import com.cresset.asimjofaofficial.fragments.ShippingPolicy;
import com.cresset.asimjofaofficial.fragments.TermsAndConditions;




public class OrderInvoiceAdapter extends FragmentPagerAdapter {

    String[] tabs;

    public OrderInvoiceAdapter(FragmentManager fm, String[] tabs) {
        super(fm);
        this.tabs = tabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InvoiceShipping invoiceShipping = new InvoiceShipping();
                return invoiceShipping;
            case 1:
                InvoiceBilling invoiceBilling = new InvoiceBilling();
                return invoiceBilling;
            case 2:
                InvoiceCart invoiceCart = new InvoiceCart();
                return invoiceCart;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return tabs.length;
    }

}
