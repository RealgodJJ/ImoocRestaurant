package mybaidu.admin.example.com.imoocrestaurant.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import mybaidu.admin.example.com.imoocrestaurant.R;
import mybaidu.admin.example.com.imoocrestaurant.ui.view.refresh.SwipeRefresh;
import mybaidu.admin.example.com.imoocrestaurant.ui.view.refresh.SwipeRefreshLayout;

public class ProductListActivity extends BaseActivity {
    private SwipeRefreshLayout srlMenu;
    private RecyclerView rvMenu;
    private TextView tvCount;
    private Button btPayForDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        setUpToolbar();
        setTitle(R.string.order);

        initView();
        initEvent();
    }

    private void initEvent() {

    }

    private void initView() {
        srlMenu = findViewById(R.id.srl_menu);
        rvMenu = findViewById(R.id.rv_menu);
        tvCount = findViewById(R.id.tv_count);
        btPayForDinner = findViewById(R.id.bt_pay_for_dinner);

        srlMenu.setMode(SwipeRefresh.Mode.BOTH);
        srlMenu.setColorSchemeColors(Color.RED, Color.BLACK, Color.GREEN, Color.YELLOW);
    }
}
