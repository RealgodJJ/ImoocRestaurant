package mybaidu.admin.example.com.imoocrestaurant.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

import mybaidu.admin.example.com.imoocrestaurant.R;
import mybaidu.admin.example.com.imoocrestaurant.bean.Product;
import mybaidu.admin.example.com.imoocrestaurant.utils.Config;
import mybaidu.admin.example.com.imoocrestaurant.utils.Toasts;

public class ProductDetailActivity extends BaseActivity {
    public static final String KEY_PRODUCT = "key_product";
    private ImageView ivDish;
    private TextView tvDishName;
    private TextView tvDishDescription;
    private TextView tvDishPrice;
    private Product product;

    public static void launch(Context context, Product product) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(KEY_PRODUCT, product);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        setUpToolbar();
        setTitle(R.string.dish_detail);

        Intent intent = getIntent();
        if (intent != null) {
            Serializable serializable = intent.getSerializableExtra(KEY_PRODUCT);
            product = (Product) serializable;
        }

        if (product == null) {
            Toasts.showToast(getString(R.string.params_error));
        }

        initView();
    }

    private void initView() {
        ivDish = findViewById(R.id.iv_dish);
        tvDishName = findViewById(R.id.tv_dish_name);
        tvDishDescription = findViewById(R.id.tv_dish_description);
        tvDishPrice = findViewById(R.id.tv_dish_price);

        Picasso.get()
                .load(Config.baseUrl + product.getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(ivDish);
        tvDishName.setText(product.getName());
        tvDishDescription.setText(product.getDescription());
        tvDishPrice.setText(getString(R.string.single_product_price, product.getPrice()));
    }
}
