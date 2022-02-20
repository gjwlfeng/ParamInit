package com.zf.version.log.manager

import android.os.Bundle
import com.zf.param.init.ParamInitActivity
import com.zf.param.init.ParamInitClass

@ParamInitClass
public class KtMainActivity : KtBaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt_main)
    }
}