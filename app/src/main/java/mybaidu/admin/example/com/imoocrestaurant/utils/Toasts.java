package mybaidu.admin.example.com.imoocrestaurant.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class Toasts {

    private static Toast toast;

    public static void showToast(String content) {
        toast.setText(content);
        toast.show();
    }

    @SuppressLint("ShowToast")
    public static void init(Context context) {
        toast = Toast.makeText(context, "", android.widget.Toast.LENGTH_SHORT);
//        Toasts.makeText(context,"", Toasts.LENGTH_SHORT).show();
    }
}
