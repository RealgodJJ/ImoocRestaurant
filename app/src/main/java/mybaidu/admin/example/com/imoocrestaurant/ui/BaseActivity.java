package mybaidu.admin.example.com.imoocrestaurant.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mybaidu.admin.example.com.imoocrestaurant.R;

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
}
