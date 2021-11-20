package com.zf.plugins.param.init.holder.binding;

import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.param.init.ParamInitViewModel;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.holder.ParamBindingHolder;

import javax.lang.model.element.Element;

public abstract class ViewModelParamBindingHolder extends ParamBindingHolder {

    public ViewModelParamBindingHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    public String getExpectFiledKey() {
        ParamInitViewModel paramKey = getAnnotation(ParamInitViewModel.class);
        if (paramKey != null) {
            String value = paramKey.key();
            if (value.length() > 0) {
                return value;
            }
        }
        return null;
    }

    public String getExpectMethodName() {
        ParamInitViewModel paramKey = getAnnotation(ParamInitViewModel.class);
        if (paramKey != null) {
            String method = paramKey.method();
            if (method.length() > 0) {
                return method;
            }
        }
        return null;
    }


    public String getParamFiledValue() {
        String expectFiledKey = getExpectFiledKey();
        if (expectFiledKey != null && expectFiledKey.trim().length() > 0) {
            return expectFiledKey;
        }
        return FILED_VALUE_PREFIX + getOriginFiledName();
    }

    public String getParamFiledName() {
        return FILED_NAME_PREFIX + getOriginFiledName().toUpperCase() + FILED_NAME_SUFFIX;
    }

    public String getPutMethodName() {
        String expectFiledKey = getExpectMethodName();
        if (expectFiledKey != null && expectFiledKey.trim().length() > 0) {
            return PUT_METHOD_NAME_SUFFIX + Utils.capitalize(expectFiledKey);
        }
        return PUT_METHOD_NAME_SUFFIX + Utils.capitalize(getOriginFiledName());
    }

    public String getGetMethodName() {
        String expectFiledKey = getExpectMethodName();
        if (expectFiledKey == null || expectFiledKey.trim().length() == 0) {
            expectFiledKey = getOriginFiledName();
        }
        return GET_METHOD_NAME_SUFFIX + Utils.capitalize(expectFiledKey);
    }


    public String getContainKeyMethodName() {
        String expectFiledKey = getExpectMethodName();
        if (expectFiledKey != null && expectFiledKey.trim().length() > 0) {
            expectFiledKey = getOriginFiledName();
        }
        return CONTAIN_KEY_METHOD_NAME_PREFIX + Utils.capitalize(expectFiledKey);
    }


    public String getHasExtraMethodName() {
        String expectFiledKey = getExpectMethodName();
        if (expectFiledKey == null || expectFiledKey.trim().length() == 0) {
            expectFiledKey = getOriginFiledName();
        }
        return HAS_EXTRA_METHOD_NAME_SUFFIX + Utils.capitalize(expectFiledKey);
    }
}
