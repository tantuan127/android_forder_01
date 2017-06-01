package com.framgia.forder.screen.ordershop;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.forder.R;
import com.framgia.forder.data.model.ShopManagement;
import com.framgia.forder.databinding.FragmentOrdershopBinding;

/**
 * OrderShop Screen.
 */
public class OrderShopFragment extends Fragment {
    private static final String EXTRA_ORDER_SHOP = "EXTRA_ORDER_SHOP";

    private OrderShopContract.ViewModel mViewModel;

    public static OrderShopFragment newInstance(ShopManagement shopManagement) {
        OrderShopFragment orderShopFragment = new OrderShopFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ORDER_SHOP, shopManagement);
        orderShopFragment.setArguments(bundle);
        return orderShopFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new OrderShopViewModel();

        OrderShopContract.Presenter presenter = new OrderShopPresenter(mViewModel);
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentOrdershopBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_ordershop, container, false);
        binding.setViewModel((OrderShopViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
