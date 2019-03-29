package mybaidu.admin.example.com.imoocrestaurant;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {
    private Button btSkip;
    private Handler handler = new Handler();

    private Runnable runnableToLogin = new Runnable() {
        @Override
        public void run() {
            toLoginActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btSkip = findViewById(R.id.bt_skip);
        initEvent();
        handler.postDelayed(runnableToLogin, 3000);
    }

    private void initEvent() {
        btSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //避免在点击事件执行之后，还进行handler的处理
                handler.removeCallbacks(runnableToLogin);
                toLoginActivity();
            }
        });
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableToLogin);
    }
}
