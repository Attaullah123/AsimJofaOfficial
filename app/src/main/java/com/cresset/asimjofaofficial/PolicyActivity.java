package com.cresset.asimjofaofficial;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.cresset.asimjofaofficial.adapter.ContactAdapter;
import com.cresset.asimjofaofficial.adapter.PolicyAdapter;
import com.cresset.asimjofaofficial.fragments.TermsAndConditions;


public class PolicyActivity extends FragmentActivity {
    private String[] tabs;
    FragmentTabHost tabHost;
    PolicyAdapter pagerAdapter;
    ViewPager viewPager;
    private TabWidget tabWidget;
    private ImageView back;
    private HorizontalScrollView horizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.policy_activity);

        viewPager = (ViewPager) findViewById(R.id.pager);
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabWidget = (TabWidget) findViewById(android.R.id.tabs);
        tabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.realTabContent);
        back = (ImageView) findViewById(R.id.img_cross);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initializeHorizontalTabs();
        initializeTabs();
        setupTabHost();

        pagerAdapter = new PolicyAdapter(getSupportFragmentManager(), tabs);
        viewPager.setAdapter(pagerAdapter);



        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * on swipe select the respective tab
             * */
            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
                tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                invalidateOptionsMenu();
            }
        });

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                viewPager.setCurrentItem(tabHost.getCurrentTab());

                for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
                {
                    tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
                    //tabHost.getTabWidget().getChildAt(i)
                }

                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.tabwigdet_color));

                scrollToCurrentTab();

            }
        });

    }

    private void initializeHorizontalTabs() {
        LinearLayout ll = (LinearLayout) tabWidget.getParent();
        horizontalScrollView = new HorizontalScrollView(this);
        horizontalScrollView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(horizontalScrollView, 0);
        ll.removeView(tabWidget);
        horizontalScrollView.addView(tabWidget);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
    }

    private void scrollToCurrentTab() {
        final int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        final int leftX = tabWidget.getChildAt(tabHost.getCurrentTab()).getLeft();

//        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
//            tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.policy_tab_unselected); // unselected
//        }
//        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.policy_tab_selected); // selected


        int newX = 0;

//        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++){
//            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#7392B5"));
//        }
        //tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#FFFFFF"));

        newX = leftX + (tabWidget.getChildAt(tabHost.getCurrentTab()).getWidth() / 2) - (screenWidth / 2);
        if (newX < 0) {
            newX = 0;
        }
        horizontalScrollView.scrollTo(newX, 0);
    }

    private void initializeTabs() {
        tabs = new String[]{"TERMS & CONDITIONS", "PRIVACY POLICY","SHIPPING POLICY","ORDERING & TRACKING","RETURN & EXCHANGE"};
    }

    private void setupTabHost() {

        for (int i = 0; i < tabs.length; i++) {

            tabHost.addTab(tabHost.newTabSpec(String.format("%sTab", tabs[i].replace(" ", "")).toLowerCase()).setIndicator(tabs[i]), TermsAndConditions.class, null);
        }
    }


}
