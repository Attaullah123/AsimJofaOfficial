package com.cresset.asimjofaofficial;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.adapter.CurrencyAdapter;
import com.cresset.asimjofaofficial.models.CurrencyListModel;
import com.cresset.asimjofaofficial.models.CurrencyModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencySelector extends ListActivity {

    private ProgressDialog progressDialog;
    private ImageView back;
    private ArrayList<CurrencyListModel> currencyList;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    List<String> currencies = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_selector);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);
        currencyList = new ArrayList<CurrencyListModel>();
        sharedPreferencesEditor =  getApplicationContext().getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE).edit();
        sharedPreferences = getApplicationContext().getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE);

        getCurrency();

        back = (ImageView) findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               // startActivity(getIntent());
            }
        });

        /** Defining click event listener for the listitem checkbox */
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int checkedItemCount = getCheckedItemCount();

                CurrencyListModel currentClick = currencyList.get(arg2);
                String clickCode = currencies.get(arg2);

                if(GlobalClass.currency != null){
                    if(GlobalClass.currency.getCurrencyCode().equals(clickCode)){
                        getListView().setItemChecked(arg2, true);
                        show("Please choose another currency");
                    }
                    else{
                        setCurrency(currentClick);
                        int itemCount = getListView().getCount();
                        for(int i=0 ; i < itemCount ; i++){
                            if(i != arg2)
                                getListView().setItemChecked(i, false);
                        }
                    }

                }
                else{
                    setCurrency(currentClick);
                    int itemCount = getListView().getCount();
                    for(int i=0 ; i < itemCount ; i++){
                        if(i != arg2)
                            getListView().setItemChecked(i, false);
                    }
                }

                /*String value = currencies.get(arg2).toString();
                CurrencyListModel currency =  currencyList.get(arg2);
                Toast.makeText(getApplicationContext(), "Clicked value is id:" + currency.getId() + " Name:" + currency.getName() , Toast.LENGTH_SHORT).show();
                */
            }
        };


        /** Setting a click listener for the listitem checkbox **/
        getListView().setOnItemClickListener(itemClickListener);
    }

    /**
     *
     * Returns the number of checked items
     */
    private int getCheckedItemCount(){
        int cnt = 0;
        SparseBooleanArray positions = getListView().getCheckedItemPositions();
        int itemCount = getListView().getCount();

        for(int i=0;i<itemCount;i++){
            if(positions.get(i))
                cnt++;
        }
        return cnt;
    }

    public void getCurrency(){
        progressDialog.show();

        Map<String,String> params = new HashMap<>();
        params.put("ProjectId", Config.PROJECTID);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_CURRENCY, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        Gson gson = new Gson();
                        CurrencyModel currencyModel = gson.fromJson(response.toString(), new TypeToken<CurrencyModel>(){}.getType());

                        currencyList = new ArrayList<CurrencyListModel>(currencyModel.getCurrencyList());

                        for (CurrencyListModel model:currencyList
                                ) {
                            currencies.add(model.getCurrencyCode());
                        }
                        setAdapter(currencies);
                        progressDialog.dismiss();
                        //currencyAdapter.notifyDataSetChanged();

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(objectRequest);
    }

    public void show(String message){
        Toast.makeText(getApplicationContext(), message ,Toast.LENGTH_LONG ).show();
    }

    public void setCurrency(CurrencyListModel listModel){
        gson = new Gson();
        String json = gson.toJson(listModel);
        sharedPreferencesEditor.putString(Config.CurrencyPreference,json);
        sharedPreferencesEditor.commit();
        GlobalClass.currency = listModel;
        show("Slected currency is " + listModel.getCurrencyCode());
    }

    public void setAdapter(List<String> currencies){
        /** Defining array adapter to store items for the listview **/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_multiple_choicee, currencies);

        /** Setting the arrayadapter for this listview  **/
        getListView().setAdapter(adapter);

        if(!GlobalClass.currency.equals(null)){
            int itemCount = getListView().getCount();
            for(int i=0 ; i < itemCount ; i++){
                if(currencies.get(i).equals(GlobalClass.currency.getCurrencyCode()))
                    getListView().setItemChecked(i, true);
            }
        }
    }
}

