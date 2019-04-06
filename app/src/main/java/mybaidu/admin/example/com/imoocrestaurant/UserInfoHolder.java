package mybaidu.admin.example.com.imoocrestaurant;

import android.text.TextUtils;

import mybaidu.admin.example.com.imoocrestaurant.bean.User;
import mybaidu.admin.example.com.imoocrestaurant.utils.SPUtils;

/**
 * Created by RealgodJJ
 * on 2019/4/6
 */

public class UserInfoHolder {
    private static UserInfoHolder mInstance = new UserInfoHolder();
    private User user;
    private static final String KEY_UESRNAME = "key_username";
    private static final String KEY_PASSWORD = "key_password";

    public static UserInfoHolder getInstance() {
        return mInstance;
    }

    public User getUser() {
        User u = user;
        if (u == null) {
            String username = (String) SPUtils.getInstance().get(KEY_UESRNAME, "ERROR");
            if (!TextUtils.isEmpty(username)) {
                u = new User();
                u.setUserName(username);
            }
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            SPUtils.getInstance().put(KEY_UESRNAME, user.getUserName());
            SPUtils.getInstance().put(KEY_PASSWORD, user.getPassword());
        }
    }
}
