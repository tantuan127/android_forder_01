package com.framgia.forder.data.source;

import com.framgia.forder.data.model.Cart;
import com.framgia.forder.data.model.Comment;
import com.framgia.forder.data.model.Product;
import com.framgia.forder.data.source.local.ProductLocalDataSource;
import com.framgia.forder.data.source.remote.ProductRemoteDataSource;
import com.framgia.forder.data.source.remote.api.request.CommentRequest;
import com.framgia.forder.data.source.remote.api.request.OrderRequest;
import com.framgia.forder.data.source.remote.api.request.RegisterProductRequest;
import com.framgia.forder.data.source.remote.api.request.UpdateProductRequest;
import com.framgia.forder.data.source.remote.api.response.BaseResponse;
import com.framgia.forder.data.source.remote.api.response.OrderCartResponse;
import com.framgia.forder.data.source.remote.api.response.RegisterProductResponse;
import com.framgia.forder.data.source.remote.api.response.UpdateProductResponse;
import java.util.List;
import rx.Observable;

public class ProductRepository {
    private final ProductDataSource.RemoteDataSource mRemoteDataSource;
    private final ProductDataSource.LocalDataSource mLocalProductDataSource;
    private int mCurrentDomainId;

    public ProductRepository(ProductRemoteDataSource remoteDataSource,
            ProductLocalDataSource productLocalDataSource, int currentDomainId) {
        mRemoteDataSource = remoteDataSource;
        mLocalProductDataSource = productLocalDataSource;
        mCurrentDomainId = currentDomainId;
    }

    public ProductRepository(ProductRemoteDataSource productRemoteDataSource,
            ProductLocalDataSource productLocalDataSource) {
        mRemoteDataSource = productRemoteDataSource;
        mLocalProductDataSource = productLocalDataSource;
    }

    public Observable<List<Product>> getListProduct() {
        return mRemoteDataSource.getListProduct(mCurrentDomainId);
    }

    public Observable<List<Product>> getListProductByCategory(int categoryId) {
        return mRemoteDataSource.getListProductByCategory(mCurrentDomainId, categoryId);
    }

    public Observable<List<Product>> getListProductByFillter(int categoryId, String priceSort,
            int priceFrom, int priceTo) {
        return mRemoteDataSource.getListProductByFillter(mCurrentDomainId, categoryId, priceSort,
                priceFrom, priceTo);
    }

    public Observable<List<Product>> getListAllProductByFillter(String priceSort, int priceFrom,
            int priceTo) {
        return mRemoteDataSource.getListAllProductByFillter(mCurrentDomainId, priceSort, priceFrom,
                priceTo);
    }

    public Observable<OrderCartResponse> orderCart(OrderRequest orderRequest) {
        return mRemoteDataSource.orderProduct(mCurrentDomainId, orderRequest);
    }

    public Observable<Void> addToCart(Product product) {
        return mLocalProductDataSource.addShoppingCard(product, mCurrentDomainId);
    }

    public Observable<Void> deleteCart(int productId) {
        return mLocalProductDataSource.deleteShoppingCard(productId, mCurrentDomainId);
    }

    public Observable<Void> editNoteCart(int productId, String note) {
        return mLocalProductDataSource.editNoteShoppingCart(productId, mCurrentDomainId, note);
    }

    public Observable<Void> upQuantity(int productId) {
        return mLocalProductDataSource.upQuantity(productId, mCurrentDomainId);
    }

    public Observable<Void> downQuantity(int productId) {
        return mLocalProductDataSource.downQuantity(productId, mCurrentDomainId);
    }

    public Observable<Integer> getNumberItem() {
        return mLocalProductDataSource.getNumberItem(mCurrentDomainId);
    }

    public Observable<Double> getTotalPrice() {
        return mLocalProductDataSource.getTotalPrice(mCurrentDomainId);
    }

    public Observable<List<Cart>> getAllShoppingCart() {
        return mLocalProductDataSource.getAllShoppingCart(mCurrentDomainId);
    }

    public Observable<Void> removeOrderOneShop(int shopId) {
        return mLocalProductDataSource.removeOrderOneShop(shopId, mCurrentDomainId);
    }

    public Observable<Void> removeAllOrder() {
        return mLocalProductDataSource.removeAllOrder(mCurrentDomainId);
    }

    public void openTransaction() {
        mLocalProductDataSource.openTransaction();
    }

    public void closeTransaction() {
        mLocalProductDataSource.closeTransaction();
    }

    public Observable<List<Product>> getListProductInShop(int shopId) {
        return mRemoteDataSource.getListProductInShop(shopId, mCurrentDomainId);
    }

    public Observable<List<Comment>> getListCommentInProduct(int productId) {
        return mRemoteDataSource.getListCommentInProduct(productId, mCurrentDomainId);
    }

    public Observable<BaseResponse> sendComment(CommentRequest request) {
        return mRemoteDataSource.sendComment(request);
    }

    public Observable<List<Product>> getListProductInShopInformation(int shopId) {
        return mRemoteDataSource.getListProductInShopInformation(shopId);
    }

    public Observable<RegisterProductResponse> requestRegisterProduct(
            RegisterProductRequest registerProductRequest) {
        return mRemoteDataSource.requestRegisterProduct(registerProductRequest);
    }

    public Observable<UpdateProductResponse> requestUpdateProduct(int productId,
            UpdateProductRequest updateProductRequest) {
        return mRemoteDataSource.requestUpdateProduct(productId, updateProductRequest);
    }

    public Observable<BaseResponse> requestDeleteProductInShop(int productId, int shopId) {
        return mRemoteDataSource.requestDeleteProductInShop(productId, shopId);
    }

    public Observable<BaseResponse> deleteCommentInProduct(int commentId) {
        return mRemoteDataSource.deleteCommentInProduct(commentId);
    }
}
