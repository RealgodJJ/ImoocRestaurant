package mybaidu.admin.example.com.imoocrestaurant.biz;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;
import java.util.Map;

import mybaidu.admin.example.com.imoocrestaurant.bean.Order;
import mybaidu.admin.example.com.imoocrestaurant.bean.Product;
import mybaidu.admin.example.com.imoocrestaurant.net.CommonCallback;
import mybaidu.admin.example.com.imoocrestaurant.utils.Config;

/**
 * Created by RealgodJJ
 * on 2019/4/17
 */

public class OrderBiz {
    public void listByPage(int currentPage, CommonCallback<List<Order>> commonCallback) {
        OkHttpUtils.post().url(Config.baseUrl + "order_find").tag(this)
                .addParams("currentPage", currentPage + "").build().execute(commonCallback);
    }

    public void add(Order order, CommonCallback<String> commonCallback) {
        //order_add
        //res_id
        //product_str, id_数量|id_数量|
        //count
        //price
        StringBuilder stringBuilder = new StringBuilder();
        Map<Product, Integer> productQuantityMap = order.productQuantityMap;
        for (Product product : productQuantityMap.keySet()) {
            stringBuilder.append(product.getId()).append("_")
                    .append(productQuantityMap.get(product)).append("|");
        }
        stringBuilder = stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        OkHttpUtils.post().url(Config.baseUrl + "order_add")
                .addParams("res_id", order.getRestaurant().getId() + "")
                .addParams("product_str", stringBuilder.toString())
                .addParams("count", order.getCount() + "")
                .addParams("price", order.getPrice() + "")
                .tag(this).build().execute(commonCallback);
    }

    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
