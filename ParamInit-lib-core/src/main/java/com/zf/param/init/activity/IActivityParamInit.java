package com.zf.param.init.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public interface IActivityParamInit {

    void init(Activity activity, Bundle savedInstanceState, Intent intent);

    void init(Activity activity, Bundle savedInstanceState);

    void init(Activity activity, Intent intent);

    void saveState(Activity activity, Bundle outState);
}
