package com.cresset.asimjofaofficial.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cresset.asimjofaofficial.fragments.InvoiceShipping;


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
