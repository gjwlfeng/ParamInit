package com.zf.plugins.param.init.holder.viewmodel.type;

import com.zf.plugins.param.init.AnnotationEnv;


import com.zf.plugins.param.init.holder.viewmodel.ViewModelParamArrayHolder;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;


import javax.lang.model.element.Element;

public class ViewModelParamArrayCharSequenceHolder extends ViewModelParamArrayHolder {

    public ViewModelParamArrayCharSequenceHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }


    public static class CreationHolder extends ViewModelCreationHolder<ViewModelParamArrayCharSequenceHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public ViewModelParamArrayCharSequenceHolder getHolder() {


            return new ViewModelParamArrayCharSequenceHolder(this.annotationEnv, this.element,isSupportV4,isAndroidX);
        }
    }
}
