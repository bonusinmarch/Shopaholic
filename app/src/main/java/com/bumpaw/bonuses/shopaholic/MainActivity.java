package com.bumpaw.bonuses.shopaholic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener{

    private ListView lvCategory;
    private String[] categories = new String[]{
            "MakeUp", "Tas", "Sepatu", "Lingerie", "Dress", "Celana"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Shopaholic Home");

        lvCategory = (ListView)findViewById(R.id.lv_category);
        lvCategory.setOnItemClickListener(this);


        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, categories);
        lvCategory.setAdapter(categoryAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //Toast.makeText(MainActivity.this,"Item clicked"+categories[position], Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
            intent.putExtra("category", categories[position]);
            startActivity(intent);
    }
}
