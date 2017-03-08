package com.example.duong.android_forder_01.data.model;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.example.duong.android_forder_01.data.source.local.ShoppingCardContract.ShoppingCardEntry.COLUMN_ID_DOMAIN;

public class Domain implements Serializable {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("status")
    private int mStatus;
    @SerializedName("user")
    private User mUser;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("slug")
    private String mSlug;
    @SerializedName("number_of_member")
    private String mNumberMember;
    @SerializedName("number_of_shop")
    private String mNumberShop;
    @SerializedName("number_of_product")
    private String mNumberProduct;
    @SerializedName("is_user_joined")
    private int mIsJoined;
    @SerializedName("is_private")
    private int mIsPrivate;
    @SerializedName("is_owner")
    private int mIsOwner;
    @SerializedName("root_domain")
    private int mRootDomain;

    public Domain(int id, String name, int status,
                  User user, String createdAt, String updatedAt, String slug) {
        mId = id;
        mName = name;
        mStatus = status;
        mUser = user;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
        mSlug = slug;
    }

    public Domain() {
    }

    public Domain(Cursor cursor) {
        mId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_DOMAIN));
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.mCreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.mUpdatedAt = updatedAt;
    }

    public String getSlug() {
        return mSlug;
    }

    public void setSlug(String slug) {
        this.mSlug = slug;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getNumberMember() {
        return mNumberMember;
    }

    public void setNumberMember(String numberMember) {
        mNumberMember = numberMember;
    }

    public String getNumberShop() {
        return mNumberShop;
    }

    public void setNumberShop(String numberShop) {
        mNumberShop = numberShop;
    }

    public String getNumberProduct() {
        return mNumberProduct;
    }

    public void setNumberProduct(String numberProduct) {
        mNumberProduct = numberProduct;
    }

    public int getIsJoined() {
        return mIsJoined;
    }

    public void setIsJoined(int isJoined) {
        mIsJoined = isJoined;
    }

    public int getIsPrivate() {
        return mIsPrivate;
    }

    public void setIsPrivate(int isPrivate) {
        mIsPrivate = isPrivate;
    }

    public int getIsOwner() {
        return mIsOwner;
    }

    public void setIsOwner(int isOwner) {
        mIsOwner = isOwner;
    }

    public int getRootDomain() {
        return mRootDomain;
    }

    public void setRootDomain(int rootDomain) {
        mRootDomain = rootDomain;
    }

    @Override
    public String toString() {
        return mName;
    }
}

