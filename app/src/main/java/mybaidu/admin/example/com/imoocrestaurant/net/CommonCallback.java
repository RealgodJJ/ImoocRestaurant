package mybaidu.admin.example.com.imoocrestaurant.net;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import mybaidu.admin.example.com.imoocrestaurant.utils.GsonUtil;
import okhttp3.Call;

public abstract class CommonCallback<T> extends StringCallback {
    private Type type;
    private static final String RESULT_CODE = "resultCode";
    private static final String RESULT_MESSAGE = "resultMessage";
    private static final String DATA = "data";

    protected CommonCallback() {
        //
        Class<? extends CommonCallback> clazz = getClass();
        //返回直接继承的父类（包含泛型参数）
        Type genericSuperclass = clazz.getGenericSuperclass();

        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Miss type Params");
        }

        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        if (parameterizedType != null) {
            type = parameterizedType.getActualTypeArguments()[0];
        }
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        onError(e);
    }

    @Override
    public void onResponse(String response, int id) {
        try {
            JSONObject resJson = new JSONObject(response);
            int resultCode = resJson.getInt(RESULT_CODE);
            if (resultCode == 1) {
                String data = resJson.getString(DATA);
                //fromJson：将指定的Json反序列化为指定类的对象
                onSuccess((T) GsonUtil.getGson().fromJson(data, type));
            } else {
                onError(new RuntimeException(resJson.getString(RESULT_MESSAGE)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            onError(e);
        }
    }

    //创建两个抽象方法等待子类实现
    public abstract void onError(Exception e);

    public abstract void onSuccess(T response);
}
