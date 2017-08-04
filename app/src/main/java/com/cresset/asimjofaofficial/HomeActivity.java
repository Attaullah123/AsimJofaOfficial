package com.cresset.asimjofaofficial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.adapter.IndexAdapter;
import com.cresset.asimjofaofficial.models.CategoryIndexImageModel;
import com.cresset.asimjofaofficial.models.CategoryList;
import com.cresset.asimjofaofficial.models.CategoryModel;
import com.cresset.asimjofaofficial.models.ChildCategoryList;
import com.cresset.asimjofaofficial.models.IndexImage;
import com.cresset.asimjofaofficial.models.ProductModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeActivity extends Fragment {
    //String url = "http://preview.cressettech.net/MobileAPI/Service1.svc/CategoryList";
    ProgressDialog PD;
    View view;
    private IndexAdapter indexAdapter;
    private ExpandableListView expandList;
    private ArrayList<CategoryList> categoryLists;
    private android.support.v7.widget.SearchView searchView;
   //private EditText searchView;
    private TextView searchProduct;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_activity,container,false);
        expandList = (ExpandableListView) view.findViewById(R.id.expandableListView);

        //searchView = (SearchView) view.findViewById(R.id.sv_productList);
        searchView  = (android.support.v7.widget.SearchView) view.findViewById(R.id.sv_productList);
        searchProduct = (TextView) view.findViewById(R.id.search_product);
        expandList.setGroupIndicator(null);
        PD = new ProgressDialog(getContext());
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        getCategoryList();

        searchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchWord = searchView.getQuery().toString().trim();
                if (!searchWord.isEmpty()){
                    Intent intent = new Intent(getContext(), SearchProductActivity.class);
                    intent.putExtra("keyword", searchWord);
                    startActivity(intent);
                }else{

                    Toast.makeText(getContext(), "enter product name", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    private void getCategoryList() {
        PD.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID );

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Config.URL_IMAGE_INDEX,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("Response", response.toString());

                Gson gson = new Gson();

                CategoryIndexImageModel categoryModel = gson.fromJson(response.toString(), new TypeToken<CategoryIndexImageModel>(){}.getType());

                final ArrayList<IndexImage> categoryLists = new ArrayList<IndexImage>(categoryModel.getIndexImagesList());

                indexAdapter = new IndexAdapter(getContext(), categoryLists);
                expandList.setAdapter(indexAdapter);


                expandList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            Intent intent = new Intent(getContext(), ProductListActivity.class);
                            intent.putExtra("categoryId", categoryLists.get(groupPosition).getCategoryId());
                            startActivity(intent);
                            return true;
                        }
                    });
                PD.dismiss();

//                try {
//                    JSONArray categoryJsonList = response.getJSONArray("Parentlist");
//                    categoryLists = new ArrayList<CategoryList>();
//
//                    for (int CategoryList_i = 0; CategoryList_i < categoryJsonList.length(); CategoryList_i++) {
//
//                        JSONObject CategoryList_obj = categoryJsonList.getJSONObject(CategoryList_i);
//                        //categoryList object
//                        CategoryList catListModel = new CategoryList();
//                        catListModel.setId(CategoryList_obj.getString("Id"));
//                        catListModel.setName(CategoryList_obj.getString("Name"));
//
//                        //String str_CategoryList_obj_Name = CategoryList_obj.getString(TAG_CATEGORYLIST_OBJ_NAME);
//                        ArrayList<ChildCategoryList> subscats = new ArrayList<ChildCategoryList>();
//
//                        String subCatStr = CategoryList_obj.getString("Child");
//                        if (!"Empty".equals(subCatStr)){
//                            Log.e("subCatStr", subCatStr);
//
//                            JSONArray Subscategories = CategoryList_obj.getJSONArray("Child");
//
//                            for (int Subscategories_i = 0; Subscategories_i < Subscategories.length(); Subscategories_i++)
//                            {
//                                JSONObject Subscategories_obj = Subscategories.getJSONObject(Subscategories_i);
//
//                                ChildCategoryList subscateModel = new ChildCategoryList();
//                                subscateModel.setId(Integer.parseInt(Subscategories_obj.getString("Id")));
//                                subscateModel.setName(Subscategories_obj.getString("Name"));
//                                subscats.add(subscateModel);
//                            }
//                        }
//                        catListModel.setChild(subscats);
//                        categoryLists.add(catListModel);
//                    }
//
//
//                    indexAdapter = new IndexAdapter(getContext(), categoryLists);
//                    expandList.setAdapter(indexAdapter);
//
//                    expandList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//                        @Override
//                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                            Intent intent = new Intent(getContext(), ProductListActivity.class);
//                            intent.putExtra("categoryId", categoryLists.get(groupPosition).getId());
//                            startActivity(intent);
//                            return true;
//                        }
//                    });
//
//                    PD.dismiss();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error",error.toString());
                PD.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjReq);
    }
}
