package com.gank.io.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by zouyingjie on 16/11/12.
 */

public class BaseActivity extends AppCompatActivity {
    public void showNetToastTip(){
        Toast.makeText(this, "加载数据失败,请重试", Toast.LENGTH_SHORT).show();
    }
}
