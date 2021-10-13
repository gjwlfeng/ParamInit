package com.zf.plugins.param.init.holder.binding.bundle.viewmodel;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;
import com.zf.plugins.param.init.holder.binding.bundle.ViewModelBundleParamBindingHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class ViewModelBundleParamBindingArrayPrimitiveHolder extends ViewModelBundleParamBindingHolder {

    private ViewModelBundleParamBindingArrayPrimitiveHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        methodSpec.beginControlFlow("if ( $N != null )", getOriginFiledName());
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {

                DeclaredType objTypeMirror =(DeclaredType) getElement().asType();
                List<? extends TypeMirror> typeArguments = objTypeMirror.getTypeArguments();
                TypeMirror argTypeMirror = typeArguments.get(0);

                ArrayType arrayType = (ArrayType) argTypeMirror;
                TypeMirror componentType = arrayType.getComponentType();
                TypeName typeName = ClassName.get(componentType).unbox();
                builder.addComment("Handling $S types", argTypeMirror);
                if (typeName == ClassName.BOOLEAN) {
                    builder.addStatement("bundle.putBooleanArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == ClassName.BYTE) {
                    builder.addStatement("bundle.putByteArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == ClassName.SHORT) {
                    builder.addStatement("bundle.putShortArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == ClassName.INT) {
                    builder.addStatement("bundle.putIntArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == ClassName.LONG) {
                    builder.addStatement("bundle.putLongArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == ClassName.CHAR) {
                    builder.addStatement("bundle.putCharArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == ClassName.FLOAT) {
                    builder.addStatement("bundle.putFloatArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == ClassName.DOUBLE) {
                    builder.addStatement("bundle.putDoubleArray($N,$N)", getParamFiledName(), getOriginFiledName());
                } else {
                    getMessager().printMessage(Diagnostic.Kind.ERROR,
                            String.format("Cannot handle type %1$s[].", typeName.toString()),
                            element);
                    return false;
                }
                return true;
            }
        });
        methodSpec.endControlFlow();
        return true;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {

        DeclaredType objTypeMirror =(DeclaredType) getElement().asType();
        List<? extends TypeMirror> typeArguments = objTypeMirror.getTypeArguments();
        TypeMirror argTypeMirror = typeArguments.get(0);

        ArrayType arrayType = (ArrayType) argTypeMirror;
        TypeMirror componentType = arrayType.getComponentType();
        TypeName typeName = ClassName.get(componentType).unbox();
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(ClassName.get(arrayType));

        methodSpec.addComment("Handling $S types.", arrayType);

        if (typeName == ClassName.BOOLEAN) {
            methodSpec.addStatement("return bundle.getBooleanArray($N)", getParamFiledName());
        } else if (typeName == ClassName.BYTE) {
            methodSpec.addStatement("return bundle.getByteArray($N)", getParamFiledName());
        } else if (typeName == ClassName.SHORT) {
            methodSpec.addStatement("return bundle.getShortArray($N)", getParamFiledName());
        } else if (typeName == ClassName.INT) {
            methodSpec.addStatement("return bundle.getIntArray($N)", getParamFiledName());
        } else if (typeName == ClassName.LONG) {
            methodSpec.addStatement("return bundle.getLongArray($N)", getParamFiledName());
        } else if (typeName == ClassName.CHAR) {
            methodSpec.addStatement("return bundle.getCharArray($N)", getParamFiledName());
        } else if (typeName == ClassName.FLOAT) {
            methodSpec.addStatement("return bundle.getFloatArray($N)", getParamFiledName());
        } else if (typeName == ClassName.DOUBLE) {
            methodSpec.addStatement("return bundle.getDoubleArray($N)", getParamFiledName());
        } else {
            getMessager().printMessage(Diagnostic.Kind.ERROR,
                    String.format("Cannot handle type %1$s[].", typeName),
                    element);
            return false;
        }
        return true;

    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelBundleParamBindingArrayPrimitiveHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ViewModelBundleParamBindingArrayPrimitiveHolder getHolder() {
            return new ViewModelBundleParamBindingArrayPrimitiveHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
