package com.framgia.forder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by levutantuan on 6/6/17.
 */

public class OrderManagement {
    @Expose
    @SerializedName("shop_id")
    private int mShopId;
    @Expose
    @SerializedName("order_id")
    private int mOrderId;
    @Expose
    @SerializedName("order_product_id")
    private int mOrderProductId;
    @Expose
    @SerializedName("status")
    private String mStatus;

    public int getShopId() {
        return mShopId;
    }

    public void setShopId(int shopId) {
        mShopId = shopId;
    }

    public int getOrderId() {
        return mOrderId;
    }

    public void setOrderId(int orderId) {
        mOrderId = orderId;
    }

    public int getOrderProductId() {
        return mOrderProductId;
    }

    public void setOrderProductId(int orderProductId) {
        mOrderProductId = orderProductId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}
