package com.zf.plugins.param.init.holder.viewmodel.type;

import com.zf.plugins.param.init.AnnotationEnv;


import com.zf.plugins.param.init.holder.viewmodel.ViewModelParamHolder;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;


import javax.lang.model.element.Element;

public class ViewModelParamSerializableHolder extends ViewModelParamHolder {

    private ViewModelParamSerializableHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelParamSerializableHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element ,boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public ViewModelParamSerializableHolder getHolder() {
            return new ViewModelParamSerializableHolder(this.annotationEnv, this.element,isSupportV4,isAndroidX);
        }
    }
}
