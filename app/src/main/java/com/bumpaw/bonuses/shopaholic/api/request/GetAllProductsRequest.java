package com.bumpaw.bonuses.shopaholic.api.request;

import com.bumpaw.bonuses.shopaholic.Product;
import com.bumpaw.bonuses.shopaholic.api.BaseApi;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 21/07/2016.
 */
public class GetAllProductsRequest extends BaseApi {

    private OnGetAllProductRequestListener onGetAllProductRequestListener;
    private AsyncHttpClient client;

    public GetAllProductsRequest(){
        client = getHttpClient();
    }

    public OnGetAllProductRequestListener getOnGetAllProductRequestListener(){
        return  onGetAllProductRequestListener;
    }

    public void setOnGetAllProductRequestListener(OnGetAllProductRequestListener onGetAllProductRequestListener) {
        this.onGetAllProductRequestListener = onGetAllProductRequestListener;
    }

    public interface OnGetAllProductRequestListener{
        void onGetAllProductsSuccess(ArrayList<Product> listProduct);
        void onGetAllProductsFailure(String errorMessage);
    }


    @Override
    public void callApi() {
        super.callApi();
        client.get(GET_ALL_PRODUCTS, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                ArrayList<Product> list = getAllProducts(response);
                if (list != null) {
                    if (list.size()> 0){
                        getOnGetAllProductRequestListener().onGetAllProductsSuccess(list);
                    } else {
                        getOnGetAllProductRequestListener().onGetAllProductsFailure("No Data Found");
                    }
                } else {
                    getOnGetAllProductRequestListener().onGetAllProductsFailure("No Data Found");

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                getOnGetAllProductRequestListener().onGetAllProductsFailure("Could Not Connect to server");

            }
        });
    }

    @Override
    public void cancelRequest() {
        super.cancelRequest();
        if (client !=null){
            client.cancelAllRequests(true);
        }
    }

    private ArrayList<Product> getAllProducts(String response){
        ArrayList<Product> list = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("200")){
                JSONArray productItems = jsonObject.getJSONArray("products");
                if (productItems.length() > 0){
                    list = new ArrayList<>();
                    Product mProduct = null;
                    for (int i=0; i<productItems.length(); i++) {
                        JSONObject item = productItems.getJSONObject(i);
                        mProduct = new Product();
                        mProduct.setId(Long.parseLong(item.getString("product_id")));
                        mProduct.setImageUri(item.getString("img_url"));
                        mProduct.setName(item.getString("name"));
                        mProduct.setPrice(item.getString("price"));

                        list.add(mProduct);
                    }
                }else {
                    return null;
                }
            }else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
        return list;
    }
}
