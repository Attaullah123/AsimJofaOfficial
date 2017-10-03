package com.cresset.asimjofaofficial.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.PaymentMethodModel;
import com.cresset.asimjofaofficial.utilities.GlobalClass;

import java.util.ArrayList;


public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<PaymentMethodModel> paymentLists;
    public ImageView paymentImage;
//    public String productName;
//    private ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView paymentName, paymentSystemName,productId;

        private Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            paymentName = (TextView) itemView.findViewById(R.id.payment_name);
            paymentImage = (ImageView) itemView.findViewById(R.id.payment_image);
            //paymentSystemName = (TextView) itemView.findViewById(R.id.payment_system_name);
            //proId = (TextView) itemView.findViewById(R.id.product_list_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Context context = v.getContext();

                    GlobalClass.paymentModel = paymentLists.get(position);
                    ((Activity)context).finish();
                }
            });
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
        holder.paymentName.setText(list.getName().replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2"));
       // holder.paymentSystemName.setText(list.getSystemName());
        //holder.price.setText(productListModel.getPrice());

//        ImageView i = new ImageView(mContext);
//        if (list.getSystemName() == "Payments.Easypaisa"){
//            i.setImageResource(R.drawable.location_icon);
//        }else {
//
//        }
        //ImageView i = new ImageView(mContext);
        if(list.getSystemName().equals("Payments.Easypaisa")){
            //set image here

            paymentImage.setImageResource(R.drawable.easypaisa_logo_new);
        }

        if(list.getSystemName().equals("Payments.BankTransfer")){
            //set image here
            paymentImage.setImageResource(R.drawable.bank_ic);
        }
    }

    @Override
    public int getItemCount() {
        return paymentLists.size();
    }
}
