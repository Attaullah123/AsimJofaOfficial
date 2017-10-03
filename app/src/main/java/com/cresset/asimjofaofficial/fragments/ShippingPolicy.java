package com.cresset.asimjofaofficial.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cresset.asimjofaofficial.R;

/**
 * Created by attaullahkhizar on 9/13/17.
 */

public class ShippingPolicy extends Fragment {
    View rootView;
    TextView shippingText,deliveryChargeText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shipping_policy, container,false);

        shippingText = (TextView) rootView.findViewById(R.id.shipping_text);
        deliveryChargeText = (TextView) rootView.findViewById(R.id.delivery_text);

        shippingText.setLineSpacing(0,1.4f);
        deliveryChargeText.setLineSpacing(0,1.4f);
        return rootView;
    }
}