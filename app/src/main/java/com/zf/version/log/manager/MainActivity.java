package com.zf.version.log.manager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.zf.param.init.ParamInitActivity;
import com.zf.param.init.ParamInitClass;

@ParamInitClass
public class MainActivity extends BaseActivity {
    @ParamInitActivity(key="age",value = "zengfeng")
    public String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}