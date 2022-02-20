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

    public String getExpectKey() {
        ParamInitViewModel paramKey = getAnnotation(ParamInitViewModel.class);
        if (paramKey != null) {
            String value = paramKey.key();
            if (value.length() > 0) {
                return value;
            }
        }
        return null;
    }

    public String getExpectValue() {
        ParamInitViewModel paramKey = getAnnotation(ParamInitViewModel.class);
        if (paramKey != null) {
            String method = paramKey.value();
            if (method.length() > 0) {
                return method;
            }
        }
        return null;
    }



    public String getPutMethodName() {
        String expectFiledKey = getExpectValue();
        if (expectFiledKey != null && expectFiledKey.trim().length() > 0) {
            return PUT_METHOD_NAME_SUFFIX + Utils.capitalize(expectFiledKey);
        }
        return PUT_METHOD_NAME_SUFFIX + Utils.capitalize(getOriginFiledName());
    }

    public String getGetMethodName() {
        String expectFiledKey = getExpectValue();
        if (expectFiledKey == null || expectFiledKey.trim().length() == 0) {
            expectFiledKey = getOriginFiledName();
        }
        return GET_METHOD_NAME_SUFFIX + Utils.capitalize(expectFiledKey);
    }


    public String getContainKeyMethodName() {
        String expectFiledKey = getExpectValue();
        if (expectFiledKey != null && expectFiledKey.trim().length() > 0) {
            expectFiledKey = getOriginFiledName();
        }
        return CONTAIN_KEY_METHOD_NAME_PREFIX + Utils.capitalize(expectFiledKey);
    }


    public String getHasExtraMethodName() {
        String expectFiledKey = getExpectValue();
        if (expectFiledKey == null || expectFiledKey.trim().length() == 0) {
            expectFiledKey = getOriginFiledName();
        }
        return HAS_EXTRA_METHOD_NAME_SUFFIX + Utils.capitalize(expectFiledKey);
    }
}
