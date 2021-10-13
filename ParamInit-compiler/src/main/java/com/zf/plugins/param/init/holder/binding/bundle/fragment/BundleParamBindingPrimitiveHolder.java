package com.zf.plugins.param.init.holder.binding.bundle.fragment;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.holder.action.FragmentCreationHolder;
import com.zf.plugins.param.init.holder.binding.bundle.FragmentBundleParamBindingHolder;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class BundleParamBindingPrimitiveHolder extends FragmentBundleParamBindingHolder {

    private BundleParamBindingPrimitiveHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {

        TypeMirror typeMirror = element.asType();
        TypeKind kind = typeMirror.getKind();

        methodSpec.addComment("Handling $S types.", typeMirror);

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
            return false;
        }
        return true;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {
        String defaultValue="defaultValue";
        TypeMirror typeMirror = getElement().asType();
        TypeName typeName = ClassName.get(typeMirror);
        methodSpec.addParameter(ParameterSpec.builder(typeName,defaultValue, Modifier.FINAL).build());
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(typeName);
        TypeKind kind = typeMirror.getKind();

        methodSpec.addComment("Handling $S types.", typeMirror);

        if (kind == TypeKind.BOOLEAN) {
            methodSpec.addStatement("return bundle.getBoolean($N,$N)", getParamFiledName(), defaultValue);
        } else if (kind == TypeKind.BYTE) {
            methodSpec.addStatement("return bundle.getByte($N,$N)", getParamFiledName(), defaultValue);
        } else if (kind == TypeKind.SHORT) {
            methodSpec.addStatement("return bundle.getShort($N,$N)", getParamFiledName(), defaultValue);
        } else if (kind == TypeKind.INT) {
            methodSpec.addStatement("return bundle.getInt($N,$N)", getParamFiledName(), defaultValue);
        } else if (kind == TypeKind.LONG) {
            methodSpec.addStatement("return bundle.getLong($N,$N)", getParamFiledName(), defaultValue);
        } else if (kind == TypeKind.CHAR) {
            methodSpec.addStatement("return bundle.getChar($N,$N)", getParamFiledName(), defaultValue);
        } else if (kind == TypeKind.FLOAT) {
            methodSpec.addStatement("return bundle.getFloat($N,$N)", getParamFiledName(), defaultValue);
        } else if (kind == TypeKind.DOUBLE) {
            methodSpec.addStatement("return bundle.getDouble($N,$N)", getParamFiledName(), defaultValue);
        } else {
            getMessager().printMessage(Diagnostic.Kind.ERROR,
                    String.format("Cannot handle type %1$s.", kind.name()),
                    getElement());
            return false;
        }
        return true;
    }

    public static class CreationHolder extends FragmentCreationHolder<BundleParamBindingPrimitiveHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv,element,isSupportV4,isAndroidX);
        }

        public BundleParamBindingPrimitiveHolder getHolder() {
            return new BundleParamBindingPrimitiveHolder(this.annotationEnv, this.element,isSupportV4,isAndroidX);
        }
    }
}
