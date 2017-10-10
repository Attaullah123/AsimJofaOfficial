package com.cresset.asimjofaofficial.productdetail;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
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
import com.cresset.asimjofaofficial.ContactUs;
import com.cresset.asimjofaofficial.CurrencyChange;
import com.cresset.asimjofaofficial.CurrencySelector;
import com.cresset.asimjofaofficial.GetCart;
import com.cresset.asimjofaofficial.PolicyActivity;
import com.cresset.asimjofaofficial.PrivacyPolicyNew;
import com.cresset.asimjofaofficial.Profile;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.adapter.AddonsAdapter;
import com.cresset.asimjofaofficial.adapter.AddonsAdapterRe;
import com.cresset.asimjofaofficial.adapter.BottomItemAdapter;
import com.cresset.asimjofaofficial.adapter.PolicyExpandAdapter;
import com.cresset.asimjofaofficial.adapter.ProductImagePagerAdapter;
import com.cresset.asimjofaofficial.adapter.QuantitySpinner;
import com.cresset.asimjofaofficial.adapter.SizeSpinnerAdapter;
import com.cresset.asimjofaofficial.models.AddToCartModel;
import com.cresset.asimjofaofficial.models.AppointmentModel;
import com.cresset.asimjofaofficial.models.AttributesItem;
import com.cresset.asimjofaofficial.models.ChnagePasswordModel;
import com.cresset.asimjofaofficial.models.GuestOrLoginResponseModel;
import com.cresset.asimjofaofficial.models.ProductAddons;
import com.cresset.asimjofaofficial.models.ProductDetailList;
import com.cresset.asimjofaofficial.models.ProductDetailModel;
import com.cresset.asimjofaofficial.models.ProductDetailSize;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.QuantityModel;
import com.cresset.asimjofaofficial.models.UserModel;
import com.cresset.asimjofaofficial.recylerview.RecyclerDivider;
import com.cresset.asimjofaofficial.userinfo.activity.ChangePassword;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.volley.AppController;
import com.cresset.asimjofaofficial.volley.SizeDialogFragment;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class ProductDetail extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = ProductDetail.class.getSimpleName();

    private TextView price, name, sku, fullDiscription, proDetailId,sizePro,proName,cartCountView,currencyNmae,bottomCancel,addTocart,
            appointmentClose;
    private Spinner productSize,quantitySpinner;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private CirclePageIndicator indicator;
    private static final Integer[] IMAGES = {};
    private SizeSpinnerAdapter sizeSpinnerAdapter;
    private Button infoButton, btmAppointmentButton;
    private String id,fulldiscrip,btmProNames,btmSku,quantityName;
    private RecyclerView recyclerView;
    private AddonsAdapterRe addonsAdapter;
    private ProductDetailList proDetail;
    private ProductDetailSize productDetailSize;
    JSONArray array = new JSONArray();
    private ProgressDialog progressDialog;
    private View v;
    private Menu menu;
    private String tag_json_obj = "json_obj_req";
    private LinearLayout headerInStore,headerComp;
    private ExpandableRelativeLayout expandInstore,expandComandCare;
    private EditText etProdName,etFullName,etEmail,etTelephone,etAddress,etCity,etBudgetet,etMessage;
    private String etBtmProName;
    ExpandableListAdapter listAdapter;
    //ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    View rootView;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detial);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);
        GlobalClass.selectedProductAddons = new ArrayList<ProductAddons>();

        price = (TextView) findViewById(R.id.product_price1);
        quantitySpinner = (Spinner) findViewById(R.id.product_quantity_spinner);
        SearchView searchView = (SearchView) findViewById(R.id.sv_productList);
        v = (View) findViewById(R.id.main_layout);

        name = (TextView) findViewById(R.id.text_code);
        addTocart = (TextView) findViewById(R.id.addTocart);
        productSize = (Spinner) findViewById(R.id.size_Spinner);
        infoButton = (Button) findViewById(R.id.btn_info);
        ImageView cancelBtn = (ImageView) findViewById(R.id.img_back);
        proDetailId = (TextView) findViewById(R.id.product_detail_id);
        mPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_adons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new RecyclerDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(addonsAdapter);

        currencyNmae = (TextView) findViewById(R.id.product_currency_name);

        currencyNmae.setText("USD");
        //quantity spinnerPro

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
        dataAdapter.setDropDownViewResource(R.layout.spinner_quantity_value);

        // attaching data adapter to spinner
        quantitySpinner.setAdapter(dataAdapter);
        quantitySpinner.setPrompt("Select Quantity");
        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                quantityName = parent.getItemAtPosition(position).toString();

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
        }, 3000, 3000);

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
            public void onClick(final View v) {

                if (!proDetail.isOutOfStock() && !proDetail.isCallForPrice()){
                    {
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
                                        Toast.makeText(getApplicationContext(), "Item Add to cart", Toast.LENGTH_SHORT).show();
                                        Snackbar snackbar = Snackbar.make(v, "Item Add to cart", Snackbar.LENGTH_LONG)
                                                .setAction("VIEW CART", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Intent intent = new Intent(getApplicationContext(), GetCart.class);
                                                        startActivity(intent);
                                                    }
                                                });

                                        snackbar.show();

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
                                Toast.makeText(getApplicationContext(),"Internet connection slow, Please try again", Toast.LENGTH_SHORT).show();
                                Log.d("Error", error.toString());
                                // progressDialog.dismiss();
                            }


                        });

                        AppController.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

                    }
                }
                else{
                    if(proDetail.isCallForPrice()){
                        Toast.makeText(getApplicationContext(),"Fill form for appointment!", Toast.LENGTH_SHORT).show();
                        appointmentBottomSheet();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Out of Stock", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    //fetching data
    private void getProductDetail(){
        //System.out.println(id);
        //progressDialog.show();
        Map<String, String> params = new HashMap<>();

        params.put("ProjectId", Config.PROJECTID);
        params.put("ProductId",id);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_PRODUCT_DETAIL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("ResponseCart", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        ProductDetailModel list = gson.fromJson(response.toString(), new TypeToken<ProductDetailModel>(){}.getType());
                        // Log.d("list", list.getName())
                        List<ProductDetailList>  productList = list.getProductDetail();
                            proDetail = productList.get(0);

                        if(proDetail.isCallForPrice()){
                            addTocart.setText("MAKE AN APPOINTMENT");
                        }
                        else if (proDetail.isOutOfStock()){
                            addTocart.setText("SOLD OUT");
                        }

                        String proName = productList.get(0).getName();
                        //String proPrice = Float.toString(productList.get(0).getPrice());
                        btmSku = productList.get(0).getSku();
                        fulldiscrip  = productList.get(0).getFullDescription();
                        btmProNames = productList.get(0).getName();
                        //btmSku = attributesItem.getSku();
                        name.setText(proName);

                        if(productList.get(0).isCallForPrice()){
                            currencyNmae.setText("");
                            price.setText("Call for Price");
                        }
                        else{
                            float proPrice =productList.get(0).getPrice();
                            if(GlobalClass.currency != null){
                                proPrice = proPrice * GlobalClass.currency.getRate();
                                currencyNmae.setText(GlobalClass.currency.CurrencyCode);
                            }
                            else{
                                currencyNmae.setText("USD");
                            }

                            price.setText(String.format("%.0f",proPrice));
                            //price.setText(NumberFormat.getNumberInstance(Locale.US).format(proPrice));
                        }

                        //price.setText(Float.toString(proPrice));

                        System.out.println(list.getStatus());
                        //set image slider
                        ArrayList<String> imageList = new ArrayList<String>(list.getImagesLink());
                        mPager.setAdapter(new ProductImagePagerAdapter(getApplicationContext(), imageList));
                        indicator.setViewPager(mPager);
                        //set products addons
                        ArrayList<ProductAddons> adonList = new ArrayList<ProductAddons>(list.getAddons());
                        addonsAdapter = new AddonsAdapterRe(getApplicationContext(), adonList,price);
                        recyclerView.setAdapter(addonsAdapter);
                        //set product size
                        ArrayList<ProductDetailSize> sizeList = new ArrayList<ProductDetailSize>(list.getSize());
                        Collections.reverse(sizeList);

                        sizeSpinnerAdapter = new SizeSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, sizeList);
                        productSize.setAdapter(sizeSpinnerAdapter);
                        productSize.setPrompt("Select Size");

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


                    }

                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                // progressDialog.dismiss();
            }


        });

        CustomVolleyRequest.getInstance(getApplicationContext()).getRequestQueue().add(objectRequest);
    }

    //bottom sheet info
    public void openBottomInfo(View v) {
        View view = getLayoutInflater().inflate(R.layout.bottom_info, null);

        headerInStore = (LinearLayout) view.findViewById(R.id.header_in_store);
        headerComp = (LinearLayout) view.findViewById(R.id.header_in_com_care);

        bottomCancel = (TextView) view.findViewById(R.id.close);

        fullDiscription = (TextView) view.findViewById(R.id.btm_prod_detial);
        proName = (TextView) view.findViewById(R.id.btm_prod_code);
        sku = (TextView) view.findViewById(R.id.btm_sku_code);
        //expListView = (ExpandableListView) view.findViewById(R.id.lvExp);


        fullDiscription.setText(Html.fromHtml(fulldiscrip));
        proName.setText(btmProNames);
        sku.setText(btmSku);
        //set adapter
        // get the listview
        //expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // preparing list data
        //prepareListData();

        listAdapter = new BottomItemAdapter(getApplicationContext(), listDataHeader, listDataChild);

        // setting list adapter
        //e/xpListView.setAdapter(listAdapter);

//        expListView.setGroupIndicator(null);
//
//        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
//                    expListView.collapseGroup(lastExpandedPosition);
//                }
//                lastExpandedPosition = groupPosition;
//            }
//        });

        TextView sizeGuide = (TextView)view.findViewById( R.id.btm_size_guide);

        final Dialog dialogBottomInfo = new Dialog(ProductDetail.this, R.style.MaterialDialogSheet);

        dialogBottomInfo.setContentView (view);
        dialogBottomInfo.setCancelable (true);
        dialogBottomInfo.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogBottomInfo.getWindow ().setGravity (Gravity.BOTTOM);
        dialogBottomInfo.show ();

        sizeGuide.setOnClickListener(this);

        bottomCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBottomInfo.dismiss();
            }
        });
        //Add onPreDrawListener
    }

