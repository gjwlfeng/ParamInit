package com.zf.plugins.param.init.holder.binding.bundle;

import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.param.init.ParamInitFragment;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.holder.binding.BundleParamBindingHolder;

import javax.lang.model.element.Element;

public abstract class FragmentBundleParamBindingHolder extends BundleParamBindingHolder {

    public FragmentBundleParamBindingHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }


    public String getExpectFiledKey() {
        ParamInitFragment paramKey = getAnnotation(ParamInitFragment.class);
        if (paramKey != null) {
            String value = paramKey.key();
            if (value.length() > 0) {
                return value;
            }
        }
        return null;
    }

    public String getExpectMethodName() {
        ParamInitFragment paramKey = getAnnotation(ParamInitFragment.class);
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
        String expectMethodName = getExpectMethodName();
        if (expectMethodName == null || expectMethodName.trim().length() == 0) {
            expectMethodName = getOriginFiledName();
        }
        return PUT_METHOD_NAME_SUFFIX + Utils.capitalize(expectMethodName);
    }

    public String getGetMethodName() {
        String expectMethodName = getExpectMethodName();
        if (expectMethodName == null || expectMethodName.trim().length() == 0) {
            expectMethodName=getOriginFiledName();
        }
        return GET_METHOD_NAME_SUFFIX + Utils.capitalize(expectMethodName);
    }

    public String getContainKeyMethodName() {
        String expectMethodName = getExpectMethodName();
        if (expectMethodName == null || expectMethodName.trim().length() == 0) {
            expectMethodName=getOriginFiledName();
        }
        return CONTAIN_KEY_METHOD_NAME_PREFIX + Utils.capitalize(expectMethodName) ;
    }
}
