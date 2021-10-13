package com.zf.plugins.param.init.holder.binding;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.holder.ParamBindingHolder;

import javax.lang.model.element.Element;

public abstract class IntentParamBindingHolder extends ParamBindingHolder {


    public IntentParamBindingHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }

    public boolean onHasExtra(MethodSpec.Builder methodSpec) {
        methodSpec.returns(ClassName.BOOLEAN);
        methodSpec.addStatement("return intent.hasExtra($N)", getParamFiledName());
        return true;
    }

}
