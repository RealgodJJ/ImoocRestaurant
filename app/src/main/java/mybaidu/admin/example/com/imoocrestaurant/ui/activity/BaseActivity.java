package mybaidu.admin.example.com.imoocrestaurant.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mybaidu.admin.example.com.imoocrestaurant.R;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    private ProgressDialog loadDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage(getString(R.string.loading));
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

    protected void stopLoadingProgress() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }

    protected void startLoadingProgress() {
        loadDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLoadingProgress();
        loadDialog = null;
    }

    protected void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
