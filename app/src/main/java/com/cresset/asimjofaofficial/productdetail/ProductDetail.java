package com.cresset.asimjofaofficial.productdetail;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.GetCart;
import com.cresset.asimjofaofficial.HomeActivity;
import com.cresset.asimjofaofficial.MainActivity;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.adapter.AddonsAdapter;
import com.cresset.asimjofaofficial.adapter.ProductImagePagerAdapter;
import com.cresset.asimjofaofficial.adapter.QuantitySpinner;
import com.cresset.asimjofaofficial.adapter.SizeSpinnerAdapter;
import com.cresset.asimjofaofficial.models.AddToCartModel;
import com.cresset.asimjofaofficial.models.AttributesItem;
import com.cresset.asimjofaofficial.models.ProductAddons;
import com.cresset.asimjofaofficial.models.ProductDetailList;
import com.cresset.asimjofaofficial.models.ProductDetailModel;
import com.cresset.asimjofaofficial.models.ProductDetailSize;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.QuantityModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class ProductDetail extends AppCompatActivity implements View.OnClickListener{
    private TextView price, name, sku, fullDiscription, proDetailId,sizePro,proName,cartCountView,currencyNmae;
    private Spinner productSize,quantitySpinner;
    private ProductImagePagerAdapter pagerAdapter;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private CirclePageIndicator indicator;
    private static final Integer[] IMAGES = {};
    private ArrayList<ProductListModel> listModelArrayList;
    private SizeSpinnerAdapter sizeSpinnerAdapter;
    private Context mContext;
    private Button infoButton;
    private Button sizeCheck;
    private String id;
    private String fulldiscrip;
    private String btmProNames;
    private String btmSku;
    private ListView listviewAddon;
    private ArrayList<ProductAddons> productAddonsArrayList;
    private AddonsAdapter addonsAdapter;
    private TextView addTocart;
    private ProductDetailList proDetail;
    private String atrribute;
    private ProductDetailSize productDetailSize;
    private List<QuantityModel> quantityModels;
    private String quantityName;
    JSONArray array = new JSONArray();
    String mUrl;  //initialized somewhere else
    ArrayList<String> attribute;  //initialized somewhere else
    private ProgressDialog progressDialog;
    private Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detial);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_1);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);
        GlobalClass.selectedProductAddons = new ArrayList<ProductAddons>();

        price = (TextView) findViewById(R.id.product_price1);
        quantitySpinner = (Spinner) findViewById(R.id.product_quantity_spinner);
        SearchView searchView = (SearchView) findViewById(R.id.sv_productList);

        name = (TextView) findViewById(R.id.text_code);
        addTocart = (TextView) findViewById(R.id.addTocart);
        productSize = (Spinner) findViewById(R.id.size_Spinner);
        infoButton = (Button) findViewById(R.id.btn_info);
        ImageView cancelBtn = (ImageView) findViewById(R.id.img_back);
        proDetailId = (TextView) findViewById(R.id.product_detail_id);
        mPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        listviewAddon = (ListView) findViewById(R.id.checkbox_listview);
        currencyNmae = (TextView) findViewById(R.id.product_currency_name);
        setListViewHeightBasedOnChildren(listviewAddon);

        currencyNmae.setText("USD");
        //quantity spinner

        infoButton.setBackgroundColor(Color.WHITE);
        final List<String> quantityList = new ArrayList<String>();
        quantityList.add("1");
        quantityList.add("2");
        quantityList.add("3");
        quantityList.add("4");
        quantityList.add("5");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, quantityList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        quantitySpinner.setAdapter(dataAdapter);
        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                quantityName = parent.getItemAtPosition(position).toString();

                //Toast.makeText(parent.getContext(), "quantity selected" + quantityName, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        id = getIntent().getStringExtra("ProductId");
        //indicator adjustment
        final float density = getResources().getDisplayMetrics().density;
        //Set circle indicator radius
        indicator.setRadius(5 * density);
        NUM_PAGES =IMAGES.length;
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });





        getProductDetail();
//AddTo cart post parameters

        addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!proDetail.isOutOfStock()){
                    AddToCartModel addToCartModel = new AddToCartModel();
                    addToCartModel.setProjectId(Config.PROJECTID);
                    addToCartModel.setProductId(String.valueOf(proDetail.getId()));
                    addToCartModel.setCustomerId(GlobalClass.userData.getUserID());
                    addToCartModel.setQuantity(quantityName);

                    List<AttributesItem> items = new ArrayList<AttributesItem>();

                    AttributesItem at = new AttributesItem();
                    if(productDetailSize != null){
                        at.setProductMappingAttributeId(productDetailSize.getProductMappingAttributeId());
                        at.setProductAttributeValueId(productDetailSize.getProductAttributeValueId());
                        items.add(at);
                    }

                    if(!GlobalClass.selectedProductAddons.isEmpty()){
                        for (int index = 0; index<GlobalClass.selectedProductAddons.size(); index++){
                            at = new AttributesItem();
                            ProductAddons prodAddons = GlobalClass.selectedProductAddons.get(index);
                            at.setProductMappingAttributeId(prodAddons.getProductMappingAttributeId());
                            at.setProductAttributeValueId(prodAddons.getProductAttributeValueId());
                            items.add(at);
                        }
                    }

                    addToCartModel.setAttribute(items);
                    Gson gson = new Gson();
                    String json = gson.toJson(addToCartModel);

                    // JSONArray jonArr =new JSONArray(json);
                    Log.d("output", json.toString());


                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_ADD_TO_CART, json,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //CategoryModel categoryModel = new CategoryModel();

                                    Log.d("Response", response.toString());
                                    Toast.makeText(getApplicationContext(), "value Add to cart", Toast.LENGTH_SHORT).show();

                                    GetCartItemsCount();

                                    try {
                                        String status = response.getString("Status");

                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }

                                }

                            },  new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                            Log.d("Error", error.toString());
                            // progressDialog.dismiss();
                        }


                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(objectRequest);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Out of Stock", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

