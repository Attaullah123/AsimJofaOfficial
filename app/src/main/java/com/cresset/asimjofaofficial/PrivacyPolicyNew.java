package com.cresset.asimjofaofficial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.cresset.asimjofaofficial.adapter.PolicyExpandAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class PrivacyPolicyNew extends Fragment {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    View rootView;
    private int lastExpandedPosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.privacy_policy_new, container,false);

        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new PolicyExpandAdapter(getActivity().getApplicationContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setGroupIndicator(null);

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        return rootView;
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("PRIVACY POLICY");
        listDataHeader.add("PASSWORD AND SECURITY");
        listDataHeader.add("DAMAGE TO YOUR COMPUTER");
        listDataHeader.add("CONSENT");
        listDataHeader.add("MODIFICATION");
        listDataHeader.add("INTELLECTUAL PROPERTY");
        listDataHeader.add("USE");

        // Adding child data
        List<String> Privacy = new ArrayList<String>();
        Privacy.add("This policy covers how we use your personal information. We take your privacy seriously and will take all measures to protect your personal information." +
                "Any personal information received will only be used to fulfill your order, and may be used for internal analytical purposes. We will not sell or " +
                "redistribute your information to anyone");

        List<String> Password = new ArrayList<String>();
        Password.add("You are responsible for maintaining the confidentiality of your password and account and any activities that occur under your account. We shall not be liable" +
                "to any person for any loss or damage which may arise as a result of any failure by you to protect your password or account.");

        List<String> Damage = new ArrayList<String>();
        Damage.add("We have made and continue to make every effort to ensure that this website is free from viruses or defects. However, we cannot guarantee" +
                "that your use of this website or any websites accessible through it won't cause damage to your computer. It is your own responsibility " +
                "to ensure that the right equipment is available to use the website and screen out anything that may damage it. " +
                "We shall not be liable to any person for any loss or damage which may arise to computer equipment as a result of using this website.");

        List<String> Consent = new ArrayList<String>();
        Consent.add("By using our website you hereby consent to our privacy policy and agree to the terms and conditions set out herein.");

        List<String> Modification = new ArrayList<String>();
        Modification.add("We may amend or modify the products detailed, contents and/or the terms" +
                " and conditions of this website and your access and use thereof as may be necessary from time to time.");

        List<String> Intelactual = new ArrayList<String>();
        Intelactual.add("You acknowledge and agree that all copyright, software, designs, trademarks and other intellectual property and material rights relating our products " +
                "and this website as detailed on this website shall remain at all times our property. All such intellectual property is protected by federal law. " +
                "This intellectual property and content herein contained may be accessed by you for non-commercial purposes only. You are not permitted to " +
                "copy, modify, publish, transfer, create from, commercially exploit otherwise use, in whole or in part, any software, products and/or services " +
                "herein contained/detailed.");
        List<String> Use = new ArrayList<String>();
        Use.add("This website may not be used in any manner that could damage, disable, overburden or impair this website or products contained herein.");

        listDataChild.put(listDataHeader.get(0), Privacy); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Password);
        listDataChild.put(listDataHeader.get(2), Damage);
        listDataChild.put(listDataHeader.get(3), Consent);
        listDataChild.put(listDataHeader.get(4), Modification);
        listDataChild.put(listDataHeader.get(5), Intelactual);
        listDataChild.put(listDataHeader.get(6), Use);
    }

}
