package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.CurrencyListModel;
import com.cresset.asimjofaofficial.models.OrdersListModel;
import com.cresset.asimjofaofficial.utilities.GlobalClass;

import java.util.ArrayList;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<OrdersListModel> ordersListModel;

//    public String productName;
//    private ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView orderNo,orderStatus,orderDate,orderTotal,orderCurrencyName;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderNo = (TextView) itemView.findViewById(R.id.order_no);
            orderStatus = (TextView) itemView.findViewById(R.id.order_status);
            orderDate = (TextView) itemView.findViewById(R.id.order_date);
            orderTotal = (TextView) itemView.findViewById(R.id.order_total);
            orderCurrencyName = (TextView) itemView.findViewById(R.id.order_currency);
            //overflow = (ImageView) view.findViewById(R.id.overflow);

        }

    }
    public OrderHistoryAdapter(Context mContext, ArrayList<OrdersListModel> orderList) {
        this.mContext = mContext;
        this.ordersListModel = orderList;
    }
    @Override
    public OrderHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_design, parent, false);

        return new OrderHistoryAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final OrderHistoryAdapter.MyViewHolder holder, int position) {
        OrdersListModel orderList = ordersListModel.get(position);

        holder.orderNo.setText(String.valueOf(orderList.getId()));
        holder.orderStatus.setText(orderList.getOrderStatus());
        holder.orderDate.setText(orderList.getOrderDate());

        float productPrice = orderList.getOrderTotal();
        //change currency

        if(GlobalClass.currency != null){
            productPrice = productPrice * GlobalClass.currency.getRate();
            holder.orderCurrencyName.setText(GlobalClass.currency.CurrencyCode);
        }else{
            holder.orderCurrencyName.setText("USD");
        }

        holder.orderTotal.setText(Float.toString(productPrice));

    }

    @Override
    public int getItemCount() {
        return ordersListModel.size();
    }


}

