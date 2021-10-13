package com.zf.plugins.param.init.holder.viewmodel.type;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.holder.viewmodel.ViewModelParamHolder;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;


import java.util.List;

import javax.lang.model.element.Element;

public class ViewModelParamBinderHolder extends ViewModelParamHolder {

    private ViewModelParamBinderHolder(AnnotationEnv annotationEnv, Element element ,boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }


    @Override
    public boolean onGetLiveDataMethod(MethodSpec.Builder methodSpec) {
        return false;
    }

//    @Override
//    public boolean onGetMethodWithInit(MethodSpec.Builder methodSpec) {
//        return false;
//    }

//    @Override
//    public boolean onGetLiveDataMethodWithInit(MethodSpec.Builder methodSpec) {
//        return false;
//    }

    @Override
    public boolean onGetMethod(MethodSpec.Builder methodSpec) {
        return false;
    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelParamBinderHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX ) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public ViewModelParamBinderHolder getHolder() {
            return new ViewModelParamBinderHolder(this.annotationEnv, this.element,isSupportV4,isAndroidX);
        }
    }
}
