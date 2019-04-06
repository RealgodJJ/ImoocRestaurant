package mybaidu.admin.example.com.imoocrestaurant.utils;

import com.google.gson.Gson;

/**
 * Created by zhy on 16/10/23.
 */
public class GsonUtil {

    private static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

}
