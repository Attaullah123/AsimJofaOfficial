package com.cresset.asimjofaofficial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.List;

public class PrivacyPolicy extends AppCompatActivity implements View.OnClickListener{

    private ExpandableRelativeLayout expandableAccuracy,expandableOrder,expandableAck,expandableDelay,expandablePlacement;
    private ImageView accuracyMinus,accuracyPlus,orderPlus,orderMinus,ackMinus,ackPlus,delayMinus,delayPlus,placementMinus,placementPlus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);

        expandableAccuracy = (ExpandableRelativeLayout) findViewById(R.id.expand_accuracy);
        expandableOrder = (ExpandableRelativeLayout) findViewById(R.id.expand_order_acceptance);
        expandableAck = (ExpandableRelativeLayout) findViewById(R.id.expand_order_ack);
        expandableDelay = (ExpandableRelativeLayout) findViewById(R.id.expand_order_delay);
        expandablePlacement = (ExpandableRelativeLayout) findViewById(R.id.expand_order_placement);

        accuracyMinus = (ImageView) findViewById(R.id.img_minus_accuracy);
        accuracyPlus = (ImageView) findViewById(R.id.img_plus_accuracy);

        orderMinus = (ImageView) findViewById(R.id.img_minus_order);
        orderPlus = (ImageView) findViewById(R.id.img_plus_order);

        ackMinus = (ImageView) findViewById(R.id.img_minus_ack);
        ackPlus = (ImageView) findViewById(R.id.img_plus_ack);

        delayMinus = (ImageView) findViewById(R.id.img_minus_delay);
        delayPlus = (ImageView) findViewById(R.id.img_plus_delay);

        placementMinus = (ImageView) findViewById(R.id.img_minus_placement);
        placementPlus = (ImageView) findViewById(R.id.img_plus_placement);

        accuracyMinus.setOnClickListener(this);
        accuracyPlus.setOnClickListener(this);
        orderMinus.setOnClickListener(this);
        orderPlus.setOnClickListener(this);
        ackMinus.setOnClickListener(this);
        ackPlus.setOnClickListener(this);
        delayMinus.setOnClickListener(this);
        delayPlus.setOnClickListener(this);
        placementMinus.setOnClickListener(this);
        placementPlus.setOnClickListener(this);

        expandableAccuracy.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                accuracyMinus.setVisibility(View.VISIBLE);
                accuracyPlus.setVisibility(View.GONE);
            }

            @Override
            public void onPreClose() {
                accuracyMinus.setVisibility(View.GONE);
                accuracyPlus.setVisibility(View.VISIBLE);
            }
        });

        expandableOrder.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                orderMinus.setVisibility(View.VISIBLE);
                orderPlus.setVisibility(View.GONE);
            }

            @Override
            public void onPreClose() {
                orderMinus.setVisibility(View.GONE);
                orderPlus.setVisibility(View.VISIBLE);
            }
        });

        expandableAck.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                ackMinus.setVisibility(View.VISIBLE);
                ackPlus.setVisibility(View.GONE);
            }

            @Override
            public void onPreClose() {
                ackMinus.setVisibility(View.GONE);
                ackPlus.setVisibility(View.VISIBLE);
            }
        });

        expandableDelay.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                delayMinus.setVisibility(View.VISIBLE);
                delayPlus.setVisibility(View.GONE);
            }

            @Override
            public void onPreClose() {
                delayMinus.setVisibility(View.GONE);
                delayPlus.setVisibility(View.VISIBLE);
            }
        });

        expandablePlacement.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                placementMinus.setVisibility(View.VISIBLE);
                placementPlus.setVisibility(View.GONE);
            }

            @Override
            public void onPreClose() {
                placementMinus.setVisibility(View.GONE);
                placementPlus.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_minus_accuracy:
                expandableAccuracy.collapse();
                break;
            case R.id.img_plus_accuracy:
                expandableAccuracy.expand();
                break;
            case R.id.img_minus_order:
                expandableOrder.collapse();
                break;
            case R.id.img_plus_order:
                expandableOrder.expand();
                break;
            case R.id.img_minus_ack:
                expandableAck.collapse();
                break;
            case R.id.img_plus_ack:
                expandableAck.expand();
                break;
            case R.id.img_minus_delay:
                expandableDelay.collapse();
                break;
            case R.id.img_plus_delay:
                expandableDelay.expand();
                break;
            case R.id.img_minus_placement:
                expandablePlacement.collapse();
                break;
            case R.id.img_plus_placement:
                expandablePlacement.expand();
                break;
        }
    }
}
