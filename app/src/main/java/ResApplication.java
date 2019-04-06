import android.annotation.SuppressLint;
import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import mybaidu.admin.example.com.imoocrestaurant.utils.SPUtils;
import mybaidu.admin.example.com.imoocrestaurant.utils.Toasts;
import okhttp3.OkHttpClient;

/**
 * Created by RealgodJJ
 * on 2019/4/6
 */

@SuppressLint("Registered")
public class ResApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Toasts.init(this);
        SPUtils.init(this, "sp_user.pref");

        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS).cookieJar(cookieJar).build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