//    private void prepareListData() {
//        listDataHeader = new ArrayList<String>();
//        listDataChild = new HashMap<String, List<String>>();
//        // Adding child data
//        listDataHeader.add("In-Store availability");
//        listDataHeader.add("Composition and care");
//        // Adding child data
//
//        List<String> Privacy = new ArrayList<String>();
//        Privacy.add("Product is available in-store and autorize retailer.");
//
//        List<String> Password = new ArrayList<String>();
//        Password.add("Pure chiffon thread  (depend on parent category)\n" +
//                "     Care:\n" +
//                "     Hand washes recommended. \n" +
//                "     Do not bleach.\n" +
//                "     Iron with care.\n" +
//                "     Do not Tumble dry.\n");
//
//        listDataChild.put(listDataHeader.get(0), Privacy); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), Password);
//    }


        public void comCare(View view){
        expandComandCare = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_com_care);
        expandComandCare.toggle(); // toggle expand and collapse
    }

    public void inStore(View view) {
        expandInstore = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_cart_item);
        expandInstore.toggle(); // toggle expand and collapse
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

                case R.id.btm_size_guide:
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                SizeDialogFragment dialogFragment = new SizeDialogFragment ();
                dialogFragment.show(fm, "Sample Fragment");
                break;
        }
    }
    //appointment form

    public void appointmentBottomSheet(){
        View view = getLayoutInflater().inflate(R.layout.appointment_form, null);

        final Dialog dialogBottomInfo = new Dialog(ProductDetail.this, R.style.MaterialDialogSheet);

        etProdName = (EditText) view.findViewById(R.id.edit_product_name);
        etFullName = (EditText) view.findViewById(R.id.edit_full_name);
        etEmail = (EditText) view.findViewById(R.id.edit_email);
        etTelephone = (EditText) view.findViewById(R.id.edit_telephone);
        etAddress = (EditText) view.findViewById(R.id.edit_address);
        etCity = (EditText) view.findViewById(R.id.edit_city);
        etBudgetet = (EditText) view.findViewById(R.id.edit_budget);
        etMessage = (EditText) view.findViewById(R.id.edit_message);

        btmAppointmentButton = (Button) view.findViewById(R.id.appointment_btn);
        appointmentClose = (TextView) view.findViewById(R.id.close_app);

        //set values
        etProdName.setText(btmProNames);


        btmAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String appProName = etProdName.getText().toString().trim();
                String appFullName = etFullName.getText().toString().trim();
                String appEmail = etEmail.getText().toString().trim();
                String appTelephone = etTelephone.getText().toString().trim();
                String appAddress = etAddress.getText().toString().trim();
                String appCity = etCity.getText().toString().trim();
                String appBudget = etBudgetet.getText().toString().trim();
                String appMessage = etMessage.getText().toString().trim();

                if (!appProName.isEmpty() && !appFullName.isEmpty() && !appEmail.isEmpty() && !appTelephone.isEmpty() && !appAddress.isEmpty()
                        && !appCity.isEmpty() && !appBudget.isEmpty() && !appMessage.isEmpty()){
                    appointmentFormSubmit(appProName, appFullName, appEmail, appTelephone, appAddress ,appCity, appBudget, appMessage);
                    dialogBottomInfo.dismiss();
                    //Toast.makeText(getApplicationContext(), "Api required for submitting the form!", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(), "Please enter the required fields!", Toast.LENGTH_LONG).show();
                }
            }
        });

        appointmentClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBottomInfo.dismiss();
            }
        });

        dialogBottomInfo.setContentView (view);
        dialogBottomInfo.setCancelable (true);
        dialogBottomInfo.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogBottomInfo.getWindow ().setGravity (Gravity.BOTTOM);
        dialogBottomInfo.show ();
    }

    public void appointmentFormSubmit(final String appProName, final String appFullName, final String appEmail, final String appTelephone,final String appAddress,
                             final String appCity, final String appBudget,final String appMessage){
        progressDialog.show();

        HashMap<String,String> params = new HashMap<>();
        params.put("ProjectId",Config.PROJECTID);
        params.put("CollectionName", appProName);
        params.put("FullName", appFullName);
        params.put("Email", appEmail);
        params.put("Phone", appTelephone);
        params.put("Address", appAddress);
        params.put("City", appCity);
        params.put("Budget", appBudget);
        params.put("Message", appMessage);


        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_APPOINTMENT_FORM, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "Form Submit Response: " + response.toString());
                        //Toast.makeText(getApplicationContext(), "your form submit successfully", Toast.LENGTH_LONG);

                        try {

                           progressDialog.dismiss();
                           Toast.makeText(getApplicationContext(), "your form submit successfully", Toast.LENGTH_LONG).show();


                        } catch (Exception e) {
                            // JSON error
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "please check connection", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        // Adding request to request queue
        CustomVolleyRequest.getInstance(getApplicationContext()).getRequestQueue().add(objectRequest);
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

        //Button imageView = (Button) findViewById(R.id.shopping_cart_icon);
        cartCountView.setOnClickListener(new View.OnClickListener() {
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
        getProductDetail();
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
                //Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
            }
        });
        CustomVolleyRequest.getInstance(getApplicationContext()).getRequestQueue().add(objectRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                Intent intent = new Intent(getApplicationContext(), GetCart.class);
                startActivity(intent);
                return true;
            case R.id.currency_change:
                Intent intent1 = new Intent(getApplicationContext(), CurrencySelector.class);
                startActivity(intent1);
                return true;
            case R.id.info:
                Intent intent2 = new Intent(getApplicationContext(), PolicyActivity.class);
                startActivity(intent2);
                return true;
            case R.id.contact_us:
                Intent intent3 = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(intent3);
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

}
