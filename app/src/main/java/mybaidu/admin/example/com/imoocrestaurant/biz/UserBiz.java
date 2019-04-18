package mybaidu.admin.example.com.imoocrestaurant.biz;

import com.zhy.http.okhttp.OkHttpUtils;

import mybaidu.admin.example.com.imoocrestaurant.bean.User;
import mybaidu.admin.example.com.imoocrestaurant.net.CommonCallback;
import mybaidu.admin.example.com.imoocrestaurant.utils.Config;

public class UserBiz {
    public void login(String username, String password, CommonCallback<User> userCommonCallback) {
        OkHttpUtils.post().url(Config.baseUrl + "user_login").tag(this)
                .addParams("username", username).addParams("password", password).build()
                .execute(userCommonCallback);
    }

    public void register(String username, String password, CommonCallback<User> userCommonCallback) {
        OkHttpUtils.post().url(Config.baseUrl + "user_register").tag(this)
                .addParams("username", username).addParams("password", password).build()
                .execute(userCommonCallback);
    }

    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
