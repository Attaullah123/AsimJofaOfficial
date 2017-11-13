package com.cresset.asimjofaofficial.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.ShippingBillingAddress;
import com.cresset.asimjofaofficial.adapter.BillingCountrySpinnerAdapter;
import com.cresset.asimjofaofficial.adapter.BillingStateSpinnerAdapter;
import com.cresset.asimjofaofficial.adapter.ShippingCountrySpinnerAdapter;
import com.cresset.asimjofaofficial.adapter.ShippingStateSpinnerAdapter;
import com.cresset.asimjofaofficial.models.BillingModel;
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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShippingAddress extends android.support.v4.app.Fragment{
    private EditText sfullname,sEmail,sAdress,sPhoneNo,sPostalCode,sDay,sMonth,sYear;
    private EditText sCity;
    private SearchableSpinner sCountry,sProvince;
    private Button btnShippingAddress;
    private ShippingModel shippingModel;
    private CountryList countryListItem;
    private StateList stateListItem;
    View view;
    private ProgressDialog progressDialog;
    private ShippingCountrySpinnerAdapter shippingCountrySpinnerAdapter;
    private ShippingStateSpinnerAdapter shippingStateSpinnerAdapter;
    private CheckBox isBillingAddressSame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shipping_adress, container, false);
        //edittext view
        sfullname = (EditText) view.findViewById(R.id.shipping_input_full_name);
        sEmail = (EditText) view.findViewById(R.id.shipping_input_email);
        sAdress = (EditText) view.findViewById(R.id.shipping_input_Address);
        sPhoneNo = (EditText) view.findViewById(R.id.shipping_input_phone);
        sCity = (EditText) view.findViewById(R.id.shipping_input_city);
        sPostalCode = (EditText) view.findViewById(R.id.shipping_input_zipcode);
        //spinner view
        sCountry = (SearchableSpinner) view.findViewById(R.id.shipping_size_country_select);
        sProvince = (SearchableSpinner) view.findViewById(R.id.shipping_spinner_select_province);
        btnShippingAddress = (Button)view.findViewById(R.id.shipping_save);

        sDay = (EditText) view.findViewById(R.id.shipping_birthday_day);
        sMonth = (EditText) view.findViewById(R.id.shipping_birthday_month);
        sYear = (EditText) view.findViewById(R.id.shipping_birthday_year);

        isBillingAddressSame = (CheckBox) view.findViewById(R.id.same_billing);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("please wait...");
        CountryList();

