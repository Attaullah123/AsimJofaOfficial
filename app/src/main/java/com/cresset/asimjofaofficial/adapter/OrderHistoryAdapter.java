package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cresset.asimjofaofficial.GetCart;
import com.cresset.asimjofaofficial.InvoiceActivity;
import com.cresset.asimjofaofficial.MainActivity;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.CurrencyListModel;
import com.cresset.asimjofaofficial.models.OrdersListModel;
import com.cresset.asimjofaofficial.utilities.GlobalClass;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<OrdersListModel> ordersListModel;

//    public String productName;
//    private ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView orderNo,orderStatus,orderDate,orderTotal,orderCurrencyName;
        public Button invoiceDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderNo = (TextView) itemView.findViewById(R.id.order_no);
            orderStatus = (TextView) itemView.findViewById(R.id.order_status);
            orderDate = (TextView) itemView.findViewById(R.id.order_date);
            orderTotal = (TextView) itemView.findViewById(R.id.order_total);
            orderCurrencyName = (TextView) itemView.findViewById(R.id.order_currency);
            invoiceDetail = (Button) itemView.findViewById(R.id.invoice_detail);
            //overflow = (ImageView) view.findViewById(R.id.overflow);

            invoiceDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, InvoiceActivity.class);
                    mContext.startActivity(intent);
                }
            });
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

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd yyyy");

        holder.orderDate.setText(orderList.getOrderDate());

        float productPrice = Float.parseFloat(orderList.getOrderTotal());
        //change currency

        if(GlobalClass.currency != null){
            productPrice = productPrice * GlobalClass.currency.getRate();
            holder.orderCurrencyName.setText(GlobalClass.currency.CurrencyCode);
        }else{
            holder.orderCurrencyName.setText("USD");
        }
        //holder.orderTotal.setText(String.format("%.0f",productPrice));
        holder.orderTotal.setText(NumberFormat.getNumberInstance(Locale.US).format(productPrice));
    }
    public void clearCart() {
        ordersListModel.clear();
        ordersListModel.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return ordersListModel.size();
    }


}

