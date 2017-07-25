package com.cresset.asimjofaofficial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by attaullah.khizar on 3/13/2017.
 */

public class CompanyAccount extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.company_account,container, false);
        //getActivity().getActionBar().hide();
        return view;

    }
}
