package com.zf.version.log.manager

import androidx.appcompat.app.AppCompatActivity
import com.zf.param.init.ParamInitActivity
import com.zf.param.init.ParamInitClass

@ParamInitClass
open class KtBaseActivity : AppCompatActivity() {
    @ParamInitActivity
    @JvmField
     var name: String? = null
}