package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cresset.asimjofaofficial.BillingShippingById;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.MultpleAddressList;
import com.cresset.asimjofaofficial.models.ProductAddons;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.productdetail.ProductDetail;
import com.cresset.asimjofaofficial.utilities.GlobalClass;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;



public class MultipleAdressAdapter  extends RecyclerView.Adapter<MultipleAdressAdapter.MyViewHolder>  {

    private Context mContext;
    private ArrayList<MultpleAddressList> multpleAddressLists;


    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView userId,headingName,userName, userName1, userEmail,userEmail1,userAddress,userAddress1,userPhone,userPhone1,userCountry,
                userCountry1,userState,userState1,edit,delete;
        //public ImageView thumbnail;
        private Context context;

        public MyViewHolder(View itemView) {
            super(itemView);

            userId = (TextView) itemView.findViewById(R.id.address_id);

            headingName = (TextView) itemView.findViewById(R.id.adress_customer_name);
            userName = (TextView) itemView.findViewById(R.id.adress_name);
            userName1 = (TextView) itemView.findViewById(R.id.adress_customer_name_detail);
            userEmail = (TextView) itemView.findViewById(R.id.adress_email_name);
            userEmail1 = (TextView) itemView.findViewById(R.id.adress_email);
            userAddress = (TextView) itemView.findViewById(R.id.adress_address_name);
            userAddress1 = (TextView) itemView.findViewById(R.id.adress_cus_address);
            userPhone = (TextView) itemView.findViewById(R.id.adress_phone_name);
            userPhone1 = (TextView) itemView.findViewById(R.id.adress_phone);
            userCountry = (TextView) itemView.findViewById(R.id.adress_country_name);
            userCountry1 = (TextView) itemView.findViewById(R.id.adress_country);
            userState = (TextView) itemView.findViewById(R.id.adress_state_name);
            userState1 = (TextView) itemView.findViewById(R.id.adress_state);

            edit = (TextView) itemView.findViewById(R.id.adress_edit);
           // delete = (TextView) itemView.findViewById(R.id.address_delete);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    Context context = v.getContext();

                    Intent intent = new Intent(v.getContext(), BillingShippingById.class);

                    intent.putExtra("addressId", multpleAddressLists.get(position).getId());
                    context.startActivity(intent);

                }
            });

//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });


        }

    }
    public MultipleAdressAdapter(Context mContext, ArrayList<MultpleAddressList> multpleAddressLists1) {
        this.mContext = mContext;
        this.multpleAddressLists = multpleAddressLists1;
    }
    @Override
    public MultipleAdressAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.muliple_list_item, parent, false);

        return new MultipleAdressAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MultipleAdressAdapter.MyViewHolder holder, int position) {
         MultpleAddressList  multipleAddress = multpleAddressLists.get(position);

        holder.userId.setText(multipleAddress.getId());

        holder.userName.setText("Full Name:");
        holder.userName1.setText(multipleAddress.getFirstName());

        holder.userEmail.setText("Email:");
        holder.userEmail1.setText(multipleAddress.getEmail());

        holder.userAddress.setText("Address:");
        holder.userAddress1.setText(multipleAddress.getAddress1());

        holder.userPhone.setText("Phone:");
        holder.userPhone1.setText(multipleAddress.getPhoneNumber());

        holder.userCountry.setText("Country:");
        holder.userCountry1.setText(multipleAddress.getCountry());

        holder.userState.setText("State:");
        holder.userState1.setText(multipleAddress.getStateProvince());
    }

    @Override
    public int getItemCount() {
        return multpleAddressLists.size();
    }


}

