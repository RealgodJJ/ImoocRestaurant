package mybaidu.admin.example.com.imoocrestaurant.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mybaidu.admin.example.com.imoocrestaurant.R;
import mybaidu.admin.example.com.imoocrestaurant.UserInfoHolder;
import mybaidu.admin.example.com.imoocrestaurant.bean.User;
import mybaidu.admin.example.com.imoocrestaurant.biz.UserBiz;
import mybaidu.admin.example.com.imoocrestaurant.net.CommonCallback;
import mybaidu.admin.example.com.imoocrestaurant.utils.Toasts;

public class LoginActivity extends BaseActivity {
    private EditText etAccount;
    private EditText etPassword;
    private Button btLogin;
    private TextView tvRegister;
    private UserBiz userBiz = new UserBiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toasts.showToast(getString(R.string.can_not_empty));
                    return;
                }
                startLoadingProgress();

                userBiz.login(username, password, new CommonCallback<User>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        Toasts.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        Toasts.showToast(getString(R.string.login_success));
                        //保存用户信息
                        UserInfoHolder.getInstance().setUser(response);
                        toOrderActivity();
                    }
                });
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegisterActivity();
            }
        });
    }

    private void toRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void toOrderActivity() {
        startActivity(new Intent(this, OrderActivity.class));
        finish();
    }

    private void initView() {
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        btLogin = findViewById(R.id.bt_login);
        tvRegister = findViewById(R.id.tv_register);
    }
}
