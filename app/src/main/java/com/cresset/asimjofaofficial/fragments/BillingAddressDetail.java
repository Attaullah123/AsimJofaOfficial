package com.cresset.asimjofaofficial.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.MyAccount;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.adapter.BillingCountrySpinnerAdapter;
import com.cresset.asimjofaofficial.adapter.BillingStateSpinnerAdapter;
import com.cresset.asimjofaofficial.models.BillingModel;
import com.cresset.asimjofaofficial.models.BillingShippingDetailModel;
import com.cresset.asimjofaofficial.models.BillingShippingModel;
import com.cresset.asimjofaofficial.models.CountryList;
import com.cresset.asimjofaofficial.models.CountryModel;
import com.cresset.asimjofaofficial.models.CustomerAddressModel;
import com.cresset.asimjofaofficial.models.CustomerDetailModel;
import com.cresset.asimjofaofficial.models.GuestOrLoginResponseModel;
import com.cresset.asimjofaofficial.models.ShippingModel;
import com.cresset.asimjofaofficial.models.StateList;
import com.cresset.asimjofaofficial.models.StateModel;
import com.cresset.asimjofaofficial.models.UpdateBillingAndShippingModel;
import com.cresset.asimjofaofficial.models.UserDetailModel;
import com.cresset.asimjofaofficial.models.UserModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.volley.AppController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
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
    //private BillingCountrySpinnerAdapter billingCountrySpinnerAdapter;
   // private BillingStateSpinnerAdapter billingStateSpinnerAdapter;
    private int CountryId,StateId;
    private ProgressDialog progressDialog;
    private CountryList countryListItem;
    private StateList stateListItem;
    private TextView billingId;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.billing_address_detail, container, false);
        //edittext view
        bfullname = (EditText) view.findViewById(R.id.billing_input_full_name);
        billingId = (TextView) view.findViewById(R.id.billing_input_id);
        bEmail = (EditText) view.findViewById(R.id.billing_input_email);
        bAdress = (EditText) view.findViewById(R.id.billing_input_Address);
        bPhoneNo = (EditText) view.findViewById(R.id.billing_input_phone);
        bCity = (EditText) view.findViewById(R.id.billing_input_city);
        bPostalCode = (EditText) view.findViewById(R.id.billing_input_zipcode);

//        bDay = (EditText) view.findViewById(R.id.billing_birthday_day);
//        bMonth = (EditText) view.findViewById(R.id.billing_birthday_month);
//        bYear = (EditText) view.findViewById(R.id.billing_birthday_year);
        //spinner view
        bCountry = (Spinner) view.findViewById(R.id.billing_country_select);
        bProvince = (Spinner) view.findViewById(R.id.billing_spinner_select_province);
        btnBillingAddress = (Button)view.findViewById(R.id.billing_save);

        getUserInfo();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("please wait...");
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
//                String day = bDay.getText().toString().trim();
//                String month = bMonth.getText().toString().trim();
//                String year = bYear.getText().toString().trim();
                //String city = bCity.getText().toString().trim();


                if (!fname.isEmpty() && !laddress.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !city.isEmpty()&& !postCode.isEmpty()){
                    //billingAddressUpdate();
                    BillingShippingDetailModel model = new BillingShippingDetailModel();
                    model.setId(Integer.parseInt(billingId.getText().toString()));
                    model.setFullName(fname);
                    model.setEmail(email);
                    model.setCity(city);
                    model.setAddress1(laddress);
                    model.setZipPostalCode(postCode);
                    model.setPhoneNumber(phone);
                    model.setCountryId(GlobalClass.billingModel.getCountryId());
                    model.setStateProvinceId(GlobalClass.billingModel.getStateProvinceId());

                    updateBillingAddress(model);
                }else {
                    Toast.makeText(getContext(), "Please enter the required fields!", Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }

    public void getUserInfo() {
        //progressBar.setVisibility(View.VISIBLE);
        //progressDialog.show();
        //creating json array list
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());
        params.put("IsBilling", "true");

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_BILLING_SHIPPING_GET, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response11", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                       // progressDialog.dismiss();

//                        Gson gson = new Gson();
//                        BillingShippingModel userDetailModel = gson.fromJson(response.toString(), new TypeToken<BillingShippingModel>(){}.getType());
//
//                        final CustomerAddressModel customerDetailModel = userDetailModel.getCustomerAddress();
//                        String userAddress =customerDetailModel.getAddress1();
//                        //String userCity =customerDetailModel.getEmail();
//                        String userName =customerDetailModel.getFirstName();
//                        String userEmail =customerDetailModel.getEmail();
//                        String userPhone=customerDetailModel.getPhoneNumber();
//                        String userCity =customerDetailModel.getCity();
//                        String userPostalCode = customerDetailModel.getZipPostalCode();
//
//                        bfullname.setText(userName);
//                        bEmail.setText(userEmail);
//                        bAdress.setText(userAddress);
//                        bPhoneNo.setText(userPhone);
//                        bCity.setText(userCity);
//                        bPostalCode.setText(userPostalCode);
//
//                        BillingModel model = new BillingModel();
//                        model.setFullName(customerDetailModel.getFirstName());
//                        model.setEmail(customerDetailModel.getEmail());
//                        model.setCity(customerDetailModel.getCity());
//                        model.setAddress1(customerDetailModel.getAddress1());
//                        model.setZipPostalCode(customerDetailModel.getZipPostalCode());
//                        model.setPhoneNumber(customerDetailModel.getPhoneNumber());
//                        model.setCountryId(customerDetailModel.getCountryId());
//                        model.setStateProvinceId(customerDetailModel.getStateProvinceId());
//                        billingId.setText(customerDetailModel.getId());
//
//                        GlobalClass.billingModel = model;



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                //progressDialog.dismiss();
                //progressBar.setVisibility(View.GONE);
                //progressDialog.dismiss();
            }
        });

        CustomVolleyRequest.getInstance(getContext()).getRequestQueue().add(objectRequest);
    }

    public void updateBillingAddress(final BillingShippingDetailModel billingAddress) {
        //progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        //creating json array list
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);

        UpdateBillingAndShippingModel model = new UpdateBillingAndShippingModel();
        model.setProjectID(Config.PROJECTID);
        model.setBillingAddress(billingAddress);

        Gson gson = new Gson();
        String json = gson.toJson(model);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_UPDATE_BILLING_DETAIL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        Gson gson = new Gson();
                        //BillingShippingModel userDetailModel = gson.fromJson(response.toString(), new TypeToken<BillingShippingModel>(){}.getType());

                        BillingModel model = new BillingModel();
                        model.setFullName(billingAddress.getFullName());
                        model.setEmail(billingAddress.getEmail());
                        model.setCity(billingAddress.getCity());
                        model.setAddress1(billingAddress.getAddress1());
                        model.setZipPostalCode(billingAddress.getZipPostalCode());
                        model.setPhoneNumber(billingAddress.getPhoneNumber());
                        model.setCountryId(GlobalClass.shippingModel.getCountryId());
                        model.setStateProvinceId(GlobalClass.shippingModel.getStateProvinceId());

                        GlobalClass.billingModel = model;
                        Toast.makeText(getContext(), "Info save successfully!", Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                //  progressDialog.dismiss();
                //progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(objectRequest);
    }

}