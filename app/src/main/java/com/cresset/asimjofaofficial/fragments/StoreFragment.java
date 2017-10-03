package com.cresset.asimjofaofficial.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cresset.asimjofaofficial.R;

/**
 * Created by attaullahkhizar on 8/25/17.
 */

public class StoreFragment extends Fragment {
    private TextView callOnclick;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, viewGroup, false);
        //output= (TextView)view.findViewById(R.id.textView);
        //output.setText("Fragment Two");
        callOnclick = (TextView) view.findViewById(R.id.call_onclick);

        callOnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+92-321-1244440"));
                startActivity(intent);
            }
        });
        return view;
    }
}
