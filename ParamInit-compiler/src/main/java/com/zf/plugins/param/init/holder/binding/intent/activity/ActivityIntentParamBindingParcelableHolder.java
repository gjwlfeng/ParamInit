package com.zf.plugins.param.init.holder.binding.intent.activity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.plugins.param.init.holder.action.ActivityCreationHolder;
import com.zf.plugins.param.init.holder.binding.intent.ActivityIntentParamBindingHolder;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

public class ActivityIntentParamBindingParcelableHolder extends ActivityIntentParamBindingHolder {

    private ActivityIntentParamBindingParcelableHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        methodSpec.beginControlFlow("if ( $N != null )", getOriginFiledName());
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                methodSpec.addStatement("intent.putExtra($N,$N)", getParamFiledName(), getOriginFiledName());
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
        methodSpec.returns(typeName);
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.addStatement("$T obj = intent.getParcelableExtra($N)", ClassName.OBJECT, getParamFiledName());
        methodSpec.beginControlFlow("if (obj != null)");
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                methodSpec.addStatement("return ($T)obj", typeName);
                return false;
            }
        });
        methodSpec.nextControlFlow("else");
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                methodSpec.addStatement("return null");
                return false;
            }
        });
        methodSpec.endControlFlow();
        return true;
    }

    public static class CreationHolder extends ActivityCreationHolder<ActivityIntentParamBindingParcelableHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element , boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4, isAndroidX);
        }

        public ActivityIntentParamBindingParcelableHolder getHolder() {
            return new ActivityIntentParamBindingParcelableHolder(this.annotationEnv, this.element,isSupportV4, isAndroidX);
        }
    }
}
