package com.cresset.asimjofaofficial.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.PaymentMethodModel;
import com.cresset.asimjofaofficial.utilities.GlobalClass;

import java.util.ArrayList;


public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<PaymentMethodModel> paymentLists;
//    public String productName;
//    private ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView paymentName, paymentSystemName,productId;
        private Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            paymentName = (TextView) itemView.findViewById(R.id.payment_name);
            //paymentSystemName = (TextView) itemView.findViewById(R.id.payment_system_name);
            //proId = (TextView) itemView.findViewById(R.id.product_list_id);
        }


    }
    public PaymentAdapter(Context mContext, ArrayList<PaymentMethodModel> paymentList) {
        this.mContext = mContext;
        this.paymentLists = paymentList;
    }
    @Override
    public PaymentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_list_row, parent, false);

        return new PaymentAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final PaymentAdapter.MyViewHolder holder, int position) {
        PaymentMethodModel list = paymentLists.get(position);

        holder.paymentName.setText(list.getName());
       // holder.paymentSystemName.setText(list.getSystemName());
        //holder.price.setText(productListModel.getPrice());


    }

    @Override
    public int getItemCount() {
        return paymentLists.size();
    }
}
