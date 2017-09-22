package com.cresset.asimjofaofficial;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;

import com.cresset.asimjofaofficial.adapter.PagerAdapterAccount;
import com.cresset.asimjofaofficial.adapter.PagerShippingSelection;



public class ShippingBillingAddress extends AppCompatActivity{
    //private CheckBox selectMethod;
    Toolbar toolbar;
    private ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_biling_activity);
        // getWindow().requestFeature(Window.FEATURE_ACTION_BAR);//getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

       // selectMethod = (CheckBox) findViewById(R.id.checkbox_check_method);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_shipping);
        tabLayout.addTab(tabLayout.newTab().setText("Shipping"));
        tabLayout.addTab(tabLayout.newTab().setText("Billing"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

         viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerShippingSelection adapter = new PagerShippingSelection
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                setTabColor();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    public void  setTabColor(){

    }

    public void navigateFragment(int position){
        viewPager.setCurrentItem(position, true);

    }

}
