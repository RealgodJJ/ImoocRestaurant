package mybaidu.admin.example.com.imoocrestaurant.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mybaidu.admin.example.com.imoocrestaurant.R;
import mybaidu.admin.example.com.imoocrestaurant.bean.User;
import mybaidu.admin.example.com.imoocrestaurant.biz.UserBiz;
import mybaidu.admin.example.com.imoocrestaurant.net.CommonCallback;
import mybaidu.admin.example.com.imoocrestaurant.utils.Toasts;

public class RegisterActivity extends BaseActivity {
    private EditText etPhoneNumber;
    private EditText etPassword;
    private EditText etRePassword;
    private Button btRegister;
    private UserBiz userBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setUpToolbar();
        setTitle(R.string.register);
        initView();
        initListener();

        userBiz = new UserBiz();
    }

    private void initView() {
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etPassword = findViewById(R.id.et_password);
        etRePassword = findViewById(R.id.et_re_password);
        btRegister = findViewById(R.id.bt_register);
    }

    private void initListener() {
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etPhoneNumber.getText().toString();
                String password = etPassword.getText().toString();
                String rePassword = etRePassword.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toasts.showToast(getString(R.string.can_not_empty));
                    return;
                }

                if (!password.equals(rePassword)) {
                    Toasts.showToast(getString(R.string.password_not_same));
                    return;
                }

                startLoadingProgress();

                userBiz.register(username, password, new CommonCallback<User>() {
                    @Override
                    public void onError(Exception e) {
                        Toasts.showToast(e.getMessage());
                        stopLoadingProgress();
                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        Toasts.showToast(getString(R.string.register_success) +
                                "用户名为：" + response.getUsername());
                        LoginActivity.launch(RegisterActivity.this,
                                response.getUsername(), response.getPassword());
                        finish();
                    }
                });
            }
        });
    }
}
