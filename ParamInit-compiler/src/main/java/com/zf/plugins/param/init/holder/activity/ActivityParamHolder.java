package com.zf.plugins.param.init.holder.activity;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.param.init.ParamInitActivity;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.holder.ParamHolder;

import java.util.List;

import javax.lang.model.element.Element;

public abstract class ActivityParamHolder extends ParamHolder {

    public ActivityParamHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    public abstract void onField(List<FieldSpec> fieldSpecList);

    public abstract void onInitMethodWithIntent(MethodSpec.Builder methodSpec, String globalVarName);

    public abstract void onInitMethodWithBundle(MethodSpec.Builder methodSpec, String globalVarName);

    public abstract void onSaveStateMethod(MethodSpec.Builder methodSpec);

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

}

