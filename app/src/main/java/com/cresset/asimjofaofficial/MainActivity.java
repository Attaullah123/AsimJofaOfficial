package com.cresset.asimjofaofficial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
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
import com.cresset.asimjofaofficial.easypasiapayment.EasyPaisaActivity;
import com.cresset.asimjofaofficial.models.GuestOrLoginResponseModel;
import com.cresset.asimjofaofficial.models.UserModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView cartCountView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    CoordinatorLayout coordinatorLayout;
    Gson gson;
    private Menu menu;

    private int cartCountNotificationValue = GlobalClass.DEFAULT_EMPTY_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gson = new Gson();
        sharedPreferencesEditor = getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE).edit();
        sharedPreferences = getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setLogo(R.mipmap.app_logo);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
        getSupportActionBar().setTitle("");
        //toolbar.setBackgroundColor(Color.parseColor("#ffffff"));

        Fragment fragment = new HomeActivity();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_content, fragment).commit();

        // guest user register at application run
        //Toast.makeText(getApplicationContext(),"User Data" + GlobalClass.userData,Toast.LENGTH_LONG).show();
        if(GlobalClass.userData == null){
            String guestUserData = sharedPreferences.getString(Config.GuestPreference,null);
            String registeredUserData = sharedPreferences.getString(Config.RegisteredPreference,null);
            if(registeredUserData != null && registeredUserData != ""){
                //show("Registered: " + registeredUserData);
                UserModel userModel = gson.fromJson(registeredUserData.toString(), new TypeToken<UserModel>(){}.getType());
                GlobalClass.userData = userModel;
                //show("registered user find");
            }
            else if(guestUserData != null && guestUserData != ""){
                //show("Guest: " + guestUserData);
                UserModel userModel = gson.fromJson(guestUserData.toString(), new TypeToken<UserModel>(){}.getType());
                GlobalClass.userData = userModel;
                //show("guest user find");
            }
            else{
                RegisterGuestUser();
            }
        }

        //bottom menu
        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.navigation, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
               /* Fragment detail = null;
                FragmentManager fm = getSupportFragmentManager();*/
                switch (itemId) {
                    case R.id.navigation_home:
                        //detail = new HomeActivity();
                        Fragment homeFragment = new HomeActivity();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_content, homeFragment).commit();
                        break;
                    case R.id.navigation_profile:
                        // detail = new Profile();
                        //toolbar.setVisibility(View.GONE);
                        Intent intent = new Intent(MainActivity.this, MyAccount.class);
                        startActivity(intent);
                       /* Bundle arg = new Bundle();
                        arg.putString("title", "Dialog with Action Bar");
                        detail = new LoginDialogFragment();
                        detail.setArguments(arg);*/

                        break;

                    case R.id.navigation_store:
                        //detail = new Store();
                        Fragment storeFragment = new Store();
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        fragmentManager1.beginTransaction().replace(R.id.frame_content, storeFragment).commit();
                        break;

                }
                //fm.beginTransaction().replace(R.id.frame_content, detail).addToBackStack("tag").commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        this.menu = menu;
        if (GlobalClass.userData != null){
            GetCartItemsCount();
        }
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
                Intent intent = new Intent(MainActivity.this, GetCart.class);
                startActivity(intent);
            }
        });

        Button imageView = (Button) findViewById(R.id.shopping_cart_icon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GetCart.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (GlobalClass.userData != null){
            GetCartItemsCount();
        }
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
                            if (GlobalClass.userData.getUserID()!= null){
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
        switch (item.getItemId()){
            case R.id.cart:
                Intent intent = new Intent(MainActivity.this, GetCart.class);
                startActivity(intent);
                return true;
            case R.id.currency_change:
                Intent intent1 = new Intent(getApplicationContext(), CurrencyChange.class);
                startActivity(intent1);
                return true;
            case R.id.info:
                Intent intent2 = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void RegisterGuestUser(){
        HashMap<String,String> params = new HashMap<>();
        params.put("ProjectId",Config.PROJECTID);

        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        params.put("IpAddress", ip);

        //Toast.makeText(getApplicationContext(), ip.toString(), Toast.LENGTH_LONG).show();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_GUEST_USER_REGISTER, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            Gson gson = new Gson();
                            GuestOrLoginResponseModel model = gson.fromJson(response.toString(), new TypeToken<GuestOrLoginResponseModel>(){}.getType());

                            //show("Customer id:" + model.getCustomerId());

                            if (model.getCustomerId() != null && model.getCustomerId() != "") {

                                UserModel userData = new UserModel();
                                userData.setUserID(model.getCustomerId());
                                userData.setGuest(true);

                                GlobalClass.userData = userData;
                                String json = gson.toJson(userData);
                                sharedPreferencesEditor.putString(Config.GuestPreference,json);
                                //show(Config.GuestPreference + ": " + json);
                                sharedPreferencesEditor.commit();

                               // show("Guest user created!");
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                snackbar.setActionTextColor(Color.RED);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(Color.DKGRAY);
                snackbar.show();
                //Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(objectRequest);


    }

    public void show(String message){

//        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
//        snackbar.show();

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}
