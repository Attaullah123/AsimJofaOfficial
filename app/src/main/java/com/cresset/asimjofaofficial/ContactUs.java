package com.cresset.asimjofaofficial;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cresset.asimjofaofficial.adapter.ContactAdapter;
import com.cresset.asimjofaofficial.adapter.PagerShippingSelection;
import com.cresset.asimjofaofficial.fragments.OnlineFragment;
import com.cresset.asimjofaofficial.fragments.StoreFragment;


public class ContactUs extends AppCompatActivity {
    Toolbar toolbar;
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

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
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_shipping);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_online));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_store));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final ContactAdapter adapter = new ContactAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
