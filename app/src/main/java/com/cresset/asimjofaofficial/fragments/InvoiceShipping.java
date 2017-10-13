package com.cresset.asimjofaofficial.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cresset.asimjofaofficial.R;

/**
 * Created by attaullahkhizar on 10/13/17.
 */

public class InvoiceShipping extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.invoice_shipping, container, false);

        return view;
    }
}
