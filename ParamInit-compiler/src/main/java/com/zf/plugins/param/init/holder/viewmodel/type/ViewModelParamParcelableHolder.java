package com.zf.plugins.param.init.holder.viewmodel.type;

import com.zf.plugins.param.init.AnnotationEnv;


import com.zf.plugins.param.init.holder.viewmodel.ViewModelParamHolder;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;


import javax.lang.model.element.Element;

public class ViewModelParamParcelableHolder extends ViewModelParamHolder {


    public ViewModelParamParcelableHolder(AnnotationEnv annotationEnv, Element element ,boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }


    public static class CreationHolder extends ViewModelCreationHolder<ViewModelParamParcelableHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public ViewModelParamParcelableHolder getHolder() {
            return new ViewModelParamParcelableHolder(this.annotationEnv, this.element,isSupportV4,isAndroidX);
        }
    }
}
