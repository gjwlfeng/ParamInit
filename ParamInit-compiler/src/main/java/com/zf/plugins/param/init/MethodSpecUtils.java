package com.zf.plugins.param.init;

import com.squareup.javapoet.MethodSpec;

public class MethodSpecUtils {

    public static boolean codeBlock(MethodSpec.Builder builder, MethodSpecBuilderCallBack methodSpecBuilderCallBack) {
        return methodSpecBuilderCallBack.innerBlock(builder);
    }

    public static boolean codeBlock(CallBack callBack) {
        return callBack.innerBlock();
    }
}


