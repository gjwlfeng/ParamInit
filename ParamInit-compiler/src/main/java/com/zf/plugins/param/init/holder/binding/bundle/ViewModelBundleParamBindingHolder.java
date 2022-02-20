package com.zf.plugins.param.init.holder.binding.bundle;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.param.init.ParamInitViewModel;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.holder.binding.ViewModelParamBindingHolder;

import javax.lang.model.element.Element;

public abstract class ViewModelBundleParamBindingHolder extends ViewModelParamBindingHolder {

    public ViewModelBundleParamBindingHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    public boolean onContainKey(MethodSpec.Builder methodSpec) {
        methodSpec.returns(ClassName.BOOLEAN);
        methodSpec.addStatement("return bundle.containsKey($N)", getParamFiledName());
        return true;
    }

    public String getContainKeyMethodName() {
        ParamInitViewModel paramMethod = getAnnotation(ParamInitViewModel.class);
        String methodName= getOriginFiledName();
        if (paramMethod != null) {
            String value = paramMethod.value();
            if (value.length() > 0) {
                methodName= value;
            }
        }
        return CONTAIN_KEY_METHOD_NAME_PREFIX + Utils.capitalize(methodName) ;
    }


}
