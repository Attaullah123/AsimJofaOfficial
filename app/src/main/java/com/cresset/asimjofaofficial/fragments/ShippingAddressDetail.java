package com.cresset.asimjofaofficial.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.adapter.ShippingCountrySpinnerAdapter;
import com.cresset.asimjofaofficial.adapter.ShippingStateSpinnerAdapter;
import com.cresset.asimjofaofficial.models.BillingShippingModel;
import com.cresset.asimjofaofficial.models.CountryList;
import com.cresset.asimjofaofficial.models.CountryModel;
import com.cresset.asimjofaofficial.models.CustomerAddressModel;
import com.cresset.asimjofaofficial.models.ShippingModel;
import com.cresset.asimjofaofficial.models.StateList;
import com.cresset.asimjofaofficial.models.StateModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.volley.AppController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by attaullahkhizar on 8/29/17.
 */

public class ShippingAddressDetail extends Fragment {

    private EditText sfullname,sEmail,sAdress,sPhoneNo,sCity,sPostalCode,sDay,sMonth,sYear;
    private Spinner sCountry,sProvince;
    private Button btnShippingAddress;
    private ShippingModel shippingModel;
    private CountryList countryListItem;
    private StateList stateListItem;
    View view;
    private ShippingCountrySpinnerAdapter shippingCountrySpinnerAdapter;
    private ShippingStateSpinnerAdapter shippingStateSpinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shipping_address_detail, container, false);
        //edittext view
        sfullname = (EditText) view.findViewById(R.id.shipping_input_full_name);
        sEmail = (EditText) view.findViewById(R.id.shipping_input_email);
        sAdress = (EditText) view.findViewById(R.id.shipping_input_Address);
        sPhoneNo = (EditText) view.findViewById(R.id.shipping_input_phone);
        sCity = (EditText) view.findViewById(R.id.shipping_input_city);
        sPostalCode = (EditText) view.findViewById(R.id.shipping_input_zipcode);
        //spinner view
        sCountry = (Spinner) view.findViewById(R.id.shipping_size_country_select);
        sProvince = (Spinner) view.findViewById(R.id.shipping_spinner_select_province);
        btnShippingAddress = (Button)view.findViewById(R.id.shipping_save);

        sDay = (EditText) view.findViewById(R.id.shipping_birthday_day);
        sMonth = (EditText) view.findViewById(R.id.shipping_birthday_month);
        sYear = (EditText) view.findViewById(R.id.shipping_birthday_year);

        //CountryList();

        getShippingDetail();

//        btnShippingAddress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String fname = sfullname.getText().toString().trim();
//                String laddress = sAdress.getText().toString().trim();
//                String email =sEmail.getText().toString().trim();
//                String phone = sPhoneNo.getText().toString().trim();
//                String city = sCity.getText().toString().trim();
//                String postCode = sPostalCode.getText().toString().trim();
//                //String city = bCity.getText().toString().trim();
//                String day = sDay.getText().toString().trim();
//                String month = sMonth.getText().toString().trim();
//                String year = sYear.getText().toString().trim();
//
//                Log.d("fname ", fname);
//                Log.d("laddress ", laddress);
//                Log.d("email ", email);
//                Log.d("phone ", phone);
//                Log.d("city ", city);
//                Log.d("postCode ", postCode);
//
////                if (!fname.isEmpty() && !laddress.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !city.isEmpty()&& !postCode.isEmpty()
////                        && !day.isEmpty() && !month.isEmpty() && !year.isEmpty()){
////
////                    shippingAddress();
////                    Toast.makeText(getContext(), "Info save successfully! Also Add Billing Address", Toast.LENGTH_LONG).show();
////
//////                    BillingAddress billingAddress=new BillingAddress();
//////                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
//////                    transaction.replace(R.id.frame_content,billingAddress); // give your fragment container id in first parameter
//////                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//////                    transaction.commit();
////
////
////                }else {
////                    Toast.makeText(getContext(), "Please enter the required fields!", Toast.LENGTH_LONG).show();
////                }
////
////
////            }
////        });
        return view;
    }

    public void getShippingDetail() {
        //progressBar.setVisibility(View.VISIBLE);
//        progressDialog.show();
        //creating json array list
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());
        params.put("IsBilling", "false");

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_BILLING_SHIPPING_GET, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        //progressDialog.dismiss();

                        Gson gson = new Gson();
                        BillingShippingModel userDetailModel = gson.fromJson(response.toString(), new TypeToken<BillingShippingModel>(){}.getType());

                        final CustomerAddressModel customerDetailModel = userDetailModel.getCustomerAddress();
                        String userAddress =customerDetailModel.getAddress1();
                        String userCity =customerDetailModel.getCity();
                        String userName =customerDetailModel.getFirstName();
                        String userEmail =customerDetailModel.getEmail();
                        String userPhone=customerDetailModel.getPhoneNumber();
                        //String userEmail =customerDetailModel.getEmail();

                        sfullname.setText(userName);
                        sEmail.setText(userEmail);
                        sAdress.setText(userAddress);
                        sPhoneNo.setText(userPhone);
                        sCity.setText(userCity);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                //  progressDialog.dismiss();
                //progressBar.setVisibility(View.GONE);
                //progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(objectRequest);

    }
}