//fetching data
    private void getProductDetail(){
        //System.out.println(id);
        progressDialog.show();
        Map<String, String> params = new HashMap<>();

        params.put("ProjectId", Config.PROJECTID);
        params.put("ProductId",id);

        //params.put("application/json","charset=utf-8");

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_PRODUCT_DETAIL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        ProductDetailModel list = gson.fromJson(response.toString(), new TypeToken<ProductDetailModel>(){}.getType());
                       // Log.d("list", list.getName())
                       List<ProductDetailList>  productList = list.getProductDetail();
                        proDetail = productList.get(0);

                        if (proDetail.isOutOfStock()){
                            addTocart.setText("SOLD OUT");
                        }

                        String proName = productList.get(0).getName();
                        String proPrice = Float.toString(productList.get(0).getPrice());
                        fulldiscrip  = productList.get(0).getFullDescription();
                        btmProNames = productList.get(0).getName();
                        //btmSku = attributesItem.getSku();
                        name.setText(proName);
                        price.setText(proPrice);
                        System.out.println(list.getStatus());

                        ArrayList<String> imageList = new ArrayList<String>(list.getImagesLink());
                        mPager.setAdapter(new ProductImagePagerAdapter(getApplicationContext(), imageList));
                        indicator.setViewPager(mPager);

                        ArrayList<ProductAddons> adonList = new ArrayList<ProductAddons>(list.getAddons());
                        addonsAdapter = new AddonsAdapter(getApplicationContext(), adonList,price);
                        listviewAddon.setAdapter(addonsAdapter);
                        productSize.setPrompt("Select Size");

                        ArrayList<ProductDetailSize> sizeList = new ArrayList<ProductDetailSize>(list.getSize());

                        sizeSpinnerAdapter = new SizeSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, sizeList);
                        productSize.setAdapter(sizeSpinnerAdapter);

                        productSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                                productDetailSize = (ProductDetailSize)productSize.getSelectedItem();

                                Log.i("Selected item : ", productDetailSize.getSizeName().toString());
                                //Toast.makeText(getApplicationContext(),productDetailSize.getSizeName().toString(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {

                            }

                        });
                        progressDialog.dismiss();

                    }

                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
               progressDialog.dismiss();
            }


        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(objectRequest);
    }




    //bottom sheet info
    public void openBottomInfo(View v) {
        View view = getLayoutInflater().inflate(R.layout.bottom_info, null);
        fullDiscription = (TextView) view.findViewById(R.id.btm_prod_detial);
        proName = (TextView) view.findViewById(R.id.btm_prod_code);
        sku = (TextView) view.findViewById(R.id.btm_sku_code);
        fullDiscription.setText(Html.fromHtml(fulldiscrip));
        proName.setText(btmProNames);
        sku.setText(btmSku);

        TextView share = (TextView)view.findViewById( R.id.btm_share);
        TextView infoStore = (TextView)view.findViewById( R.id.btm_store_availability);
        TextView compCare = (TextView)view.findViewById( R.id.btm_co_care);
        TextView sizeGuide = (TextView)view.findViewById( R.id.btm_size_guide);
        TextView shipping = (TextView)view.findViewById( R.id.btm_shipping);
        TextView returns = (TextView)view.findViewById( R.id.btm_returns);

        final Dialog dialogBottomInfo = new Dialog(ProductDetail.this, R.style.MaterialDialogSheet);

        dialogBottomInfo.setContentView (view);
        dialogBottomInfo.setCancelable (true);
        dialogBottomInfo.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogBottomInfo.getWindow ().setGravity (Gravity.BOTTOM);
        dialogBottomInfo.show ();
        share.setOnClickListener(this);
        infoStore.setOnClickListener(this);
        compCare.setOnClickListener(this);
        sizeGuide.setOnClickListener(this);
        shipping.setOnClickListener(this);
        returns.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btm_share:
                Toast.makeText(getApplicationContext(),"Share click", Toast.LENGTH_LONG).show();
                break;
            case  R.id.btm_store_availability:
                Toast.makeText(getApplicationContext(),"store click", Toast.LENGTH_LONG).show();
                break;
            case R.id.btm_co_care:
                Toast.makeText(getApplicationContext(),"composition and care click", Toast.LENGTH_LONG).show();
                break;
            case  R.id.btm_size_guide:
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetail.this);
                //Yes Button
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Yes button Clicked", Toast.LENGTH_LONG).show();
                        //Log.i("Code2care ", "Yes button Clicked!");
                        dialog.dismiss();
                    }
                });
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.size_alert,null);
                builder.setView(dialogLayout);
                builder.show();
                break;

            case R.id.btm_shipping:
                Toast.makeText(getApplicationContext(),"shipping click", Toast.LENGTH_LONG).show();
                break;
            case  R.id.btm_returns:
                Toast.makeText(getApplicationContext(),"returns click", Toast.LENGTH_LONG).show();
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        this.menu = menu;
        GetCartItemsCount();
        return super.onCreateOptionsMenu(menu);
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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        GetCartItemsCount();
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
                            if (GlobalClass.userData.getUserID()!= null ){
                                jsonObject = new JSONObject(response.toString());
                                GlobalClass.CartCount = jsonObject.getInt("CartCount");
                                UpdateCartCount();
                            }

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
        switch (item.getItemId()) {
            case R.id.cart:
                Intent intent = new Intent(getApplicationContext(), GetCart.class);
                startActivity(intent);
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            //pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
