package mybaidu.admin.example.com.imoocrestaurant.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import mybaidu.admin.example.com.imoocrestaurant.R;
import mybaidu.admin.example.com.imoocrestaurant.bean.Order;
import mybaidu.admin.example.com.imoocrestaurant.utils.Config;
import mybaidu.admin.example.com.imoocrestaurant.utils.Toasts;

public class OrderDetailActivity extends BaseActivity {
    public static final String KEY_ORDER = "key_order";
    private ImageView ivDish;
    private TextView tvDishName;
    private TextView tvDishDescription;
    private TextView tvDishPrice;
    private Order order;

    public static void launch(Context context, Order order) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(KEY_ORDER, order);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        setUpToolbar();
        setTitle(R.string.order_detail);

        Intent intent = getIntent();
        if (intent != null) {
            Serializable serializable = intent.getSerializableExtra(KEY_ORDER);
            order = (Order) serializable;
        }

        if (order == null) {
            Toasts.showToast(getString(R.string.params_error));
            finish();
            return;
        }

        initView();
    }

    private void initView() {
        ivDish = findViewById(R.id.iv_dish);
        tvDishName = findViewById(R.id.tv_dish_name);
        tvDishDescription = findViewById(R.id.tv_dish_description);
        tvDishPrice = findViewById(R.id.tv_dish_price);

        Picasso.get()
                .load(Config.baseUrl + order.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(ivDish);
        tvDishName.setText(order.getRestaurant().getName());

        List<Order.ProductVo>  productVoList = order.productVos;
        StringBuilder stringBuilder = new StringBuilder();
        for (Order.ProductVo productVo : productVoList) {
            stringBuilder.append(productVo.product.getName()).append(" * ")
                    .append(productVo.count).append("\n");
        }
        tvDishDescription.setText(stringBuilder.toString());
        tvDishPrice.setText(getString(R.string.total_cost, order.getPrice()));
    }
}
