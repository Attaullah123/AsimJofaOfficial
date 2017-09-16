package com.cresset.asimjofaofficial.volley;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cresset.asimjofaofficial.R;

/**
 * Created by attaullahkhizar on 8/30/17.
 */

public class SizeDialogFragment extends DialogFragment {
    //private Button cancel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.size_dialog_fragment, container, false);
        getDialog().setTitle("Simple Dialog");

//        cancel = (Button) rootView.findViewById(R.id.close_button);
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        return rootView;
    }
}
