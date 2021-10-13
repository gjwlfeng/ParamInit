package com.zf.plugins.param.init.holder.binding.intent.viewmodel;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;
import com.zf.plugins.param.init.holder.binding.intent.ViewModelIntentParamBindingHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class ViewModelIntentParamBindingPackageClassHolder extends ViewModelIntentParamBindingHolder {

    private ViewModelIntentParamBindingPackageClassHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
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
        TypeMirror typeMirror = getElement().asType();
        DeclaredType declaredType= (DeclaredType) typeMirror;
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        TypeMirror argTypeMirror = typeArguments.get(0);

        TypeName typeName = ClassName.get(argTypeMirror).unbox();
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(typeName);

        String defaultValue = "defaultValue";

        methodSpec.addParameter(
                ParameterSpec.builder(typeName, defaultValue, Modifier.FINAL)
                        .addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()))
                        .build());

        methodSpec.addComment("Handling $S types.", typeMirror);

        if (typeName == TypeName.BOOLEAN) {
            methodSpec.addStatement("return intent.getBooleanExtra($N,$N)",
                    getParamFiledName(),
                    defaultValue);
        } else if (typeName == TypeName.BYTE) {
            methodSpec.addStatement("return intent.getByteExtra($N,$N)",
                    getParamFiledName(),
                    defaultValue);
        } else if (typeName == TypeName.SHORT) {
            methodSpec.addStatement("return intent.getShortExtra($N,$N)",
                    getParamFiledName(),
                    defaultValue);

        } else if (typeName == TypeName.INT) {
            methodSpec.addStatement("return intent.getIntExtra($N,$N)",
                    getParamFiledName(),
                    defaultValue);

        } else if (typeName == TypeName.LONG) {
            methodSpec.addStatement("return intent.getLongExtra($N,$N)",
                    getParamFiledName(),
                    defaultValue);

        } else if (typeName == TypeName.CHAR) {
            methodSpec.addStatement("return intent.getCharExtra($N,$N)",
                    getParamFiledName(),
                    defaultValue);

        } else if (typeName == TypeName.FLOAT) {
            methodSpec.addStatement("return intent.getFloatExtra($N,$N)",
                    getParamFiledName(),
                    defaultValue);

        } else if (typeName == TypeName.DOUBLE) {
            methodSpec.addStatement("return intent.getDoubleExtra($N,$N)",
                    getParamFiledName(),
                    defaultValue);
        } else {
            final Element element = ((DeclaredType) getElement().asType()).asElement();
            final String packageName = getElementUtils().getPackageOf(element).getQualifiedName().toString();
            final String simpleName = element.getSimpleName().toString();
            final String className = ClassName.get(packageName, simpleName).reflectionName();

            getMessager().printMessage(Diagnostic.Kind.ERROR,
                    String.format("Cannot handle type %1$s.", className),
                    getElement());
            return false;
        }
        return true;

    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelIntentParamBindingPackageClassHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ViewModelIntentParamBindingPackageClassHolder getHolder() {
            return new ViewModelIntentParamBindingPackageClassHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
