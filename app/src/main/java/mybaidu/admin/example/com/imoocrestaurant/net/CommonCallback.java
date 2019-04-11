package mybaidu.admin.example.com.imoocrestaurant.net;

import com.google.gson.internal.$Gson$Types;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import mybaidu.admin.example.com.imoocrestaurant.utils.GsonUtil;
import okhttp3.Call;

/**
 * 处理网络回调
 *
 * @param <T>
 */
public abstract class CommonCallback<T> extends StringCallback {
    private Type type;
    private static final String RESULT_CODE = "resultCode";
    private static final String RESULT_MESSAGE = "resultMessage";
    private static final String DATA = "data";

    protected CommonCallback() {
        type = getSuperclassTypeParameter(getClass());
    }

    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        //返回直接继承的父类（包含泛型参数）
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize((parameterized != null ?
                parameterized.getActualTypeArguments() : new Type[0])[0]);
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
