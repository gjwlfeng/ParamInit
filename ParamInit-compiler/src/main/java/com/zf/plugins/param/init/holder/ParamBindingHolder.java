package com.zf.plugins.param.init.holder;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

public abstract class ParamBindingHolder extends ParamHolder {

    public final static String HAS_EXTRA_METHOD_NAME_SUFFIX = "has";
//    public final static String GET_EXTRA_METHOD_NAME_SUFFIX = "Extra";
    public final static String CONTAIN_KEY_METHOD_NAME_PREFIX = "contains";
//    public final static String CONTAIN_KEY_METHOD_NAME_SUFFIX = "Extra";

    public ParamBindingHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    public void onField(List<FieldSpec> fieldSpecList) {
        FieldSpec fieldSpec = FieldSpec.builder(ClassNameConstant.INTENT, "intent", Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .build();
        fieldSpecList.add(fieldSpec);
    }


    public abstract boolean onSetValue(MethodSpec.Builder methodSpec);

    public abstract boolean onGetValue(MethodSpec.Builder methodSpec);

    public abstract String getPutMethodName();

    public abstract String getGetMethodName();


}
