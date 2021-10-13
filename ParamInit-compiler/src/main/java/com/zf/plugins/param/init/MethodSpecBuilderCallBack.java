package com.zf.plugins.param.init;

import com.squareup.javapoet.MethodSpec;

public interface MethodSpecBuilderCallBack {
    boolean innerBlock(MethodSpec.Builder builder);
}