package com.zf.plugins.param.init.holder.binding.bundle.fragment;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.plugins.param.init.holder.action.FragmentCreationHolder;
import com.zf.plugins.param.init.holder.binding.bundle.FragmentBundleParamBindingHolder;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * 包装类
 */
public class BundleParamBindingSizeHolder extends FragmentBundleParamBindingHolder {

    private BundleParamBindingSizeHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        methodSpec.addAnnotation(AnnotationSpec.builder(ClassNameConstant.getRequiresApiSupportAnnotation(isAndroidX()))
                .addMember("api", "$T.VERSION_CODES.LOLLIPOP", ClassNameConstant.BUILD).build());

        methodSpec.beginControlFlow("if ( $N != null )", getOriginFiledName());
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                builder.addStatement("bundle.putSize($N,$N)", getParamFiledName(), getOriginFiledName());
                return false;
            }
        });
        methodSpec.endControlFlow();
        return true;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {

        methodSpec.addAnnotation(AnnotationSpec.builder(ClassNameConstant.getRequiresApiSupportAnnotation(isAndroidX()))
                .addMember("api", "$T.VERSION_CODES.LOLLIPOP", ClassNameConstant.BUILD).build());

        TypeMirror typeMirror = getElement().asType();
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.addStatement("return bundle.getSize($N)", getParamFiledName());
        methodSpec.returns(ClassName.get(typeMirror));
        return true;
    }

    public static class CreationHolder extends FragmentCreationHolder<BundleParamBindingSizeHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv,element,isSupportV4,isAndroidX);
        }

        public BundleParamBindingSizeHolder getHolder() {
            return new BundleParamBindingSizeHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
