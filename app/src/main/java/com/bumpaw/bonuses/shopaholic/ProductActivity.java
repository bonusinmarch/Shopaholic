package com.bumpaw.bonuses.shopaholic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener{

    private ListView lvItem;
    private ProductAdapter adapter;
    private ArrayList<Product> listProduct;
    private String cate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        cate = getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(cate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvItem = (ListView)findViewById(R.id.lv_item);
        listProduct = new ArrayList<>();
        adapter = new ProductAdapter(ProductActivity.this);
        adapter.setListItem(listProduct);

        Product mProduct = null;
        for (int i = 0; i < SampleData.makeup.length; i++){
            mProduct = new Product();
            mProduct.setId(System.currentTimeMillis());
            mProduct.setName(SampleData.makeup[i][0]);
            mProduct.setPrice(SampleData.makeup[i][2]);
            mProduct.setImageUri(SampleData.makeup[i][1]);

            listProduct.add(mProduct);
        }

        adapter.setListItem(listProduct);
        adapter.notifyDataSetChanged();
        lvItem.setAdapter(adapter);

        lvItem.setOnItemClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ProductActivity.this, DetailProductActivity.class);
        intent.putExtra("product", listProduct.get(position));
        startActivity(intent);
    }
}
