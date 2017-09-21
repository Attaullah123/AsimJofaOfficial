package com.cresset.asimjofaofficial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.adapter.AddonsAdapter;
import com.cresset.asimjofaofficial.adapter.CurrencyAdapter;
import com.cresset.asimjofaofficial.adapter.ProductListAdapter;
import com.cresset.asimjofaofficial.models.CurrencyListModel;
import com.cresset.asimjofaofficial.models.CurrencyModel;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.ProductModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CurrencyChange extends AppCompatActivity {

    private ListView listView;
    private CurrencyAdapter currencyAdapter;
    private ProgressDialog progressDialog;
    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_change);

        back = (ImageView) findViewById(R.id.img_back);
        listView = (ListView) findViewById(R.id.currency_listview);

        //listView.setAdapter(currencyAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);

        getCurrency();
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
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

                        ArrayList<CurrencyListModel> detailLists = new ArrayList<CurrencyListModel>(currencyModel.getCurrencyList());

                        currencyAdapter = new CurrencyAdapter(getApplicationContext(), detailLists);
                        listView.setAdapter(currencyAdapter);
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

}
