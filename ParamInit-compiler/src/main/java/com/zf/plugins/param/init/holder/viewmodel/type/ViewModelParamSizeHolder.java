package com.zf.plugins.param.init.holder.viewmodel.type;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;
import com.zf.plugins.param.init.holder.viewmodel.ViewModelParamHolder;

import javax.lang.model.element.Element;

public class ViewModelParamSizeHolder extends ViewModelParamHolder {

    private ViewModelParamSizeHolder(AnnotationEnv annotationEnv, Element element , boolean isSupportV4, boolean isAndroidX) {
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
       return super.onGetLiveDataMethod(methodSpec);
    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelParamSizeHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX ) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public ViewModelParamSizeHolder getHolder() {
            return new ViewModelParamSizeHolder(this.annotationEnv, this.element,isSupportV4,isAndroidX);
        }
    }
}
