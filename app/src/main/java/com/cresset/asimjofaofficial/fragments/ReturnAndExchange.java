package com.cresset.asimjofaofficial.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cresset.asimjofaofficial.R;


public class ReturnAndExchange extends Fragment {

    View rootView;
    TextView exchangeText,returnText,refundText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.return_exchange, container,false);
        exchangeText = (TextView) rootView.findViewById(R.id.exchange_text);
        returnText = (TextView) rootView.findViewById(R.id.return_policy_text);
        refundText = (TextView) rootView.findViewById(R.id.refund_policy_text);

        exchangeText.setLineSpacing(0,1.4f);
        returnText.setLineSpacing(0,1.4f);
        refundText.setLineSpacing(0,1.4f);

        return rootView;
    }
}
