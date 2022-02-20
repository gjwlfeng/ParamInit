package com.zf.plugins.param.init.holder.binding.bundle;

import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.param.init.ParamInitActivity;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.holder.binding.BundleParamBindingHolder;

import javax.lang.model.element.Element;

public abstract class ActivityBundleParamBindingHolder extends BundleParamBindingHolder {


    public ActivityBundleParamBindingHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    public String getExpectKey() {
        ParamInitActivity paramKey = getAnnotation(ParamInitActivity.class);
        if (paramKey != null) {
            String value = paramKey.key();
            if (value.length() > 0) {
                return value;
            }
        }
        return null;
    }

    public String getExpectValue() {
        ParamInitActivity paramKey = getAnnotation(ParamInitActivity.class);
        if (paramKey != null) {
            String method = paramKey.value();
            if (method.length() > 0) {
                return method;
            }
        }
        return null;
    }


    public String getPutMethodName() {
        String expectMethodName = getExpectValue();
        if (expectMethodName == null || expectMethodName.trim().length() == 0) {
            expectMethodName = getOriginFiledName();
        }
        return PUT_METHOD_NAME_SUFFIX + Utils.capitalize(expectMethodName);
    }

    public String getGetMethodName() {
        String expectMethodName = getExpectValue();
        if (expectMethodName == null || expectMethodName.trim().length() == 0) {
            expectMethodName = getOriginFiledName();
        }
        return GET_METHOD_NAME_SUFFIX + Utils.capitalize(expectMethodName);
    }

    public String getContainKeyMethodName() {
        String expectMethodName = getExpectValue();
        if (expectMethodName == null || expectMethodName.trim().length() == 0) {
            expectMethodName = getOriginFiledName();
        }
        return CONTAIN_KEY_METHOD_NAME_PREFIX + Utils.capitalize(expectMethodName);
    }
}
