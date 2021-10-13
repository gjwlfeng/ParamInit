package com.zf.plugins.param.init.holder.fragment;

import com.zf.plugins.param.init.AnnotationEnv;

import javax.lang.model.element.Element;

public abstract class FragmentParamSparseArrayHolder extends FragmentParamHolder {

    public FragmentParamSparseArrayHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }


}