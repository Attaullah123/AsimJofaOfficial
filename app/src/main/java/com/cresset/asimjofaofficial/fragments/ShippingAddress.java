package com.cresset.asimjofaofficial.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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
import com.cresset.asimjofaofficial.adapter.ShippingCountrySpinnerAdapter;
import com.cresset.asimjofaofficial.adapter.ShippingStateSpinnerAdapter;
import com.cresset.asimjofaofficial.models.CountryList;
import com.cresset.asimjofaofficial.models.CountryModel;
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


public class ShippingAddress extends android.support.v4.app.Fragment{
    private EditText sfullname,sEmail,sAdress,sPhoneNo,sCity,sPostalCode;
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
        view = inflater.inflate(R.layout.shipping_adress, container, false);
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

        CountryList();

        loadData();
        
        btnShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = sfullname.getText().toString().trim();
                String laddress = sAdress.getText().toString().trim();
                String email =sEmail.getText().toString().trim();
                String phone = sPhoneNo.getText().toString().trim();
                String city = sCity.getText().toString().trim();
                String postCode = sPostalCode.getText().toString().trim();
                //String city = bCity.getText().toString().trim();

                Log.d("fname ", fname);
                Log.d("laddress ", laddress);
                Log.d("email ", email);
                Log.d("phone ", phone);
                Log.d("city ", city);
                Log.d("postCode ", postCode);

                if (!fname.isEmpty() && !laddress.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !city.isEmpty()&& !postCode.isEmpty()){

                    shippingAddress();
                    Toast.makeText(getContext(), "Info save successfully! Also Add Billing Address", Toast.LENGTH_LONG).show();

//                    BillingAddress billingAddress=new BillingAddress();
//                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame_content,billingAddress); // give your fragment container id in first parameter
//                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                    transaction.commit();


                }else {
                    Toast.makeText(getContext(), "Please enter your remaining fields!", Toast.LENGTH_LONG).show();
                }


            }
        });
        return view;
    }


    public void CountryList(){
        HashMap<String, String> params = new HashMap<>();
        params.put("ProjectId", Config.PROJECTID);

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
        shippingCountrySpinnerAdapter = new ShippingCountrySpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, contList );
        sCountry.setAdapter(shippingCountrySpinnerAdapter);

        if (GlobalClass.shippingModel!= null){
            if (!GlobalClass.shippingModel.getCountryId().equals(null))
            {

                for(CountryList country : contList){
                    if(country.getId() == GlobalClass.shippingModel.getCountryId()){
                        int spinnerPosition = shippingCountrySpinnerAdapter.getPosition(country);
                        sCountry.setSelection(spinnerPosition);

                        show("Country pos: " + spinnerPosition);

                    }
                }
            }
        }


        sCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                countryListItem = (CountryList)sCountry.getSelectedItem();
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

        Toast.makeText(getContext(),"selected country : " +countryId, Toast.LENGTH_SHORT).show();
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
        shippingStateSpinnerAdapter = new ShippingStateSpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, statList);
        sProvince.setAdapter(shippingStateSpinnerAdapter);

        if (GlobalClass.shippingModel!= null){
            if (!GlobalClass.shippingModel.getCountryId().equals(null))
            {

                for(StateList state : statList){
                    if(countryId == GlobalClass.shippingModel.getCountryId())
                        if(String.valueOf(state.getId()) == GlobalClass.shippingModel.getStateProvinceId()){
                            int spinnerPosition = shippingStateSpinnerAdapter.getPosition(state);
                            sProvince.setSelection(spinnerPosition);
                            show("state pos: " + spinnerPosition);
                        }
                }
            }
        }

        sProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                stateListItem = (StateList) sProvince.getSelectedItem();
                Log.i("Selected item : ", stateListItem.getName().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public void loadData(){
        if(GlobalClass.shippingModel != null){
            sfullname.setText(GlobalClass.shippingModel.getFullName());
            sEmail.setText(GlobalClass.shippingModel.getEmail());
            sCity.setText(GlobalClass.shippingModel.getCity());
            sAdress.setText(GlobalClass.shippingModel.getAddress1());
            sPhoneNo.setText(GlobalClass.shippingModel.getPhoneNumber());
            sPostalCode.setText(GlobalClass.shippingModel.getZipPostalCode());
        }
    }
    public void shippingAddress(){
        shippingModel = new ShippingModel();
        shippingModel.setFullName(sfullname.getText().toString());
        shippingModel.setEmail(sEmail.getText().toString());
        shippingModel.setCountryId(countryListItem.getId().toString());
        shippingModel.setStateProvinceId(String.valueOf(stateListItem.getId()));
        shippingModel.setCity(sCity.getText().toString());
        shippingModel.setAddress1(sAdress.getText().toString());
        shippingModel.setZipPostalCode(sPostalCode.getText().toString());
        shippingModel.setPhoneNumber(sPhoneNo.getText().toString());

        GlobalClass.shippingModel = shippingModel;
    }

    public void show(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
