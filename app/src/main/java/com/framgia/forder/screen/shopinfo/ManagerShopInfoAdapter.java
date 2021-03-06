package com.framgia.forder.screen.shopinfo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.forder.R;
import com.framgia.forder.data.model.User;
import com.framgia.forder.databinding.ItemManagerBinding;
import com.framgia.forder.screen.BaseRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by levutantuan on 5/18/17.
 */

public class ManagerShopInfoAdapter
        extends BaseRecyclerViewAdapter<ManagerShopInfoAdapter.ItemViewHolder> {

    private final List<User> mUsers;
    private OnRecyclerViewItemClickListener<User> mItemClickListener;

    ManagerShopInfoAdapter(@NonNull Context context) {
        super(context);
        mUsers = new ArrayList<>();
    }

    @Override
    public ManagerShopInfoAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        ItemManagerBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_manager, parent, false);
        return new ManagerShopInfoAdapter.ItemViewHolder(binding, mItemClickListener);
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener<User> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ManagerShopInfoAdapter.ItemViewHolder holder, int position) {
        holder.bind(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void updateData(List<User> users) {
        if (users == null) {
            return;
        }
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemManagerBinding mBinding;
        private final OnRecyclerViewItemClickListener<User> mItemClickListener;

        ItemViewHolder(ItemManagerBinding binding,
                BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<User> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(User users) {
            mBinding.setViewModel(new ItemManagerShopInfoViewModel(users, mItemClickListener));
            mBinding.executePendingBindings();
        }
    }
}

