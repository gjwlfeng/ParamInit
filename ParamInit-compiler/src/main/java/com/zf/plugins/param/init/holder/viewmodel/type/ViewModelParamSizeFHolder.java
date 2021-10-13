package com.zf.plugins.param.init.holder.viewmodel.type;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;
import com.zf.plugins.param.init.holder.viewmodel.ViewModelParamHolder;

import javax.lang.model.element.Element;

public class ViewModelParamSizeFHolder extends ViewModelParamHolder {

    private ViewModelParamSizeFHolder(AnnotationEnv annotationEnv, Element element , boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }

//    @Override
//    public boolean onGetLiveDataMethodWithInit(MethodSpec.Builder methodSpec) {
//        methodSpec.addAnnotation(AnnotationSpec.builder(ClassNameConstant.getRequiresApiSupportAnnotation(isAndroidX()))
//                .addMember("api", "$T.VERSION_CODES.LOLLIPOP", ClassNameConstant.BUILD).build());
//        return super.onGetLiveDataMethodWithInit(methodSpec);
//    }

    @Override
    public boolean onGetMethod(MethodSpec.Builder methodSpec) {
        methodSpec.addAnnotation(AnnotationSpec.builder(ClassNameConstant.getRequiresApiSupportAnnotation(isAndroidX()))
                .addMember("api", "$T.VERSION_CODES.LOLLIPOP", ClassNameConstant.BUILD).build());
       return super.onGetMethod(methodSpec);
    }

//    @Override
//    public boolean onGetMethodWithInit(MethodSpec.Builder methodSpec) {
//        methodSpec.addAnnotation(AnnotationSpec.builder(ClassNameConstant.getRequiresApiSupportAnnotation(isAndroidX()))
//                .addMember("api", "$T.VERSION_CODES.LOLLIPOP", ClassNameConstant.BUILD).build());
//       return super.onGetMethodWithInit(methodSpec);
//    }

    @Override
    public boolean onGetLiveDataMethod(MethodSpec.Builder methodSpec) {
        methodSpec.addAnnotation(AnnotationSpec.builder(ClassNameConstant.getRequiresApiSupportAnnotation(isAndroidX()))
                .addMember("api", "$T.VERSION_CODES.LOLLIPOP", ClassNameConstant.BUILD).build());
        return  super.onGetLiveDataMethod(methodSpec);
    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelParamSizeFHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX ) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public ViewModelParamSizeFHolder getHolder() {
            return new ViewModelParamSizeFHolder(this.annotationEnv, this.element,isSupportV4,isAndroidX);
        }
    }
}
