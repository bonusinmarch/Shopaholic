package com.bumpaw.bonuses.shopaholic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumpaw.bonuses.shopaholic.db.CartHelper;
import com.bumpaw.bonuses.shopaholic.db.CartItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.lv_item3)
    ListView lvItem3;
    @BindView(R.id.tv_total_qty)
    TextView tvTotalQty;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.btn_submit_order)
    Button btnSubmitOrder;
    @BindView(R.id.ln_cart_summary)
    LinearLayout lnCartSummary;

    private ArrayList<CartItem> list;
    private CartHelper mCartHelper;
    private CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCartHelper = new CartHelper(CartActivity.this);
        list = mCartHelper.getAll();

        lnCartSummary.setVisibility(View.GONE);

        adapter = new CartAdapter(CartActivity.this);
        if (list != null) {
            if (list.size() > 0) {
                lnCartSummary.setVisibility(View.VISIBLE);
                adapter.setList(list);
                showTotalTransactionInfo();

            } else {
                adapter.setList(new ArrayList<CartItem>());
                Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            adapter.setList(new ArrayList<CartItem>());
            Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
        }
        lvItem3.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTotalTransactionInfo(){
        ArrayList<CartItem> list = mCartHelper.getAll();
        if (list == null){
            lnCartSummary.setVisibility(View.GONE);
        }else {
            int totalQty = 0;
            int totalPay = 0;

            for (CartItem item :list){
                totalQty +=item.getQty();
                totalPay += (item.getQty() * item.getPrice());
            }

            tvTotalQty.setText("Total Qty : " +totalQty);
            tvTotalPrice.setText("Total Pay : "+totalPay);
        }
    }

    @Subscribe
    public void onEvent (RefreshCartEven event){
        showTotalTransactionInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
