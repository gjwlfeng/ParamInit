package com.zf.plugins.param.init.holder.binding.intent.viewmodel;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;
import com.zf.plugins.param.init.holder.binding.intent.ViewModelIntentParamBindingHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class ViewModelIntentParamBindingPrimitiveHolder extends ViewModelIntentParamBindingHolder {

    private ViewModelIntentParamBindingPrimitiveHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        methodSpec.addStatement("intent.putExtra($N,$N)", getParamFiledName(), getOriginFiledName());
        return true;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {
        DeclaredType objTypeMirror =(DeclaredType) getElement().asType();
        List<? extends TypeMirror> typeArguments = objTypeMirror.getTypeArguments();
        TypeMirror argTypeMirror = typeArguments.get(0);

        TypeName typeName = ClassName.get(argTypeMirror);
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(typeName);

        String defaultValue = "defaultValue";

        methodSpec.addParameter(
                ParameterSpec.builder(ClassName.get(argTypeMirror), defaultValue, Modifier.FINAL)
                        .build());

        methodSpec.addComment("Handling $S types.", argTypeMirror);

        if (typeName == TypeName.BOOLEAN) {
            methodSpec.addStatement("return intent.getBooleanExtra($L,$N)",
                    getParamFiledName(),
                    defaultValue);
        } else if (typeName == TypeName.BYTE) {
            methodSpec.addStatement("return intent.getByteExtra($L,$N)",
                    getParamFiledName(),
                    defaultValue);
        } else if (typeName == TypeName.SHORT) {
            methodSpec.addStatement("return intent.getShortExtra($L,$N)",
                    getParamFiledName(),
                    defaultValue);

        } else if (typeName == TypeName.INT) {
            methodSpec.addStatement("return intent.getIntExtra($L,$N)",
                    getParamFiledName(),
                    defaultValue);

        } else if (typeName == TypeName.LONG) {
            methodSpec.addStatement("return intent.getLongExtra($L,$N)",
                    getParamFiledName(),
                    defaultValue);

        } else if (typeName == TypeName.CHAR) {
            methodSpec.addStatement("return intent.getCharExtra($L,$N)",
                    getParamFiledName(),
                    defaultValue);

        } else if (typeName == TypeName.FLOAT) {
            methodSpec.addStatement("return intent.getFloatExtra($L,$N)",
                    getParamFiledName(),
                    defaultValue);

        } else if (typeName == TypeName.DOUBLE) {
            methodSpec.addStatement("return intent.getDoubleExtra($L,$N)",
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

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelIntentParamBindingPrimitiveHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ViewModelIntentParamBindingPrimitiveHolder getHolder() {
            return new ViewModelIntentParamBindingPrimitiveHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
