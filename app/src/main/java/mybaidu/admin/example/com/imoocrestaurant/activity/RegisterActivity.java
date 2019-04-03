package mybaidu.admin.example.com.imoocrestaurant.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mybaidu.admin.example.com.imoocrestaurant.R;

public class RegisterActivity extends BaseActivity {
    private EditText etPhoneNumber;
    private EditText etPassword;
    private EditText etRePassword;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setUpToolbar();
        initView();
        initListener();

        setTitle(R.string.register);
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

            }
        });
    }
}
