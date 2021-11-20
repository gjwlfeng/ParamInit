package com.zf.plugins.param.init.holder.binding.intent;

import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.param.init.ParamInitViewModel;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.holder.binding.ViewModelParamBindingHolder;

import javax.lang.model.element.Element;

public abstract class ViewModelIntentParamBindingHolder extends ViewModelParamBindingHolder {

    public ViewModelIntentParamBindingHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }

    public boolean onHasExtra(MethodSpec.Builder methodSpec) {
        methodSpec.addStatement("return intent.hasExtra($N)", getParamFiledName());
        return true;
    }

    public String getHasExtraMethodName() {
        ParamInitViewModel paramMethod = getAnnotation(ParamInitViewModel.class);
        String methodName= getOriginFiledName();
        if (paramMethod != null) {
            String value = paramMethod.method();
            if (value.length() > 0) {
                methodName= value;
            }
        }
        return HAS_EXTRA_METHOD_NAME_SUFFIX + Utils.capitalize(methodName);
    }
}
