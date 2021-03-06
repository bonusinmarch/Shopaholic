package com.bumpaw.bonuses.shopaholic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumpaw.bonuses.shopaholic.db.CartHelper;
import com.bumpaw.bonuses.shopaholic.db.CartItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity implements
        View.OnClickListener{

    private TextView tvDetName, tvDetPrice, tvDetDesc;
    private Button btn_cart;
    private ImageView imgDetail;
    private Spinner spn_count;
    private ImageView imgThumbA, imgThumbB, imgThumbC, imgThumbD;
    private int currentImagePosition=0;

    private TextView tvCart, tvTitle;
    private ImageView imgCart;

    private CartHelper cartHelper;
    private Toolbar toolbar;

    private Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);



        tvDetName = (TextView)findViewById(R.id.tv_det_name);
        tvDetPrice = (TextView)findViewById(R.id.tv_det_price);
        tvDetDesc = (TextView)findViewById(R.id.tv_det_desc);
        btn_cart = (Button)findViewById(R.id.btn_det_addcart);
        imgDetail = (ImageView)findViewById(R.id.img_det);
        spn_count = (Spinner)findViewById(R.id.spin_count);

        imgThumbA = (ImageView)findViewById(R.id.img_thumb_a);
        imgThumbB = (ImageView)findViewById(R.id.img_thumb_b);
        imgThumbC = (ImageView)findViewById(R.id.img_thumb_c);
        imgThumbD = (ImageView)findViewById(R.id.img_thumb_d);


        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvCart = (TextView)findViewById(R.id.tv_cart);
        imgCart = (ImageView)findViewById(R.id.img_cart);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        imgCart.setOnClickListener(this);

        imgThumbA.setOnClickListener(this);
        imgThumbB.setOnClickListener(this);
        imgThumbC.setOnClickListener(this);
        imgThumbD.setOnClickListener(this);

        imgDetail.setOnClickListener(this);
        btn_cart.setOnClickListener(this);

        setSupportActionBar(toolbar);
        tvTitle.setText("Detail Produk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedProduct = getIntent().getParcelableExtra("product");
        tvDetName.setText(selectedProduct.getName());
        tvDetPrice.setText(selectedProduct.getPrice());
        Glide.with(DetailProductActivity.this).load(selectedProduct.getImageUri()).into(imgDetail);

        String[] jumlah = new String[] {
                "Masukkan Jumlah", "1", "2", "3", "4", "5"
        };

        ArrayAdapter<String> countAdapter = new ArrayAdapter<String>(DetailProductActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1, jumlah);
        spn_count.setAdapter(countAdapter);

        tvDetDesc.setText("Hampir semua wanita menginginkan alis yang sangat sempurna dan rata agar terlihat lebih indah dan enak dipandang oleh banyak orang. Anda yang super sibuk, pastinya menginginkan bentuk yang rapi dan sempurna dalam sekejap tanpa berantakan. Untuk itu, Anda harus menggunakan NYX Eyebrow kit with stencil agar alis Anda terlihat lebih indah.\n" +
                "\n" +
                "kit termasuk 4 warna menyanjung universal untuk semua nada alis, sikat alis miring, sisir alis, dan 3 stensil yang berbeda untuk membantu Anda mendapatkan bentuk yang sempurna untuk wajah Anda. Sekarang Anda dapat mengisi, menentukan, dan bentuk alis Anda tidak seperti sebelumnya!");

        Glide.with(DetailProductActivity.this).load(SampleData.makeupthumb[0]).into(imgThumbA);
        Glide.with(DetailProductActivity.this).load(SampleData.makeupthumb[1]).into(imgThumbB);
        Glide.with(DetailProductActivity.this).load(SampleData.makeupthumb[2]).into(imgThumbC);
        Glide.with(DetailProductActivity.this).load(SampleData.makeupthumb[3]).into(imgThumbD);

        cartHelper = new CartHelper(DetailProductActivity.this);


    }


    //dembele
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String imageUri = null;
        switch (v.getId()){
            case R.id.img_thumb_a:
                imageUri = SampleData.makeupthumb[0];
                currentImagePosition=0;
                break;
            case R.id.img_thumb_b:
                imageUri = SampleData.makeupthumb[1];
                currentImagePosition=1;
                break;
            case R.id.img_thumb_c:
                imageUri = SampleData.makeupthumb[2];
                currentImagePosition=2;

                break;
            case R.id.img_thumb_d:
                imageUri = SampleData.makeupthumb[3];
                currentImagePosition=3;

                break;
            case R.id.img_det:
                ArrayList<String> list = new ArrayList<>();
                for (int i=0; i < SampleData.makeupthumb.length; i++){
                    list.add(SampleData.makeupthumb[i]);
                }
                Intent mIntent = new Intent(DetailProductActivity.this, DetailImageActivity.class);
                mIntent.putExtra("url_images", list);
                mIntent.putExtra("position", currentImagePosition);
                startActivity(mIntent);
                break;
            case R.id.btn_det_addcart:
                if (cartHelper.isItemAlreadyExist((int)selectedProduct.getId())){
                    Toast.makeText(DetailProductActivity.this, "This Product Already in Cart", Toast.LENGTH_SHORT).show();
                }else {
                    cartHelper.create((int)selectedProduct.getId(),
                            selectedProduct.getName(),
                            selectedProduct.getImageUri(), 1,
                            Double.parseDouble(selectedProduct.getPrice()));
                    Toast.makeText(DetailProductActivity.this, "This Product Successfully Added", Toast.LENGTH_SHORT).show();
                    updateCartQty();
                }
                break;

            case R.id.img_cart:
                Intent intent = new Intent(DetailProductActivity.this, CartActivity.class);
                startActivity(intent);
                break;

        };

        if (imageUri != null){
            Glide.with(DetailProductActivity.this).load(imageUri).into(imgDetail);
        }
    }

    private void updateCartQty(){
        ArrayList<CartItem> list = cartHelper.getAll();
        tvCart.setVisibility(View.GONE);
        if (list != null){
            if (list.size() > 0) {
                int cartQty = list.size();
                tvCart.setVisibility(View.VISIBLE);
                tvCart.setText(String.valueOf(cartQty));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartQty();
    }
}
