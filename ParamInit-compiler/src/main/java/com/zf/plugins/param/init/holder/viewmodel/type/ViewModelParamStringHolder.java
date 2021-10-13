package com.zf.plugins.param.init.holder.viewmodel.type;

import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;
import com.zf.plugins.param.init.holder.viewmodel.ViewModelParamHolder;

import javax.lang.model.element.Element;

public class ViewModelParamStringHolder extends ViewModelParamHolder {

    public ViewModelParamStringHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX ) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelParamStringHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX ) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public ViewModelParamStringHolder getHolder() {

            return new ViewModelParamStringHolder(this.annotationEnv, this.element,isSupportV4,isAndroidX);
        }
    }
}
