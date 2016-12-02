package com.gank.io.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by zouyingjie on 16/11/12.
 */

public class BaseActivity extends AppCompatActivity {
    public void showToastTip(String tip) {
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }
}
