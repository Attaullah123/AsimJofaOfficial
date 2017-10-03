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

public class OrderAndTracking extends Fragment {
    View rootView;
    TextView orderPlacementText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.order_tracking, container,false);

        orderPlacementText = (TextView) rootView.findViewById(R.id.order_placement_text);

        orderPlacementText.setLineSpacing(0,1.4f);
        return rootView;
    }
}