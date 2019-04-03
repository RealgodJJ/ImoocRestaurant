package mybaidu.admin.example.com.imoocrestaurant.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mybaidu.admin.example.com.imoocrestaurant.R;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void setUpToolbar() {
        Toolbar tbCommon = findViewById(R.id.tb_common);
        setSupportActionBar(tbCommon);

        tbCommon.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
