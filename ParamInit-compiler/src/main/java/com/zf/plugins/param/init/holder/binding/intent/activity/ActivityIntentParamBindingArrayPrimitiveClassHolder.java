package com.zf.plugins.param.init.holder.binding.intent.activity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.plugins.param.init.holder.action.ActivityCreationHolder;
import com.zf.plugins.param.init.holder.binding.intent.ActivityIntentParamBindingHolder;

import javax.lang.model.element.Element;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;

public class ActivityIntentParamBindingArrayPrimitiveClassHolder extends ActivityIntentParamBindingHolder {

    private ActivityIntentParamBindingArrayPrimitiveClassHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        methodSpec.beginControlFlow("if ( $N != null )", getOriginFiledName());
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                builder.addStatement("intent.putExtra($N,$N)", getParamFiledName(), getOriginFiledName());
                return false;
            }
        });
        methodSpec.endControlFlow();
        return true;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {
        ArrayType typeMirror = (ArrayType) getElement().asType();
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(ClassName.get(typeMirror));
        TypeKind kind = typeMirror.getComponentType().getKind();

        methodSpec.addComment("Handling $S types.", typeMirror);

        if (kind == TypeKind.BOOLEAN) {
            methodSpec.addStatement("return intent.getBooleanArrayExtra($N)",
                    getParamFiledName());
        } else if (kind == TypeKind.BYTE) {
            methodSpec.addStatement("return intent.getByteArrayExtra($N)",
                    getParamFiledName());
        } else if (kind == TypeKind.SHORT) {
            methodSpec.addStatement("return intent.getShortArrayExtra($N)",
                    getParamFiledName());

        } else if (kind == TypeKind.INT) {
            methodSpec.addStatement("return intent.getIntArrayExtra($N)",
                    getParamFiledName());

        } else if (kind == TypeKind.LONG) {
            methodSpec.addStatement("return intent.getLongArrayExtra($N)",
                    getParamFiledName());

        } else if (kind == TypeKind.CHAR) {
            methodSpec.addStatement("return intent.getCharArrayExtra($N)",
                    getParamFiledName());

        } else if (kind == TypeKind.FLOAT) {
            methodSpec.addStatement("return intent.getFloatArrayExtra($N)",
                    getParamFiledName());

        } else if (kind == TypeKind.DOUBLE) {
            methodSpec.addStatement("return intent.getDoubleArrayExtra($N)",
                    getParamFiledName());
        } else {
            getMessager().printMessage(Diagnostic.Kind.ERROR,
                   "Cannot handle type",
                    getElement());
            return false;
        }
        return true;
    }

    public static class CreationHolder extends ActivityCreationHolder<ActivityIntentParamBindingArrayPrimitiveClassHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ActivityIntentParamBindingArrayPrimitiveClassHolder getHolder() {
            return new ActivityIntentParamBindingArrayPrimitiveClassHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
