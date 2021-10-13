package com.zf.plugins.param.init.holder.binding.bundle.activity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.plugins.param.init.holder.action.ActivityCreationHolder;
import com.zf.plugins.param.init.holder.binding.bundle.ActivityBundleParamBindingHolder;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

public class ActivityBundleParamBindingSerializableHolder extends ActivityBundleParamBindingHolder {

    private ActivityBundleParamBindingSerializableHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        methodSpec.beginControlFlow("if ( $N != null )", getOriginFiledName());
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                return false;
            }
        });
        methodSpec.endControlFlow();
        return true;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {
        TypeMirror typeMirror = getElement().asType();
        TypeName typeName = ClassName.get(typeMirror);
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(typeName);
        methodSpec.addStatement("$T obj = bundle.getSerializable($N)", ClassName.OBJECT, getParamFiledName());
        methodSpec.beginControlFlow("if (obj != null)");
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                methodSpec.addStatement("return ($T)obj", typeName);
                return true;
            }
        });
        methodSpec.nextControlFlow("else");
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                methodSpec.addStatement("return null");
                return true;
            }
        });
        methodSpec.endControlFlow();
        return true;
    }

    public static class CreationHolder extends ActivityCreationHolder<ActivityBundleParamBindingSerializableHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ActivityBundleParamBindingSerializableHolder getHolder() {

//            TypeMirror typeMirror = element.asType();
//            DeclaredType declaredType= (DeclaredType) typeMirror;
//            Name simpleName = declaredType.asElement().getSimpleName();

            return new ActivityBundleParamBindingSerializableHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
