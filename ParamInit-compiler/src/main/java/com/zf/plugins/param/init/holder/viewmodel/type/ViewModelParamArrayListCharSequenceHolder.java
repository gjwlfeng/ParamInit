package com.zf.plugins.param.init.holder.viewmodel.type;

import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.holder.viewmodel.ViewModelParamHolder;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;

import javax.lang.model.element.Element;

public class ViewModelParamArrayListCharSequenceHolder extends ViewModelParamHolder {

    private ViewModelParamArrayListCharSequenceHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelParamArrayListCharSequenceHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public ViewModelParamArrayListCharSequenceHolder getHolder() {
            return new ViewModelParamArrayListCharSequenceHolder(this.annotationEnv, this.element,isSupportV4,isAndroidX);
        }
    }
}