//        String[] cityName = {"Lahore", "Karachi", "Faisalabad", "Rawalpindi", "Multan", "Hyderābād",
//                "Gujranwala", "Peshawar", "Rahim Yar Khan", "Quetta", "Sargodha", "Sialkot","Bahawalpur","Sukkur","Kandhkot","Shekhupura","Mardan","Gujrat",
//        "Larkana","Kasur",""};
//        ArrayAdapter<String> cityNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, cityName);
//
//        sCity.setThreshold(1);
//        sCity.setAdapter(cityNameAdapter);
        loadData();

        isBillingAddressSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    //countryListItem.isAllowsBilling();
                    if (countryListItem.isAllowsBilling()){
                        Toast.makeText(getContext(), "you select same billing address", Toast.LENGTH_LONG).show();
                        //countryListItem.setSelected(true);
                    }else {
                        Toast.makeText(getContext(), "you Un-select same billing address", Toast.LENGTH_LONG).show();
                        //countryListItem.setSelected(false);
                    }

                }
            }
        });
        
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
                String day = sDay.getText().toString().trim();
                String month = sMonth.getText().toString().trim();
                String year = sYear.getText().toString().trim();

                Log.d("fname ", fname);
                Log.d("laddress ", laddress);
                Log.d("email ", email);
                Log.d("phone ", phone);
                Log.d("city ", city);
                Log.d("postCode ", postCode);

                if (!fname.isEmpty() && !laddress.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !city.isEmpty()&& !postCode.isEmpty()
                        && !day.isEmpty() && !month.isEmpty() && !year.isEmpty()){


                    shippingAddress();
                    Toast.makeText(getContext(), "your address save successfully", Toast.LENGTH_LONG).show();

                    if(isBillingAddressSame.isChecked()){
                        ShippingBillingSame();
                        getActivity().finish();
                    }
                    else{
                        ((ShippingBillingAddress)getActivity()).navigateFragment(1);
                    }


                }else {
                    Toast.makeText(getContext(), "Please enter the required fields!", Toast.LENGTH_LONG).show();
                }


            }
        });
        return view;
    }


    public void CountryList(){
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("ProjectId", Config.PROJECTID);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_Country_List, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());
                        progressDialog.dismiss();

                        Gson gson = new Gson();
                        CountryModel countryModel= gson.fromJson(response.toString(), new TypeToken<CountryModel>(){}.getType());

                        CountrySpinner(countryModel.getCountryList());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Couldn't get countries, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                progressDialog.dismiss();
            }
        });
        objectRequest.setRetryPolicy(AppController.getDefaultRetryPolice());
        objectRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(objectRequest, Config.tag_json_obj);
    }

    public void CountrySpinner(final List<CountryList> countryList){
        ArrayList<CountryList> contList = new ArrayList<CountryList>(countryList);
        shippingCountrySpinnerAdapter = new ShippingCountrySpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, contList );
       // shippingCountrySpinnerAdapter.setDropDownViewResource(R.layout.spinner_billing_shipping_country);
        sCountry.setAdapter(shippingCountrySpinnerAdapter);
        sCountry.setTitle("Select Country");


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

       // Toast.makeText(getContext(),"selected country : " +countryId, Toast.LENGTH_SHORT).show();
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
        objectRequest.setRetryPolicy(AppController.getDefaultRetryPolice());
        objectRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(objectRequest, Config.tag_json_obj);
    }

    public void StateSpinner(List<StateList> stateList,String countryId){
        ArrayList<StateList> statList = new ArrayList<StateList>(stateList);
        shippingStateSpinnerAdapter = new ShippingStateSpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, statList);
        sProvince.setAdapter(shippingStateSpinnerAdapter);
        sProvince.setTitle("Select State");

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

            sDay.setText(GlobalClass.shippingModel.getBirthdayDay());
            sMonth.setText(GlobalClass.shippingModel.getBirthdayMonth());
            sYear.setText(GlobalClass.shippingModel.getBirthdayYear());
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

        shippingModel.setBirthdayDay(sDay.getText().toString());
        shippingModel.setBirthdayMonth(sMonth.getText().toString());
        shippingModel.setBirthdayYear(sYear.getText().toString());

        String birthday = sYear.getText().toString() + "-" + sMonth.getText().toString() + "-" + sDay.getText().toString();
        shippingModel.setDOB(birthday);
        GlobalClass.shippingModel = shippingModel;
    }

    public void ShippingBillingSame(){
        BillingModel model = new BillingModel();
        model.setFullName(sfullname.getText().toString());
        model.setEmail(sEmail.getText().toString());

        model.setCountryId(countryListItem.getId().toString());
        model.setStateProvinceId(String.valueOf(stateListItem.getId()));

        model.setCity(sCity.getText().toString());
        model.setAddress1(sAdress.getText().toString());
        model.setZipPostalCode(sPostalCode.getText().toString());
        model.setPhoneNumber(sPhoneNo.getText().toString());

        model.setBirthdayDay(sDay.getText().toString());
        model.setBirthdayMonth(sMonth.getText().toString());
        model.setBirthdayYear(sYear.getText().toString());

        String birthday = sYear.getText().toString() + "-" + sMonth.getText().toString() + "-" + sDay.getText().toString();
        model.setDOB(birthday);
        GlobalClass.billingModel = model;
    }


    public void show(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
