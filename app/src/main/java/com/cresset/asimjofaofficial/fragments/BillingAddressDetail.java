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
import com.cresset.asimjofaofficial.models.BillingShippingModel;
import com.cresset.asimjofaofficial.models.CountryList;
import com.cresset.asimjofaofficial.models.CountryModel;
import com.cresset.asimjofaofficial.models.CustomerAddressModel;
import com.cresset.asimjofaofficial.models.CustomerDetailModel;
import com.cresset.asimjofaofficial.models.GuestOrLoginResponseModel;
import com.cresset.asimjofaofficial.models.StateList;
import com.cresset.asimjofaofficial.models.StateModel;
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
    //private Button btnBillingAddress;
    private BillingModel billingModel;
    private BillingCountrySpinnerAdapter billingCountrySpinnerAdapter;
    private BillingStateSpinnerAdapter billingStateSpinnerAdapter;
    private int CountryId,StateId;
    private ProgressDialog progressDialog;
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
        //btnBillingAddress = (Button)view.findViewById(R.id.billing_save);

        getUserInfo();
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setCancelable(false);
//        progressDialog.setTitle("please wait...");
        //loadData();

//        btnBillingAddress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String fname = bfullname.getText().toString().trim();
//                String laddress = bAdress.getText().toString().trim();
//                String email = bEmail.getText().toString().trim();
//                String phone = bPhoneNo.getText().toString().trim();
//                String city = bCity.getText().toString().trim();
//                String postCode = bPostalCode.getText().toString().trim();
//                String day = bDay.getText().toString().trim();
//                String month = bMonth.getText().toString().trim();
//                String year = bYear.getText().toString().trim();
//                //String city = bCity.getText().toString().trim();
//
//
//                if (!fname.isEmpty() && !laddress.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !city.isEmpty()&& !postCode.isEmpty()
//                        && !day.isEmpty() && !month.isEmpty() && !year.isEmpty()){
//                    //billingAddressUpdate();
//                    Toast.makeText(getContext(), "Your info save successfully!", Toast.LENGTH_LONG).show();
//                    getActivity().finish();
//                }else {
//                    Toast.makeText(getContext(),
//                            "Please enter the required fields!", Toast.LENGTH_LONG)
//                            .show();
//                }
//
//            }
//        });
        return view;
    }

    public void getUserInfo() {
        //progressBar.setVisibility(View.VISIBLE);
//        progressDialog.show();
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
                        //progressDialog.dismiss();

                        Gson gson = new Gson();
                        BillingShippingModel userDetailModel = gson.fromJson(response.toString(), new TypeToken<BillingShippingModel>(){}.getType());

                        final CustomerAddressModel customerDetailModel = userDetailModel.getCustomerAddress();
                        String userAddress =customerDetailModel.getAddress1();
                        //String userCity =customerDetailModel.getEmail();
                        String userName =customerDetailModel.getFirstName();
                        String userEmail =customerDetailModel.getEmail();
                        String userPhone=customerDetailModel.getPhoneNumber();
                        String userCity =customerDetailModel.getCity();
                        bfullname.setText(userName);
                        bEmail.setText(userEmail);
                        bAdress.setText(userAddress);
                        bPhoneNo.setText(userPhone);
                        bCity.setText(userCity);


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

        CustomVolleyRequest.getInstance(getContext()).getRequestQueue().add(objectRequest);
    }

//    public void billingAddressUpdate(final String fname, final String lname, final String email, final String password,final String confirmPassword,
//                             final String day, final String month,final String year){
//        progressDialog.show();
//        HashMap<String,String> params = new HashMap<>();
//        params.put("ProjectId",Config.PROJECTID);
//        params.put("FirstName", fname);
//        params.put("LastName", lname);
//        params.put("Email", email);
//        params.put("Pasword", password);
//        params.put("Day", day);
//        params.put("month", month);
//        params.put("year", year);
//
//        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
//        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
//
//        params.put("IpAddress", ip);
//
//        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_USER_REGISTER, new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try{
//
//                            JSONObject jObj = new JSONObject(response.toString());
//                            String result = jObj.getString("Status");
//                            progressDialog.dismiss();
//
//                            Gson gson = new Gson();
//                            GuestOrLoginResponseModel model = gson.fromJson(response.toString(), new TypeToken<GuestOrLoginResponseModel>(){}.getType());
//
//                            //show("Customer id:" + model.getCustomerId());
//
//                            if (model.getCustomerId() != null && model.getCustomerId() != "") {
//
//                                UserModel userData = new UserModel();
//                                userData.setUserID(model.getCustomerId());
//                                userData.setUserName(fname);
//                                userData.setEmail(email);
//
//                                userData.setBirthdayDay(day);
//                                userData.setBirthdayMonth(month);
//                                userData.setBirthdayYear(year);
//
//                                userData.setGuest(false);
//
//                                GlobalClass.userData = userData;
//                                String json = gson.toJson(userData);
//                                sharedPreferencesEditor.putString(Config.RegisteredPreference,json);
//                                sharedPreferencesEditor.commit();
//
//                                show("Registered successfully!");
//                            }
//
//                            // Launch login activity
//                            Intent intent = new Intent(getApplicationContext(), MyAccount.class);
//                            startActivity(intent);
//                            finish();
//
//                        }catch (JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Couldn't register user, please check connection", Toast.LENGTH_SHORT).show();
//                Log.d("Error", error.toString());
//                progressDialog.dismiss();
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(objectRequest);
//    }
//
//    public void show(String message){
//        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
//    }


}