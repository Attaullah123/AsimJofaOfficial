package com.cresset.asimjofaofficial.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.SendMailDialog;
import com.cresset.asimjofaofficial.volley.SizeDialogFragment;

/**
 * Created by attaullahkhizar on 8/25/17.
 */

public class OnlineFragment extends Fragment {
    private LinearLayout byEmai,facebook,twitter,instagram;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.online_fragment, viewGroup, false);

        byEmai = (LinearLayout) view.findViewById(R.id.by_email);
        facebook = (LinearLayout) view.findViewById(R.id.facebook_anchor);
        twitter = (LinearLayout) view.findViewById(R.id.twitter_anchor);
        instagram = (LinearLayout) view.findViewById(R.id.instagram_anchor);
        //output= (TextView)view.findViewById(R.id.textView);
        //output.setText("Fragment Two");

        byEmai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){

                    case R.id.by_email:
                        android.support.v4.app.FragmentManager fm = getFragmentManager();
                        SendMailDialog dialogFragment = new SendMailDialog();
                        dialogFragment.show(fm, "Sample Fragment");
                        break;
                }

            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fbIntent = new Intent(Intent.ACTION_VIEW);
                fbIntent.setData(Uri.parse("https://www.facebook.com/asimjofaofficial/?fref=ts"));
                startActivity(fbIntent);

            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW);
                twitterIntent.setData(Uri.parse("https://twitter.com/AsimJofa"));
                startActivity(twitterIntent);

            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instaIntent = new Intent(Intent.ACTION_VIEW);
                instaIntent.setData(Uri.parse("https://www.instagram.com/asimjofa/"));
                startActivity(instaIntent);

            }
        });


        return view;
    }
}
