package mybaidu.admin.example.com.imoocrestaurant.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mybaidu.admin.example.com.imoocrestaurant.R;
import mybaidu.admin.example.com.imoocrestaurant.UserInfoHolder;
import mybaidu.admin.example.com.imoocrestaurant.bean.Order;
import mybaidu.admin.example.com.imoocrestaurant.bean.User;
import mybaidu.admin.example.com.imoocrestaurant.biz.OrderBiz;
import mybaidu.admin.example.com.imoocrestaurant.net.CommonCallback;
import mybaidu.admin.example.com.imoocrestaurant.ui.adapter.OrderAdapter;
import mybaidu.admin.example.com.imoocrestaurant.ui.view.CircleTransform;
import mybaidu.admin.example.com.imoocrestaurant.ui.view.refresh.SwipeRefresh;
import mybaidu.admin.example.com.imoocrestaurant.ui.view.refresh.SwipeRefreshLayout;
import mybaidu.admin.example.com.imoocrestaurant.utils.Toasts;

public class OrderActivity extends BaseActivity {
    private ImageView ivAvatar;
    private TextView tvUsername;
    private Button btTakeOrder;
    private RecyclerView rvMyHistoryOrder;
    private SwipeRefreshLayout srlOrders;
    private OrderAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private OrderBiz orderBiz = new OrderBiz();
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLoadingProgress();
        loadData();
    }

    private void initEvent() {
        btTakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProductListActivity();
            }
        });
    }

    private void toProductListActivity() {
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivityForResult(intent, 1001);
    }

    private void loadMore() {
        orderBiz.listByPage(++currentPage, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                Toasts.showToast(e.getMessage());
                srlOrders.setPullUpRefreshing(false);
                currentPage--;

                String message = e.getMessage();
                if (message.contains(getString(R.string.user_not_login))) {
                    toLoginActivity();
                }
            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
                if (response.size() == 0) {
                    Toasts.showToast(getString(R.string.no_more_orders));
                    srlOrders.setPullUpRefreshing(false);
                    return;
                }
                Toasts.showToast(getString(R.string.load_orders_success));
                orderList.addAll(response);
                orderAdapter.notifyDataSetChanged();
                srlOrders.setPullUpRefreshing(false);
            }
        });
    }

    private void loadData() {
        orderBiz.listByPage(0, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                Toasts.showToast(e.getMessage());
                if (srlOrders.isRefreshing()) {
                    srlOrders.setRefreshing(false);
                }
                if (e.getMessage().contains(getString(R.string.user_not_login))) {
                    toLoginActivity();
                }
            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
//                currentPage = 0;
                Toasts.showToast(getString(R.string.refresh_order_success));
                orderList.clear();
                orderList.addAll(response);
                orderAdapter.notifyDataSetChanged();
                if (srlOrders.isRefreshing()) {
                    srlOrders.setRefreshing(false);
                }
            }
        });
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
            return;
        }

        srlOrders.setMode(SwipeRefresh.Mode.BOTH);
        srlOrders.setColorSchemeColors(Color.RED, Color.BLACK, Color.GREEN, Color.YELLOW);
        //下拉刷新订单
        srlOrders.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        //上拉显示更多
        srlOrders.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();
            }
        });

        orderAdapter = new OrderAdapter(this, orderList);
        rvMyHistoryOrder.setLayoutManager(new LinearLayoutManager(this));
        rvMyHistoryOrder.setAdapter(orderAdapter);

        Picasso.get().load(R.drawable.icon).placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform()).into(ivAvatar);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
