package mybaidu.admin.example.com.imoocrestaurant.biz;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import mybaidu.admin.example.com.imoocrestaurant.bean.Product;
import mybaidu.admin.example.com.imoocrestaurant.net.CommonCallback;
import mybaidu.admin.example.com.imoocrestaurant.utils.Config;

/**
 * Created by RealgodJJ
 * on 2019/4/18
 */

public class ProductBiz {
    public void listByPage(int currentPage, CommonCallback<List<Product>> commonCallback) {
        //product_find
        //currentPage
        OkHttpUtils.post().url(Config.baseUrl + "product_find")
                .addParams("currentPage", currentPage + "").tag(this)
                .build().execute(commonCallback);
    }

    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
