package com.zf.plugins.param.init.holder;

import com.zf.plugins.param.init.AnnotationEnv;

import java.lang.annotation.Annotation;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class ParamHolder {

    public final static String FILED_NAME_SUFFIX = "_KEY";
    public final static String PUT_METHOD_NAME_SUFFIX = "set";
    public final static String GET_METHOD_NAME_SUFFIX = "get";
    public final static String FILED_NAME_PREFIX = "PARAM_INIT_";
    public final static String FILED_VALUE_PREFIX = "param_init_";
    private final AnnotationEnv annotationEnv;
    protected Element element;
    private final boolean isSupportV4;
    private final boolean isAndroidX;

    public ParamHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        this.annotationEnv = annotationEnv;
        this.element = element;
        this.isAndroidX = isAndroidX;
        this.isSupportV4 = isSupportV4;
    }

    public boolean isSupportV4() {
        return isSupportV4;
    }

    public boolean isAndroidX() {
        return isAndroidX;
    }

    public Element getElement() {
        return element;
    }

    public AnnotationEnv getAnnotationEnv() {
        return annotationEnv;
    }

    public Elements getElementUtils() {
        return annotationEnv.mElementUtils;
    }

    public Types getTypeUtils() {
        return annotationEnv.mTypeUtils;
    }

    public Messager getMessager() {
        return annotationEnv.mMessager;
    }

    public Filer getFiler() {
        return annotationEnv.mFiler;
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return element.getAnnotation(annotationType);
    }

    public String getOriginFiledName() {
        return getElement().toString();
    }

    public abstract  String getParamFiledName();

    public abstract String getParamFiledValue();

    public abstract String getExpectFiledKey();

    public abstract String getExpectMethodName();

}
