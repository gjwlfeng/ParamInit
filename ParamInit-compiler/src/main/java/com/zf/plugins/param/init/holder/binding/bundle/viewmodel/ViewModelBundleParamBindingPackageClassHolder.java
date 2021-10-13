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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class ViewModelBundleParamBindingPackageClassHolder extends ViewModelBundleParamBindingHolder {

    private ViewModelBundleParamBindingPackageClassHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        methodSpec.beginControlFlow("if ( $N != null )", getOriginFiledName());
        boolean result = MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {

                DeclaredType objTypeMirror =(DeclaredType) getElement().asType();
                List<? extends TypeMirror> typeArguments = objTypeMirror.getTypeArguments();
                TypeMirror argTypeMirror = typeArguments.get(0);

                TypeName typeName = ClassName.get(argTypeMirror).unbox();

                builder.addComment("Handling $S types.", argTypeMirror);

                if (typeName == TypeName.BOOLEAN) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.BYTE) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.SHORT) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.INT) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.LONG) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.CHAR) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.FLOAT) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.DOUBLE) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else {

                    final Element element = ((DeclaredType) argTypeMirror).asElement();
                    final String packageName = getElementUtils().getPackageOf(element).getQualifiedName().toString();
                    final String simpleName = element.getSimpleName().toString();
                    final String className = ClassName.get(packageName, simpleName).reflectionName();
                    getMessager().printMessage(Diagnostic.Kind.ERROR,
                            String.format("Cannot handle type %1$s.", className),
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

        DeclaredType objTypeMirror =(DeclaredType) getElement().asType();
        List<? extends TypeMirror> typeArguments = objTypeMirror.getTypeArguments();
        TypeMirror argTypeMirror = typeArguments.get(0);

        TypeName originalTypeName = ClassName.get(argTypeMirror);
        TypeName typeName = originalTypeName.unbox();
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(originalTypeName);
        if (typeName == TypeName.BOOLEAN) {
            methodSpec.addStatement("$T obj = bundle.getSerializable($N)", ClassName.OBJECT, getParamFiledName());
        } else if (typeName == TypeName.BYTE) {
            methodSpec.addStatement("$T obj = bundle.getSerializable($N)", ClassName.OBJECT, getParamFiledName());
        } else if (typeName == TypeName.SHORT) {
            methodSpec.addStatement("$T obj = bundle.getSerializable($N)", ClassName.OBJECT, getParamFiledName());
        } else if (typeName == TypeName.INT) {
            methodSpec.addStatement("$T obj = bundle.getSerializable($N)", ClassName.OBJECT, getParamFiledName());
        } else if (typeName == TypeName.LONG) {
            methodSpec.addStatement("$T obj = bundle.getSerializable($N)", ClassName.OBJECT, getParamFiledName());
        } else if (typeName == TypeName.CHAR) {
            methodSpec.addStatement("$T obj = bundle.getSerializable($N)", ClassName.OBJECT, getParamFiledName());
        } else if (typeName == TypeName.FLOAT) {
            methodSpec.addStatement("$T obj = bundle.getSerializable($N)", ClassName.OBJECT, getParamFiledName());
        } else if (typeName == TypeName.DOUBLE) {
            methodSpec.addStatement("$T obj = bundle.getSerializable($N)", ClassName.OBJECT, getParamFiledName());
        } else {
            final Element element = ((DeclaredType) argTypeMirror).asElement();
            final String packageName = getElementUtils().getPackageOf(element).getQualifiedName().toString();
            final String simpleName = element.getSimpleName().toString();
            final String className = ClassName.get(packageName, simpleName).reflectionName();
            getMessager().printMessage(Diagnostic.Kind.ERROR,
                    String.format("Cannot handle type %1$s.", className),
                    element);
            return false;
        }

        methodSpec.beginControlFlow("if (obj != null)");
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                methodSpec.addStatement("return ($T)obj", originalTypeName);
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

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelBundleParamBindingPackageClassHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ViewModelBundleParamBindingPackageClassHolder getHolder() {
            return new ViewModelBundleParamBindingPackageClassHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
