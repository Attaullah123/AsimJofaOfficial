package com.cresset.asimjofaofficial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.adapter.ProductListAdapter;
import com.cresset.asimjofaofficial.models.IndexImage;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.ProductModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    Toolbar toolbar;
    private ProductListAdapter adapter;
    private List<ProductModel> productModels;
    private List<ProductListModel> productList;
    //private SearchView searchView;
    private ImageView back;
    private ProgressDialog progressDialog;
    private String searchPro;
    private String prodId;
    private View emptyCart;
    private TextView cartCountView;
    private Menu menu;

    //    public static final String TAG_FIRST_APPEARANCE = "firstAppearance";
    //    public static final String TAG_POWERS = "powers";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_search);
        back = (ImageView) findViewById(R.id.img_back);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        emptyCart = findViewById(R.id.cart_empty);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        //initCollapsingToolbar();

        searchPro = getIntent().getStringExtra("keyword");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);

        //searchView = (SearchView) findViewById(R.id.sv_productList);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SearchProductActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

        productList = new ArrayList<>();

        getSearchProductData();
        //prepareProduct();



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void getSearchProductData() {
        progressDialog.show();

        IndexImage indexImage = new IndexImage();
        //creating json array list
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CategoryId", indexImage.getCategoryId());
        params.put("keyword", searchPro);


        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_SEARCH_PRODUCT, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        Gson gson = new Gson();
                        ProductModel productModel = gson.fromJson(response.toString(), new TypeToken<ProductModel>(){}.getType());

                        ArrayList<ProductListModel> detailLists = new ArrayList<ProductListModel>(productModel.getProductList());


                        if (productModel.getProductList() == null || productModel.getProductList().size() == 0) {
                            setCartVisibility(false);
                        } else {
                            setCartVisibility(true);
                            //cartAdapter.refreshItems(cartDetailModel);
                        }
                        adapter = new ProductListAdapter(getApplicationContext(), detailLists);
                        recyclerView.setAdapter(adapter);
                        progressDialog.dismiss();


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

    private void setCartVisibility(boolean visible) {
        if (visible) {
            if (emptyCart != null) emptyCart.setVisibility(View.GONE);
            if (recyclerView != null) recyclerView.setVisibility(View.VISIBLE);
            //if (cartFooter != null) cartFooter.setVisibility(View.VISIBLE);
        } else {
            if (adapter != null) adapter.clearCart();
            if (emptyCart != null) emptyCart.setVisibility(View.VISIBLE);
            if (recyclerView != null) recyclerView.setVisibility(View.GONE);
            if (recyclerView != null) recyclerView.setVisibility(View.GONE);
        }
    }

//    public void searchKeyword(){
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return true;
//            }
//        });
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        this.menu = menu;
        GetCartItemsCount();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        GetCartItemsCount();
    }

    public void UpdateCartCount(){
        MenuItem cartItem = menu.findItem(R.id.cart);
        MenuItemCompat.setActionView(cartItem, R.layout.count_badge);
        View view = MenuItemCompat.getActionView(cartItem);
        cartCountView = (TextView) view.findViewById(R.id.shopping_cart_notify);
        cartCountView.setText(Integer.toString(GlobalClass.CartCount));
        cartCountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GetCart.class);
                startActivity(intent);
            }
        });

        Button imageView = (Button) findViewById(R.id.shopping_cart_icon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GetCart.class);
                startActivity(intent);
            }
        });
    }

    public void GetCartItemsCount() {
        HashMap<String, String> params = new HashMap<>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_Cart_Count, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.toString());
                            GlobalClass.CartCount = jsonObject.getInt("CartCount");
                            UpdateCartCount();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(objectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart:
                Intent intent = new Intent(getApplicationContext(), GetCart.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
