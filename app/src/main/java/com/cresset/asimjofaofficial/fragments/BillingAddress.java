package com.cresset.asimjofaofficial.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.cresset.asimjofaofficial.adapter.BillingCountrySpinnerAdapter;
import com.cresset.asimjofaofficial.adapter.BillingStateSpinnerAdapter;
import com.cresset.asimjofaofficial.adapter.SizeSpinnerAdapter;
import com.cresset.asimjofaofficial.models.BillingModel;
import com.cresset.asimjofaofficial.models.CartModel;
import com.cresset.asimjofaofficial.models.CountryList;
import com.cresset.asimjofaofficial.models.CountryModel;
import com.cresset.asimjofaofficial.models.ProductAddons;
import com.cresset.asimjofaofficial.models.ProductDetailSize;
import com.cresset.asimjofaofficial.models.StateList;
import com.cresset.asimjofaofficial.models.StateModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.volley.AppController;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BillingAddress extends android.support.v4.app.Fragment{

    private EditText bfullname,bEmail,bAdress,bPhoneNo,bCity,bPostalCode,bDay,bMonth,bYear;
    private Spinner bCountry,bProvince;
    private Button btnBillingAddress;
    private BillingModel billingModel;
    private BillingCountrySpinnerAdapter billingCountrySpinnerAdapter;
    private BillingStateSpinnerAdapter billingStateSpinnerAdapter;
    private int CountryId,StateId;
    private CountryList countryListItem;
    private ProgressDialog progressDialog;
    private StateList stateListItem;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.billing_address, container, false);
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

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("please wait...");
        CountryList();

        loadData();

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


                if (!fname.isEmpty() && !laddress.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !city.isEmpty()&& !postCode.isEmpty()
                        && !day.isEmpty() && !month.isEmpty() && !year.isEmpty()){
                    billingAddress();
                    Toast.makeText(getContext(), "Your info save successfully!", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }else {
                    Toast.makeText(getContext(),
                            "Please enter the required fields!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
        return view;
    }

    public void CountryList(){
        HashMap<String, String> params = new HashMap<>();
        params.put("ProjectId",Config.PROJECTID);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_Country_List, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    Log.d("Response", response.toString());

                    Gson gson = new Gson();
                    CountryModel countryModel= gson.fromJson(response.toString(), new TypeToken<CountryModel>(){}.getType());

                    CountrySpinner(countryModel.getCountryList());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Couldn't get countries, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest);
    }

    public void CountrySpinner(final List<CountryList> countryList){
        ArrayList<CountryList> contList = new ArrayList<CountryList>(countryList);

        billingCountrySpinnerAdapter = new BillingCountrySpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, contList );
        bCountry.setAdapter(billingCountrySpinnerAdapter);
        bCountry.setPrompt("Select Country");

        if (GlobalClass.shippingModel!= null){
            if (!GlobalClass.shippingModel.getCountryId().equals(null))
            {
                for(CountryList country : contList){
                    if(country.getId() == GlobalClass.shippingModel.getCountryId()){
                        int spinnerPosition = billingCountrySpinnerAdapter.getPosition(country);
                        bCountry.setSelection(spinnerPosition);
                    }
                }
            }
        }

        bCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                countryListItem = (CountryList)bCountry.getSelectedItem();
                //Toast.makeText(getContext(),"selected country : " +countryListItem.getName(), Toast.LENGTH_SHORT).show();

                StateList(countryListItem.getId());
                Log.i("Selected country : ", countryListItem.getName().toString() + " " + countryListItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

    }

    public void StateList(final String countryId){
        Log.d("stateModel", countryId.toString());

        //Toast.makeText(getContext(),"selected country : " +countryId, Toast.LENGTH_SHORT).show();
        HashMap<String, String> params = new HashMap<>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CountryId", countryId);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_State_List, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("stateModel", response.toString());

                        Gson gson = new Gson();
                        StateModel stateModel= gson.fromJson(response.toString(), new TypeToken<StateModel>(){}.getType());

                        StateSpinner(stateModel.getStateList(),countryId);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Couldn't get states, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest);
    }

    public void StateSpinner(List<StateList> stateList,String countryId){
        ArrayList<StateList> statList = new ArrayList<StateList>(stateList);
        billingStateSpinnerAdapter = new BillingStateSpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, statList);
        bProvince.setAdapter(billingStateSpinnerAdapter);
        bProvince.setPrompt("Select State");

        if (GlobalClass.shippingModel!= null){
            if (!GlobalClass.shippingModel.getCountryId().equals(null))
            {
                for(StateList state : statList){
                    if(countryId == GlobalClass.shippingModel.getCountryId())
                        if(String.valueOf(state.getId()) == GlobalClass.shippingModel.getStateProvinceId()){
                            int spinnerPosition = billingStateSpinnerAdapter.getPosition(state);
                            bProvince.setSelection(spinnerPosition);
                        }
                }
            }
        }

        bProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                stateListItem = (StateList) bProvince.getSelectedItem();
                Log.i("Selected item : ", stateListItem.getName().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }
    public void loadData(){
        if(GlobalClass.billingModel != null){
            bfullname.setText(GlobalClass.billingModel.getFullName());
            bEmail.setText(GlobalClass.billingModel.getEmail());
            bCity.setText(GlobalClass.billingModel.getCity());
            bAdress.setText(GlobalClass.billingModel.getAddress1());
            bPhoneNo.setText(GlobalClass.billingModel.getPhoneNumber());
            bCity.setText(GlobalClass.billingModel.getCity());
            bPostalCode.setText(GlobalClass.billingModel.getZipPostalCode());

            bDay.setText(GlobalClass.billingModel.getBirthdayDay());
            bMonth.setText(GlobalClass.billingModel.getBirthdayMonth());
            bYear.setText(GlobalClass.billingModel.getBirthdayYear());


        }
    }
    public void billingAddress(){
        billingModel = new BillingModel();
        billingModel.setFullName(bfullname.getText().toString());
        billingModel.setEmail(bEmail.getText().toString());
        billingModel.setCountryId(countryListItem.getId().toString());
        billingModel.setStateProvinceId(String.valueOf(stateListItem.getId()));
        billingModel.setCity(bCity.getText().toString());
        billingModel.setAddress1(bAdress.getText().toString());
        billingModel.setZipPostalCode(bPostalCode.getText().toString());
        billingModel.setPhoneNumber(bPhoneNo.getText().toString());

        billingModel.setBirthdayDay(bDay.getText().toString());
        billingModel.setBirthdayMonth(bMonth.getText().toString());
        billingModel.setBirthdayYear(bYear.getText().toString());
        String birthday = bYear.getText().toString() + "-" + bMonth.getText().toString() + "-" + bDay.getText().toString();
        billingModel.setDOB(birthday);
        GlobalClass.billingModel = billingModel;
    }

}
