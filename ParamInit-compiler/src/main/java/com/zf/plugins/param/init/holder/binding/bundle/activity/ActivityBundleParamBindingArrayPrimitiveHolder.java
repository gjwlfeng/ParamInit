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
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;

public class ActivityBundleParamBindingArrayPrimitiveHolder extends ActivityBundleParamBindingHolder {

    private ActivityBundleParamBindingArrayPrimitiveHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        methodSpec.beginControlFlow("if ( $N != null )", getOriginFiledName());
        boolean result = MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                ArrayType typeMirror = (ArrayType) getElement().asType();
                TypeKind kind = typeMirror.getComponentType().getKind();
                builder.addComment("Handling $S types.", typeMirror);
                if (kind == TypeKind.BOOLEAN) {
                    builder.addStatement("bundle.putBooleanArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (kind == TypeKind.BYTE) {
                    builder.addStatement("bundle.putByteArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (kind == TypeKind.SHORT) {
                    builder.addStatement("bundle.putShortArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (kind == TypeKind.INT) {
                    builder.addStatement("bundle.putIntArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (kind == TypeKind.LONG) {
                    builder.addStatement("bundle.putLongArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (kind == TypeKind.CHAR) {
                    builder.addStatement("bundle.putCharArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (kind == TypeKind.FLOAT) {
                    builder.addStatement("bundle.putFloatArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (kind == TypeKind.DOUBLE) {
                    builder.addStatement("bundle.putDoubleArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else {
                    getMessager().printMessage(Diagnostic.Kind.ERROR,
                            String.format("Cannot handle type %1$s[].", kind.name()),
                            element);
                    return false;
                }
                return true;
            }
        });
        methodSpec.endControlFlow();
        return result;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {

        ArrayType typeMirror = (ArrayType) getElement().asType();
        TypeName typeName = ClassName.get(typeMirror);
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(typeName);
        TypeKind kind = typeMirror.getComponentType().getKind();

        methodSpec.addComment("Handling $S types.", typeMirror);
        if (kind == TypeKind.BOOLEAN) {
            methodSpec.addStatement("return bundle.getBooleanArray($N)", getParamFiledName());
        } else if (kind == TypeKind.BYTE) {
            methodSpec.addStatement("return bundle.getByteArray($N)", getParamFiledName());
        } else if (kind == TypeKind.SHORT) {
            methodSpec.addStatement("return bundle.getShortArray($N)", getParamFiledName());
        } else if (kind == TypeKind.INT) {
            methodSpec.addStatement("return bundle.getIntArray($N)", getParamFiledName());
        } else if (kind == TypeKind.LONG) {
            methodSpec.addStatement("return bundle.getLongArray($N)", getParamFiledName());
        } else if (kind == TypeKind.CHAR) {
            methodSpec.addStatement("return bundle.getCharArray($N)", getParamFiledName());
        } else if (kind == TypeKind.FLOAT) {
            methodSpec.addStatement("return bundle.getFloatArray($N)", getParamFiledName());
        } else if (kind == TypeKind.DOUBLE) {
            methodSpec.addStatement("return bundle.getDoubleArray($N)", getParamFiledName());
        } else {
            getMessager().printMessage(Diagnostic.Kind.ERROR,
                    String.format("Cannot handle type %1$s[].", kind.name()),
                    element);
            return false;
        }

        return true;
    }

    public static class CreationHolder extends ActivityCreationHolder<ActivityBundleParamBindingArrayPrimitiveHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ActivityBundleParamBindingArrayPrimitiveHolder getHolder() {
            return new ActivityBundleParamBindingArrayPrimitiveHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
