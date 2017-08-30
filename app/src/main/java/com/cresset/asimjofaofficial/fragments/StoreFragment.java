package com.cresset.asimjofaofficial.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cresset.asimjofaofficial.R;

/**
 * Created by attaullahkhizar on 8/25/17.
 */

public class StoreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, viewGroup, false);
        //output= (TextView)view.findViewById(R.id.textView);
        //output.setText("Fragment Two");


        return view;
    }
}
