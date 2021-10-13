package com.zf.plugins.param.init.holder.binding.intent;

import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.param.init.ParamInitActivity;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.holder.binding.IntentParamBindingHolder;

import javax.lang.model.element.Element;

public abstract class ActivityIntentParamBindingHolder extends IntentParamBindingHolder {

    public ActivityIntentParamBindingHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    public String getExpectFiledKey() {
        ParamInitActivity paramKey = getAnnotation(ParamInitActivity.class);
        if (paramKey != null) {
            String value = paramKey.key();
            if (value.length() > 0) {
                return value;
            }
        }
        return null;
    }

    public String getExpectMethodName() {
        ParamInitActivity paramKey = getAnnotation(ParamInitActivity.class);
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
        return FILED_NAME_PREFIX+getOriginFiledName().toUpperCase() + FILED_NAME_SUFFIX;
    }

    public String getPutMethodName() {
        String expectMethodName = getExpectMethodName();
        if (expectMethodName != null && expectMethodName.trim().length() > 0) {
            return PUT_METHOD_NAME_SUFFIX + expectMethodName;
        }
        return PUT_METHOD_NAME_SUFFIX + Utils.capitalize(getOriginFiledName());
    }

    public String getGetMethodName() {
        String expectMethodName = getExpectMethodName();
        if (expectMethodName != null && expectMethodName.trim().length() > 0) {
            return GET_METHOD_NAME_SUFFIX + Utils.capitalize(expectMethodName);
        }
        return GET_METHOD_NAME_SUFFIX + Utils.capitalize(getOriginFiledName());
    }


    public String getHasExtraMethodName() {
        String expectMethodName = getExpectMethodName();
        if (expectMethodName != null && expectMethodName.trim().length() > 0) {
            return HAS_EXTRA_METHOD_NAME_SUFFIX + Utils.capitalize(expectMethodName) + GET_EXTRA_METHOD_NAME_SUFFIX;
        }
        return HAS_EXTRA_METHOD_NAME_SUFFIX + Utils.capitalize(getOriginFiledName()) + GET_EXTRA_METHOD_NAME_SUFFIX;
    }
}
