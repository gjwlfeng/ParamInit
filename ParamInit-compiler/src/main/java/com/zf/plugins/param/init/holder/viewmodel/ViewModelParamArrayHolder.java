package com.zf.plugins.param.init.holder.viewmodel;

import com.zf.plugins.param.init.AnnotationEnv;
import javax.lang.model.element.Element;

public abstract class ViewModelParamArrayHolder extends ViewModelParamHolder {

    public ViewModelParamArrayHolder(AnnotationEnv annotationEnv, Element element ,boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }
}

