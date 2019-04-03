package mybaidu.admin.example.com.imoocrestaurant.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mybaidu.admin.example.com.imoocrestaurant.R;

public class LoginActivity extends AppCompatActivity {
    private EditText etAccount;
    private EditText etPassword;
    private Button btLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOrderActivity();
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
    }

    private void initView() {
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        btLogin = findViewById(R.id.bt_login);
        tvRegister = findViewById(R.id.tv_register);
    }
}
