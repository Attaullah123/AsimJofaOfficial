package com.cresset.asimjofaofficial.userinfo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.adapter.AddressHistoryAdapter;
import com.cresset.asimjofaofficial.adapter.PagerShippingSelection;


public class AddressBookActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adress_book_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        // selectMethod = (CheckBox) findViewById(R.id.checkbox_check_method);
        back = (ImageView) findViewById(R.id.img_cross);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_address_book);
        tabLayout.addTab(tabLayout.newTab().setText("Shipping"));
        tabLayout.addTab(tabLayout.newTab().setText("Billing"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final AddressHistoryAdapter adapter = new AddressHistoryAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

}
