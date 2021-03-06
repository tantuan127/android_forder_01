package com.framgia.forder.utils.navigator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import com.framgia.forder.R;
import com.framgia.forder.data.model.CartItem;
import com.framgia.forder.data.model.DomainManagement;
import com.framgia.forder.data.model.OwnerShop;
import com.framgia.forder.data.model.Product;
import com.framgia.forder.screen.addtocart.AddToCartFragment;
import com.framgia.forder.screen.cart.notecart.NoteCartFragment;
import com.framgia.forder.screen.domainmanagement.adddomain.AddDomainFragment;
import com.framgia.forder.screen.domainmanagement.adddomain.AddDomainListener;
import com.framgia.forder.screen.domainmanagement.editdomain.EditDomainListener;
import com.framgia.forder.screen.domainmanagement.editdomain.EditdomainFragment;
import com.framgia.forder.screen.managerinshop.ManagerInShopFragment;
import com.framgia.forder.screen.quickorder.QuickOrderFragment;
import com.framgia.forder.screen.quickorder.QuickOrderListener;
import java.util.List;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public class Navigator {

    public static final int NONE = 0;
    public static final int RIGHT_LEFT = 1;
    public static final int BOTTOM_UP = 2;
    public static final int FADED = 3;
    public static final int LEFT_RIGHT = 4;

    @NonNull
    private FragmentActivity mActivity;
    private Fragment mFragment;

    public Navigator(@NonNull FragmentActivity activity) {
        mActivity = activity;
    }

    public Navigator(Fragment fragment) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
    }

    public void startActivity(@NonNull Intent intent) {
        mActivity.startActivity(intent);
    }

    public void startActivity(@NonNull Class<? extends Activity> clazz) {
        mActivity.startActivity(new Intent(mActivity, clazz));
    }

    public void finishActivity() {
        if (mActivity != null) {
            mActivity.finish();
        }
        if (mFragment != null) {
            mFragment.getActivity().finish();
        }
    }

    public void startActivity(@NonNull Class<? extends Activity> clazz, Bundle args) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(args);
        startActivity(intent);
    }

    public void startActivityAtRoot(@NonNull Class<? extends Activity> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void startActivityForResult(@NonNull Intent intent, int requestCode) {
        mActivity.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResultFromFragment(@NonNull Intent intent, int requestCode) {
        mFragment.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(@NonNull Class<? extends Activity> clazz, Bundle args,
            int requestCode) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(args);
        startActivityForResult(intent, requestCode);
    }

    public void finishActivityWithResult(Intent intent, int resultCode) {
        mActivity.setResult(resultCode, intent);
        mActivity.finish();
    }

    public void openUrl(String url) {
        if (TextUtils.isEmpty(url) || !Patterns.WEB_URL.matcher(url).matches()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
        mActivity.startActivity(intent);
    }

    /**
     * Go to next fragment which nested inside current fragment
     *
     * @param fragment new child fragment
     */
    public void goNextChildFragment(@IdRes int containerViewId, Fragment fragment,
            boolean addToBackStack, int animation, String tag) {
        if (mFragment == null) {
            return;
        }
        FragmentTransaction transaction = mFragment.getChildFragmentManager().beginTransaction();
        setFragmentTransactionAnimation(transaction, animation);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.replace(containerViewId, fragment, tag);
        transaction.commitAllowingStateLoss();
        mFragment.getChildFragmentManager().executePendingTransactions();
    }

    /**
     * Always keep 1 fragment in container
     *
     * @return true if fragment stack has pop
     */
    public boolean goBackChildFragment() {
        if (mFragment == null) {
            return false;
        }
        boolean isShowPrevious = mFragment.getChildFragmentManager().getBackStackEntryCount() > 1;
        if (isShowPrevious) {
            mFragment.getChildFragmentManager().popBackStackImmediate();
        }
        return isShowPrevious;
    }

    private void setFragmentTransactionAnimation(FragmentTransaction transaction,
            @NavigateAnim int animation) {
        switch (animation) {
            case FADED:
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case RIGHT_LEFT:
                transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                        R.anim.slide_left_in, R.anim.slide_right_out);
                break;
            case BOTTOM_UP:
                transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_top_out,
                        R.anim.slide_top_in, R.anim.slide_bottom_out);
                break;
            case NONE:
                break;
            default:
                break;
        }
    }

    public void showToast(@StringRes int stringId) {
        Toast.makeText(mActivity, mActivity.getString(stringId) + "", Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }

    public void showToastCustom(String message) {
        Toast toast = Toast.makeText(mActivity, message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        toast.setGravity(Gravity.BOTTOM | Gravity.HORIZONTAL_GRAVITY_MASK, 0, 0);
        view.setBackgroundResource(R.drawable.item_custom_toast);
        toast.setView(view);
        toast.show();
    }

    public void showToastCustomActivity(@StringRes int stringId) {
        Toast toast = Toast.makeText(mActivity, stringId, Toast.LENGTH_SHORT);
        View view = toast.getView();
        toast.setGravity(Gravity.BOTTOM | Gravity.HORIZONTAL_GRAVITY_MASK, 0, 0);
        view.setBackgroundResource(R.drawable.item_custom_toast);
        toast.setView(view);
        toast.show();
    }

    public void goBackFragmentByTag(String tag, int flags) {
        mFragment.getChildFragmentManager().popBackStackImmediate(tag, flags);
    }

    public void setFragment(@IdRes int containerViewId, Fragment fragment, boolean addToBackStack,
            int animation, String tag) {
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        setFragmentTransactionAnimation(transaction, animation);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.replace(containerViewId, fragment, tag);
        transaction.commitAllowingStateLoss();
        mActivity.getSupportFragmentManager().executePendingTransactions();
    }

    public void showAddNoteDialog(CartItem cartItem, String tag) {
        FragmentManager fragmentManager = mFragment.getFragmentManager();
        NoteCartFragment.newInstance(cartItem).show(fragmentManager, tag);
    }

    public void showAddDomainDialog(String tag, AddDomainListener listener) {
        FragmentManager fragmentManager = mFragment.getFragmentManager();
        AddDomainFragment fragment = AddDomainFragment.newInstance();
        fragment.setAddDomainListener(listener);
        fragment.show(fragmentManager, tag);
    }

    public void showEditDomainDialog(String tag, DomainManagement domainManagement,
            EditDomainListener listener) {
        FragmentManager fragmentManager = mFragment.getFragmentManager();
        EditdomainFragment fragment = EditdomainFragment.newInstance(domainManagement);
        fragment.setEditDomainListener(listener);
        fragment.show(fragmentManager, tag);
    }

    public void showQuickOrderDialog(String tag, Product product, QuickOrderListener listener) {
        FragmentManager fragmentManager = mFragment.getFragmentManager();
        QuickOrderFragment fragment = QuickOrderFragment.newInstance(product);
        fragment.setQuickOrderListener(listener);
        fragment.show(fragmentManager, tag);
    }

    public void showManagerInShopDialog(String tag, List<OwnerShop> ownerShops) {
        FragmentManager fragmentManager = mFragment.getFragmentManager();
        ManagerInShopFragment fragment = ManagerInShopFragment.newInstance(ownerShops);
        fragment.show(fragmentManager, tag);
    }

    public void showAddToCartDialog(String tag, Product product, int productInCart,
            int quantity) {
        FragmentManager fragmentManager = mFragment.getFragmentManager();
        AddToCartFragment fragment =
                AddToCartFragment.newInstance(product, productInCart, quantity);
        fragment.show(fragmentManager, tag);
    }

    @IntDef({ RIGHT_LEFT, BOTTOM_UP, FADED, NONE, LEFT_RIGHT })
    public @interface NavigateAnim {
    }

    @IntDef({ ActivityTransition.START, ActivityTransition.FINISH })
    @interface ActivityTransition {
        int START = 0x00;
        int FINISH = 0x01;
    }
}
