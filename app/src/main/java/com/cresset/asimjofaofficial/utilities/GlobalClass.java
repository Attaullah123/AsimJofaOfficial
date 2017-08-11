package com.cresset.asimjofaofficial.utilities;

import com.cresset.asimjofaofficial.PaymentMethod;
import com.cresset.asimjofaofficial.fragments.ShippingAddress;
import com.cresset.asimjofaofficial.models.BillingModel;
import com.cresset.asimjofaofficial.models.CartModelItems;
import com.cresset.asimjofaofficial.models.CurrencyListModel;
import com.cresset.asimjofaofficial.models.IndexImage;
import com.cresset.asimjofaofficial.models.OrderPlaceResponse;
import com.cresset.asimjofaofficial.models.PaymentMethodModel;
import com.cresset.asimjofaofficial.models.PaymentModel;
import com.cresset.asimjofaofficial.models.ProductAddons;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.ShippingModel;
import com.cresset.asimjofaofficial.models.ShippingmethodList;
import com.cresset.asimjofaofficial.models.UpdateProductQuantity;
import com.cresset.asimjofaofficial.models.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahboob khan on 6/17/2017.
 */

public class GlobalClass {
    public static ShippingmethodList shippingMethod = null;
    public static ShippingModel shippingModel = null;
    public static BillingModel billingModel = null;
    public static PaymentModel paymentMethod = null;
    public static PaymentMethodModel paymentModel = null;

    public static ArrayList<ProductAddons> selectedProductAddons = new ArrayList<ProductAddons>();
    public static ArrayList<UpdateProductQuantity> updateProductQuantity = new ArrayList<UpdateProductQuantity>();
    public static List<Integer> deleteSelectedCartItems = new ArrayList<>();

    public static CurrencyListModel currency = null;
    public static UserModel userData = null;

    public static IndexImage indexData = null;
    public static ProductListModel productLisItem = null;
    public static OrderPlaceResponse orderResponse = null;

    public static final int LONG_DELAY = 3500; // 3.5 seconds
    public static final int DEFAULT_EMPTY_ID = -131;
    public static int CartCount = 0;
}

