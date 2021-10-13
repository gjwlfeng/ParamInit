package com.zf.plugins.param.init.holder.binding.intent.viewmodel;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;
import com.zf.plugins.param.init.holder.binding.intent.ViewModelIntentParamBindingHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class ViewModelIntentParamBindingArrayPrimitiveHolder extends ViewModelIntentParamBindingHolder {

    private ViewModelIntentParamBindingArrayPrimitiveHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
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

        DeclaredType objTypeMirror =(DeclaredType) getElement().asType();
        List<? extends TypeMirror> typeArguments = objTypeMirror.getTypeArguments();
        TypeMirror argTypeMirror = typeArguments.get(0);

        ArrayType arrayType = (ArrayType) argTypeMirror;
        TypeMirror componentType = arrayType.getComponentType();
        TypeName typeName = ClassName.get(componentType).unbox();
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(ClassName.get(arrayType));

        methodSpec.addComment("Handling $S types.", argTypeMirror);

        if (typeName == TypeName.BOOLEAN) {
            methodSpec.addStatement("return intent.getBooleanArrayExtra($N)",
                    getParamFiledName());
        } else if (typeName == TypeName.BYTE) {
            methodSpec.addStatement("return intent.getByteArrayExtra($N)",
                    getParamFiledName());
        } else if (typeName == TypeName.SHORT) {
            methodSpec.addStatement("return intent.getShortArrayExtra($N)",
                    getParamFiledName());
        } else if (typeName == TypeName.INT) {
            methodSpec.addStatement("return intent.getIntArrayExtra($N)",
                    getParamFiledName());
        } else if (typeName == TypeName.LONG) {
            methodSpec.addStatement("return intent.getLongArrayExtra($N)",
                    getParamFiledName());
        } else if (typeName == TypeName.CHAR) {
            methodSpec.addStatement("return intent.getCharArrayExtra($N)",
                    getParamFiledName());
        } else if (typeName == TypeName.FLOAT) {
            methodSpec.addStatement("return intent.getFloatArrayExtra($N)",
                    getParamFiledName());
        } else if (typeName == TypeName.DOUBLE) {
            methodSpec.addStatement("return intent.getDoubleArrayExtra($N)",
                    getParamFiledName());
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

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelIntentParamBindingArrayPrimitiveHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ViewModelIntentParamBindingArrayPrimitiveHolder getHolder() {
            return new ViewModelIntentParamBindingArrayPrimitiveHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
