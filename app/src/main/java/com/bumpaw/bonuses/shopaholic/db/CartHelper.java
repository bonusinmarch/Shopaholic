package com.bumpaw.bonuses.shopaholic.db;

import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by user on 22/07/2016.
 */
public class CartHelper {

    private Realm realm;
    private Context mContext;
    public CartHelper(Context mContext){
        this.mContext = mContext;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(mContext).build();
        realm = Realm.getInstance(realmConfiguration);
    }

    public void create(int productId,
                       String productName, String productImage,
                       int producQty, double productPrice){
        realm.beginTransaction();
        CartItem cartItem = realm.createObject(CartItem.class);
        cartItem.setId(productId);
        cartItem.setName(productName);
        cartItem.setImage(productImage);
        cartItem.setQty(producQty);
        cartItem.setPrice(productPrice);
        realm.commitTransaction();
    }

    public ArrayList<CartItem> getAll(){
        ArrayList<CartItem> list = null;
        RealmResults<CartItem> results = realm.where(CartItem.class).findAll();
        if (results.size() > 0){
            list = new ArrayList<>();
            for (CartItem item : results){
                list.add(item);
            }
        }
        return list;
    }

    public void update(int productQty, int productID){
        realm.beginTransaction();
        CartItem cartItem = realm.where(CartItem.class).equalTo("id", productID).findFirst();
        cartItem.setQty(productQty);
        realm.commitTransaction();
    }

    public void delete(int productID){
        realm.beginTransaction();
        RealmResults<CartItem> items = realm.where(CartItem.class).equalTo("id", productID).findAll();
        CartItem item = items.get(0);
        item.deleteFromRealm();
        realm.commitTransaction();
    }

    public boolean isItemAlreadyExist (int productId){
        CartItem item = realm.where(CartItem.class).equalTo("id", productId).findFirst();
        if (item !=null){
            return true;
        }else {
            return false;
        }
    }
}
