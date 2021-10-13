package com.zf.plugins.param.init.holder.binding;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.holder.ParamBindingHolder;

import javax.lang.model.element.Element;

public abstract class BundleParamBindingHolder extends ParamBindingHolder {

    public BundleParamBindingHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }

    public boolean onContainKey(MethodSpec.Builder methodSpec) {
        methodSpec.returns(ClassName.BOOLEAN);
        methodSpec.addStatement("return bundle.containsKey($N)", getParamFiledName());
        return true;
    }

}
