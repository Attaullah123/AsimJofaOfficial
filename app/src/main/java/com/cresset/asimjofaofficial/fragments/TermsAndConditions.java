package com.cresset.asimjofaofficial.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.adapter.PolicyExpandAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by attaullahkhizar on 9/13/17.
 */

public class  TermsAndConditions extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    View rootView;
    private int lastExpandedPosition = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.terms_conditions, container,false);

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
        listDataHeader.add("ACCURACY OF CONTENT");
        listDataHeader.add("ORDER ACCEPTANCE");
        listDataHeader.add("ACKNOWLEDGEMENT");
        listDataHeader.add("DELAYS");
        listDataHeader.add("ORDER PLACEMENT");

        // Adding child data
        List<String> Accuracy = new ArrayList<String>();
        Accuracy.add("We have taken every care in the preparation of the content of this website, in particular to ensure that prices quoted" +
                "are correct at time of publishing and all products have been fairly described. However, orders will only be accepted" +
                "if there are no material errors in the description of the goods or their prices as advertised on this website. Packaging may vary" +
                "from that shown. The weights, dimensions and capacities given are approximate only. We have made every effort to display as accurately" +
                "as possible the colors of our products that appear on the website. However, as the actual colors you see will depend on your monitor," +
                "we cannot guarantee that your monitors display of any color will accurately reflect the color of the product on delivery.");

        List<String> Order = new ArrayList<String>();
        Order.add("The acceptation of your order begins with the delivery of an e-mail and subsequent phone call for\n" +
                "confirmation of your order contents and relevant details");

        List<String> Ack = new ArrayList<String>();
        Ack.add("If you have supplied us with your email address, we will notify you by email as earliest as possible to confirm receipt of your order.\n" +
                "Our acceptance of your order will take place upon dispatch of the product(s) ordered.");

        List<String> Delay = new ArrayList<String>();
        Delay.add("We shall try our level best to make deliveries in the most reasonable time possible. But order deliveries\n" +
                "may be delayed in case of certain unexpected situations; we do not assume\n" +
                "any responsibility of delayed deliveries, though it does everything in its power to avoid them.");

        List<String> OrderPlacement = new ArrayList<String>();
        OrderPlacement.add("1. International customers can make payment through bank transfer. The order that you place is kept on hold for 36 hours after \n" +
                "you receive a confirmation email / call from us about the order and payment details. If you plan to make payment after 36 hours of\n" +
                "receiving the confirmation, please confirm it with us if the products that you have ordered are still in stock.\n" +
                "2. The Asim Jofa Store(s) takes no guarantee of the products availability that you had ordered, if you are making the payment after\n" +
                "the given timeframe. Though our efforts will be to process your initial order, but if any of the ordered products is out of stock\n" +
                "you will be offered to choose any other product/design/color\n" +
                "(within the same price range). We will not be held responsible if there are delays in delivery due to out of stock products.\n" +
                "3. If we get no confirmation from the customer, the order might be cancelled without any notification.\n" +
                "4. The International order is processed only after the international customer makes the payment.\n" +
                "5. The customers within Pakistan can place the order online with confirmation for payment upon delivery.");

        listDataChild.put(listDataHeader.get(0), Accuracy); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Order);
        listDataChild.put(listDataHeader.get(2), Ack);
        listDataChild.put(listDataHeader.get(3), Delay);
        listDataChild.put(listDataHeader.get(4), OrderPlacement);
    }

}
