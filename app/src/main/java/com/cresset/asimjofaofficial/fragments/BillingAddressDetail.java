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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.adapter.BillingCountrySpinnerAdapter;
import com.cresset.asimjofaofficial.adapter.BillingStateSpinnerAdapter;
import com.cresset.asimjofaofficial.models.BillingModel;
import com.cresset.asimjofaofficial.models.CountryList;
import com.cresset.asimjofaofficial.models.CountryModel;
import com.cresset.asimjofaofficial.models.CustomerDetailModel;
import com.cresset.asimjofaofficial.models.StateList;
import com.cresset.asimjofaofficial.models.StateModel;
import com.cresset.asimjofaofficial.models.UserDetailModel;
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


public class BillingAddressDetail extends Fragment {
    private EditText bfullname,bEmail,bAdress,bPhoneNo,bCity,bPostalCode,bDay,bMonth,bYear;
    private Spinner bCountry,bProvince;
    private Button btnBillingAddress;
    private BillingModel billingModel;
    private BillingCountrySpinnerAdapter billingCountrySpinnerAdapter;
    private BillingStateSpinnerAdapter billingStateSpinnerAdapter;
    private int CountryId,StateId;
    private CountryList countryListItem;
    private StateList stateListItem;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.billing_address_detail, container, false);
        //edittext view
        bfullname = (EditText) view.findViewById(R.id.billing_input_full_name);
        bEmail = (EditText) view.findViewById(R.id.billing_input_email);
        bAdress = (EditText) view.findViewById(R.id.billing_input_Address);
        bPhoneNo = (EditText) view.findViewById(R.id.billing_input_phone);
        bCity = (EditText) view.findViewById(R.id.billing_input_city);
        bPostalCode = (EditText) view.findViewById(R.id.billing_input_zipcode);

        bDay = (EditText) view.findViewById(R.id.billing_birthday_day);
        bMonth = (EditText) view.findViewById(R.id.billing_birthday_month);
        bYear = (EditText) view.findViewById(R.id.billing_birthday_year);
        //spinner view
        bCountry = (Spinner) view.findViewById(R.id.billing_country_select);
        bProvince = (Spinner) view.findViewById(R.id.billing_spinner_select_province);
        btnBillingAddress = (Button)view.findViewById(R.id.billing_save);

        getUserInfo();

        //loadData();

        btnBillingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = bfullname.getText().toString().trim();
                String laddress = bAdress.getText().toString().trim();
                String email = bEmail.getText().toString().trim();
                String phone = bPhoneNo.getText().toString().trim();
                String city = bCity.getText().toString().trim();
                String postCode = bPostalCode.getText().toString().trim();

                String day = bDay.getText().toString().trim();
                String month = bMonth.getText().toString().trim();
                String year = bYear.getText().toString().trim();
                //String city = bCity.getText().toString().trim();


//                if (!fname.isEmpty() && !laddress.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !city.isEmpty()&& !postCode.isEmpty()
//                        && !day.isEmpty() && !month.isEmpty() && !year.isEmpty()){
//                    billingAddress();
//                    Toast.makeText(getContext(), "Your info save successfully!", Toast.LENGTH_LONG).show();
//                    getActivity().finish();
//                }else {
//                    Toast.makeText(getContext(),
//                            "Please enter the required fields!", Toast.LENGTH_LONG)
//                            .show();
//                }

            }
        });
        return view;
    }

    public void getUserInfo() {
        //progressBar.setVisibility(View.VISIBLE);

        //creating json array list
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());
        params.put("IsBilling", "true");

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_BILLING_GET, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

//                        Gson gson = new Gson();
//                        UserDetailModel userDetailModel = gson.fromJson(response.toString(), new TypeToken<UserDetailModel>(){}.getType());
//
//                        final CustomerDetailModel customerDetailModel = userDetailModel.getCustomerDetail();
//                        String userName =customerDetailModel.getUserName();
//                        String userEmail =customerDetailModel.getEmail();
//                        etuserName.setText(userName);
//                        etuserEmail.setText(userEmail);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                //progressBar.setVisibility(View.GONE);
                //progressDialog.dismiss();
            }
        });

    }
}