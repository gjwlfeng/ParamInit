package com.zf.version.log.manager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zf.param.init.ParamInitActivity;
import com.zf.param.init.ParamInitClass;

@ParamInitClass
public class BaseActivity extends AppCompatActivity {
    @ParamInitActivity
    public String name;
}