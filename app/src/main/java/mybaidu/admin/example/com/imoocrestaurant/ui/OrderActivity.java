package mybaidu.admin.example.com.imoocrestaurant.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mybaidu.admin.example.com.imoocrestaurant.R;
import mybaidu.admin.example.com.imoocrestaurant.UserInfoHolder;
import mybaidu.admin.example.com.imoocrestaurant.bean.User;
import mybaidu.admin.example.com.imoocrestaurant.ui.view.CircleTransform;
import mybaidu.admin.example.com.imoocrestaurant.ui.view.refresh.SwipeRefresh;
import mybaidu.admin.example.com.imoocrestaurant.ui.view.refresh.SwipeRefreshLayout;

public class OrderActivity extends BaseActivity {
    private ImageView ivAvatar;
    private TextView tvUsername;
    private Button btTakeOrder;
    private RecyclerView rvMyHistoryOrder;
    private SwipeRefreshLayout srlOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();
        initEvent();
    }

    private void initEvent() {

    }

    private void initView() {
        ivAvatar = findViewById(R.id.iv_avatar);
        tvUsername = findViewById(R.id.tv_username);
        btTakeOrder = findViewById(R.id.bt_take_order);
        rvMyHistoryOrder = findViewById(R.id.rv_my_history_order);
        srlOrders = findViewById(R.id.srl_orders);

        User user = UserInfoHolder.getInstance().getUser();
        //判断是否成功获取用户状态
        if (user != null) {
            tvUsername.setText(user.getUsername());
            ivAvatar.setImageResource(R.drawable.icon);
        } else {
            toLoginActivity();
            finish();
            return;
        }

        srlOrders.setMode(SwipeRefresh.Mode.BOTH);
        srlOrders.setColorSchemeColors(Color.RED, Color.BLACK, Color.GREEN, Color.YELLOW);

        rvMyHistoryOrder.setLayoutManager(new LinearLayoutManager(this));
//        rvMyHistoryOrder.setAdapter();

        Picasso.get().load(R.drawable.icon).placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform()).into(ivAvatar);
    }
}
