package com.zf.plugins.param.init.holder.binding.bundle.viewmodel;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;
import com.zf.plugins.param.init.holder.binding.bundle.ViewModelBundleParamBindingHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class ViewModelBundleParamBindingPrimitiveHolder extends ViewModelBundleParamBindingHolder {

    private ViewModelBundleParamBindingPrimitiveHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {

        DeclaredType objTypeMirror =(DeclaredType) getElement().asType();
        List<? extends TypeMirror> typeArguments = objTypeMirror.getTypeArguments();
        TypeMirror argTypeMirror = typeArguments.get(0);

        TypeKind kind = argTypeMirror.getKind();

        methodSpec.addComment("Handling $S types.", argTypeMirror);
        if (kind == TypeKind.BOOLEAN) {
            methodSpec.addStatement("bundle.putBoolean($N,$N)", getParamFiledName(), getOriginFiledName());
        } else if (kind == TypeKind.BYTE) {
            methodSpec.addStatement("bundle.putByte($N,$N)", getParamFiledName(), getOriginFiledName());
        } else if (kind == TypeKind.SHORT) {
            methodSpec.addStatement("bundle.putShort($N,$N)", getParamFiledName(), getOriginFiledName());
        } else if (kind == TypeKind.INT) {
            methodSpec.addStatement("bundle.putInt($N,$N)", getParamFiledName(), getOriginFiledName());
        } else if (kind == TypeKind.LONG) {
            methodSpec.addStatement("bundle.putLong($N,$N)", getParamFiledName(), getOriginFiledName());
        } else if (kind == TypeKind.CHAR) {
            methodSpec.addStatement("bundle.putChar($N,$N)", getParamFiledName(), getOriginFiledName());
        } else if (kind == TypeKind.FLOAT) {
            methodSpec.addStatement("bundle.putFloat($N,$N)", getParamFiledName(), getOriginFiledName());
        } else if (kind == TypeKind.DOUBLE) {
            methodSpec.addStatement("bundle.putDouble($N,$N)", getParamFiledName(), getOriginFiledName());
        } else {
            getMessager().printMessage(Diagnostic.Kind.ERROR,
                    String.format("Cannot handle type %1$s.", kind.name()),
                    getElement());
        }
        return true;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {

        DeclaredType objTypeMirror =(DeclaredType) getElement().asType();
        List<? extends TypeMirror> typeArguments = objTypeMirror.getTypeArguments();
        TypeMirror argTypeMirror = typeArguments.get(0);

        TypeName typeName = ClassName.get(argTypeMirror);
        methodSpec.addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX()));
        methodSpec.returns(typeName);

        String defaultValue = "defaultValue";
        methodSpec.addParameter(
                ParameterSpec.builder(ClassName.get(argTypeMirror), defaultValue, Modifier.FINAL)
                        .build());

        methodSpec.addComment("Handling $S types.", argTypeMirror);
        if (typeName == ClassName.BOOLEAN) {
            methodSpec.addStatement("return bundle.getBoolean($N,$N)", getParamFiledName(), defaultValue);
        } else if (typeName == ClassName.BYTE) {
            methodSpec.addStatement("return bundle.getByte($N,$N)", getParamFiledName(), defaultValue);
        } else if (typeName == ClassName.SHORT) {
            methodSpec.addStatement("return bundle.getShort($N,$N)", getParamFiledName(), defaultValue);
        } else if (typeName == ClassName.INT) {
            methodSpec.addStatement("return bundle.getInt($N,$N)", getParamFiledName(), defaultValue);
        } else if (typeName == ClassName.LONG) {
            methodSpec.addStatement("return bundle.getLong($N,$N)", getParamFiledName(), defaultValue);
        } else if (typeName == ClassName.CHAR) {
            methodSpec.addStatement("return bundle.getChar($N,$N)", getParamFiledName(), defaultValue);
        } else if (typeName == ClassName.FLOAT) {
            methodSpec.addStatement("return bundle.getFloat($N,$N)", getParamFiledName(), defaultValue);
        } else if (typeName == ClassName.DOUBLE) {
            methodSpec.addStatement("return bundle.getDouble($N,$N)", getParamFiledName(),defaultValue);
        } else {
            getMessager().printMessage(Diagnostic.Kind.ERROR,
                    String.format("Cannot handle type %1$s.", typeName),
                    getElement());
            return false;
        }
        return true;
    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelBundleParamBindingPrimitiveHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ViewModelBundleParamBindingPrimitiveHolder getHolder() {
            return new ViewModelBundleParamBindingPrimitiveHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
