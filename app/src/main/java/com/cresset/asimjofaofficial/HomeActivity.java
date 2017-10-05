package com.cresset.asimjofaofficial;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.cresset.asimjofaofficial.utilities.AnimatedExpandableListView;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.cresset.asimjofaofficial.volley.AppController;
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
    //ProgressDialog PD;
    private String tag_json_obj = "json_obj_req";
    private ProgressBar progressBar;
    View view;
    private IndexAdapter indexAdapter;
    private ExpandableListView expandList;
    private ArrayList<CategoryList> categoryLists;
    private android.support.v7.widget.SearchView searchView;
    //private EditText searchView;
    ArrayList<ChildCategoryList> parentList;
    private TextView searchProduct;
    private Context mContext;
    private int lastExpandedPosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_activity,container,false);
        expandList = (ExpandableListView) view.findViewById(R.id.expandableListView);

        //searchView = (SearchView) view.findViewById(R.id.sv_productList);
        searchView  = (android.support.v7.widget.SearchView) view.findViewById(R.id.sv_productList);
        searchProduct = (TextView) view.findViewById(R.id.search_product);
        expandList.setGroupIndicator(null);

        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
//        PD = new ProgressDialog(getContext());
//        PD.setMessage("Loading.....");
//        PD.setCancelable(false);
        //progressBar.setVisibility();

        getCategoryList();
        runTask ();

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
        progressBar.setVisibility(View.VISIBLE);
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID );

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Config.URL_CATEGORY_LIST,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("Response", response.toString());

                Gson gson = new Gson();

                final CategoryModel categoryModel = gson.fromJson(response.toString(), new TypeToken<CategoryModel>(){}.getType());

                final ArrayList<CategoryList> categoryLists = new ArrayList<CategoryList>(categoryModel.getParentlist());

                indexAdapter = new IndexAdapter(getContext(), categoryLists);
                expandList.setAdapter(indexAdapter);
                //adapter.setChoiceMode(AnswersAdabter.CHOICE_MODE_SINGLE_PER_GROUP);

                // Handle the click when the user clicks an any child
                expandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        //parentList = new ArrayList<ChildCategoryList>();
                        indexAdapter.notifyDataSetChanged();

                        //show(Integer.toString(childPosition));
                        //show(Integer.toString(categoryLists.get(groupPosition).getChild().get(childPosition).getId()));


                        Intent intent = new Intent(getContext(), ProductListActivity.class);
                        intent.putExtra("categoryId", Integer.toString(categoryLists.get(groupPosition).getChild().get(childPosition).getId()));
                        intent.putExtra("categoryName",categoryLists.get(groupPosition).getChild().get(childPosition).getName());
                        //intent.putExtra("categoryName",categoryLists.get(groupPosition).getName());
                        startActivity(intent);
                        return false;
                    }
                });

                expandList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {
                        //if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                        if (!categoryLists.get(groupPosition).getChild().isEmpty()) {
                            expandList.collapseGroup(0);

                        }
                        else{
                            Intent intent = new Intent(getContext(), ProductListActivity.class);
                            intent.putExtra("categoryId", categoryLists.get(groupPosition).getId());
                            intent.putExtra("categoryName",categoryLists.get(groupPosition).getName());
                            startActivity(intent);
                        }
                        lastExpandedPosition = groupPosition;
                    }
                });
                progressBar.setVisibility(View.GONE);
                //PD.dismiss();

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

                //Toast.makeText(mContext, "Couldn't get countries, check connection", Toast.LENGTH_SHORT).show();
                //show("Couldn't feed refresh, check connection");
                Log.d("Error",error.toString());
                progressBar.setVisibility(View.GONE);

            }
        });
        //call volley
        CustomVolleyRequest.getInstance(getContext()).getRequestQueue().add(jsonObjReq);
    }

//    public void show(String message){
////        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
////        snackbar.show();
//
//        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
//    }
    @Override
    public void onResume() {
        super.onResume();
        getCategoryList();
    }


    public void runTask () {
        if(isNetworkAvailable()) {

    /* DO WHATEVER YOU WANT IF INTERNET IS AVAILABLE */

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(false);
            builder.setTitle("No Internet");
            builder.setMessage("Internet is required. Please connect and Retry.");

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getActivity().finish();
                }
            });

            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    getCategoryList();
                }
            });
            AlertDialog dialog = builder.create(); // calling builder.create after adding buttons
            dialog.show();
            Toast.makeText(getContext(), "Network Unavailable!", Toast.LENGTH_LONG).show();
        }
    }

    // Private class isNetworkAvailable
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


}
