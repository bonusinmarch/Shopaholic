package com.bumpaw.bonuses.shopaholic;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by user on 19/07/2016.
 */
public class ProductAdapter extends
        BaseAdapter{

    private Activity activity;
    private ArrayList<Product> listItem;

    public ProductAdapter(Activity activity) {
        this.activity= activity;
    }

    public ArrayList<Product> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<Product> listItem) {
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return getListItem().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_product, null);
            holder = new ViewHolder();
            holder.imgItem = (ImageView)view.findViewById(R.id.img_item_product);
            holder.tvName = (TextView) view.findViewById(R.id.tv_item_name);
            holder.tvPrice = (TextView) view.findViewById(R.id.tv_item_price);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        Product item = getListItem().get(position);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(item.getPrice());

        Glide.with(activity).load(item.getImageUri()).into(holder.imgItem);

        return view;
    }

    static class ViewHolder{
        ImageView imgItem;
        TextView tvName, tvPrice;
    }
}
