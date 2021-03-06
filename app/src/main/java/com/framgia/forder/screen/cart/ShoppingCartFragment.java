package com.framgia.forder.screen.cart;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.forder.R;
import com.framgia.forder.data.model.Cart;
import com.framgia.forder.data.source.DomainRepository;
import com.framgia.forder.data.source.ProductRepository;
import com.framgia.forder.data.source.UserRepository;
import com.framgia.forder.data.source.local.DomainLocalDataSource;
import com.framgia.forder.data.source.local.ProductLocalDataSource;
import com.framgia.forder.data.source.local.UserLocalDataSource;
import com.framgia.forder.data.source.local.realm.RealmApi;
import com.framgia.forder.data.source.local.sharedprf.SharedPrefsApi;
import com.framgia.forder.data.source.local.sharedprf.SharedPrefsImpl;
import com.framgia.forder.data.source.remote.DomainRemoteDataSource;
import com.framgia.forder.data.source.remote.ProductRemoteDataSource;
import com.framgia.forder.data.source.remote.UserRemoteDataSource;
import com.framgia.forder.data.source.remote.api.service.FOrderServiceClient;
import com.framgia.forder.databinding.FragmentShoppingcartBinding;
import com.framgia.forder.screen.main.LoadCartListener;
import com.framgia.forder.utils.navigator.Navigator;
import com.framgia.forder.widgets.dialog.DialogManager;
import java.util.ArrayList;
import java.util.List;

/**
 * ShoppingCart Screen.
 */
public class ShoppingCartFragment extends Fragment {
    private ShoppingCartContract.ViewModel mViewModel;
    private LoadCartListener mLoadCartListener;

    public static ShoppingCartFragment newInstance() {
        return new ShoppingCartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Cart> cartList = new ArrayList<>();
        ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity(), cartList);
        DialogManager dialogManager = new DialogManager(getActivity());
        Navigator navigator = new Navigator(getParentFragment());

        mViewModel =
                new ShoppingCartViewModel(adapter, dialogManager, navigator, mLoadCartListener);

        RealmApi realmApi = new RealmApi();
        SharedPrefsApi prefsApi = new SharedPrefsImpl(getActivity());

        DomainRepository domainRepository =
                new DomainRepository(new DomainRemoteDataSource(FOrderServiceClient.getInstance()),
                        new DomainLocalDataSource(prefsApi, new UserLocalDataSource(prefsApi)));
        int currentDomainId = domainRepository.getCurrentDomain().getId();
        ProductRepository productRepository = new ProductRepository(
                new ProductRemoteDataSource(FOrderServiceClient.getInstance()),
                new ProductLocalDataSource(realmApi), currentDomainId);
        UserRepository userRepository =
                new UserRepository(new UserRemoteDataSource(FOrderServiceClient.getInstance()),
                        new UserLocalDataSource(prefsApi));
        ShoppingCartContract.Presenter presenter =
                new ShoppingCartPresenter(mViewModel, productRepository, userRepository);
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        FragmentShoppingcartBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_shoppingcart, container, false);
        binding.setViewModel((ShoppingCartViewModel) mViewModel);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            return;
        }
        mViewModel.reloadData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mLoadCartListener = (LoadCartListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement OnHeadlineSelectedListener");
        }
    }
}
