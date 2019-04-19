package mybaidu.admin.example.com.imoocrestaurant.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mybaidu.admin.example.com.imoocrestaurant.R;
import mybaidu.admin.example.com.imoocrestaurant.bean.Order;
import mybaidu.admin.example.com.imoocrestaurant.bean.Product;
import mybaidu.admin.example.com.imoocrestaurant.biz.OrderBiz;
import mybaidu.admin.example.com.imoocrestaurant.biz.ProductBiz;
import mybaidu.admin.example.com.imoocrestaurant.net.CommonCallback;
import mybaidu.admin.example.com.imoocrestaurant.ui.adapter.ProductAdapter;
import mybaidu.admin.example.com.imoocrestaurant.ui.view.refresh.SwipeRefresh;
import mybaidu.admin.example.com.imoocrestaurant.ui.view.refresh.SwipeRefreshLayout;
import mybaidu.admin.example.com.imoocrestaurant.utils.Toasts;
import mybaidu.admin.example.com.imoocrestaurant.vo.ProductItem;

public class ProductListActivity extends BaseActivity {
    private SwipeRefreshLayout srlMenu;
    private RecyclerView rvMenu;
    private TextView tvCount;
    private Button btPayForDinner;
    private ProductAdapter productAdapter;
    private List<ProductItem> productItemList = new ArrayList<>();
    private OrderBiz orderBiz = new OrderBiz();
    private ProductBiz productBiz = new ProductBiz();
    private int currentPage = 0;

    private float totalPrice;
    private int totalCount;
    private Order order = new Order();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        setUpToolbar();
        setTitle(R.string.order);

        initView();
        initEvent();
        loadData();
    }

    private void initEvent() {
        srlMenu.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();
            }
        });

        srlMenu.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        tvCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (totalCount == 0) {
                    btPayForDinner.setEnabled(false);
                    Toasts.showToast(getString(R.string.not_choose_dish));
                } else {
                    btPayForDinner.setEnabled(true);
                }
            }
        });

        btPayForDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:跳转到支付界面
                order.setCount(totalCount);
                order.setPrice(totalPrice);
                order.setRestaurant(productItemList.get(0).getRestaurant());

                startLoadingProgress();
                orderBiz.add(order, new CommonCallback<String>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        Toasts.showToast(e.getMessage());
                        toLoginActivity();
                    }

                    @Override
                    public void onSuccess(String response) {
                        stopLoadingProgress();
                        Toasts.showToast(getString(R.string.create_order_success));
                        finish();
                    }
                });
            }
        });

        productAdapter.setOnProductListener(new ProductAdapter.OnProductListener() {
            @Override
            public void onProductAdd(ProductItem productItem) {
                totalCount++;
                totalPrice += productItem.getPrice();
                tvCount.setText(getString(R.string.count_0, totalCount));
                btPayForDinner.setText(getString(R.string.pay_for_dinner, totalPrice));
                order.addProduct(productItem);
            }

            @Override
            public void onProductSub(ProductItem productItem) {
                totalCount--;
                totalPrice -= productItem.getPrice();
                tvCount.setText(getString(R.string.count_0, totalCount));
                btPayForDinner.setText(getString(R.string.pay_for_dinner, totalPrice));
                order.subProduct(productItem);
            }
        });
    }

    private void loadMore() {
        productBiz.listByPage(++currentPage, new CommonCallback<List<Product>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                Toasts.showToast(e.getMessage());
                currentPage--;
                if (srlMenu.isRefreshing())
                    srlMenu.setPullUpRefreshing(false);
            }

            @Override
            public void onSuccess(List<Product> response) {
                stopLoadingProgress();
                srlMenu.setPullUpRefreshing(false);
                if (response.size() == 0) {
                    Toasts.showToast(getString(R.string.no_more_dish));
                    return;
                }
                Toasts.showToast(getString(R.string.find_out_more_dishes, response.size()));
                for (Product product : response) {
                    productItemList.add(new ProductItem(product));
                }
                productAdapter.notifyDataSetChanged();
            }
        });
    }

    //每行菜品数量、总数量、总价全部清零
    private void loadData() {
        startLoadingProgress();
        productBiz.listByPage(0, new CommonCallback<List<Product>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                Toasts.showToast(e.getMessage());
                if (srlMenu.isRefreshing())
                    srlMenu.setRefreshing(false);
            }

            @Override
            public void onSuccess(List<Product> response) {
                stopLoadingProgress();
                srlMenu.setRefreshing(false);
                currentPage = 0;
                productItemList.clear();
                for (Product product : response) {
                    productItemList.add(new ProductItem(product));
                }
                productAdapter.notifyDataSetChanged();
                //清空选择的数据、数量、价格
                totalCount = 0;
                totalPrice = 0;

                tvCount.setText(getString(R.string.count_0, totalCount));
                btPayForDinner.setText(getString(R.string.pay_for_dinner, totalPrice));
            }
        });
    }

    private void initView() {
        srlMenu = findViewById(R.id.srl_menu);
        rvMenu = findViewById(R.id.rv_menu);
        tvCount = findViewById(R.id.tv_count);
        btPayForDinner = findViewById(R.id.bt_pay_for_dinner);

        srlMenu.setMode(SwipeRefresh.Mode.BOTH);
        srlMenu.setColorSchemeColors(Color.RED, Color.BLACK, Color.GREEN, Color.YELLOW);

        productAdapter = new ProductAdapter(this, productItemList);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setAdapter(productAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productBiz.onDestroy();
    }
}
