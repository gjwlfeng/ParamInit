package com.zf.plugins.param.init.holder.fragment;

import com.zf.plugins.param.init.AnnotationEnv;

import javax.lang.model.element.Element;

public abstract class FragmentParamBinderHolder extends FragmentParamHolder {

    public FragmentParamBinderHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }
}