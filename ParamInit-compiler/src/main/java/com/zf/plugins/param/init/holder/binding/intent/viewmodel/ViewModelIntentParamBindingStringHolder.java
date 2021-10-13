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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

public class ViewModelIntentParamBindingStringHolder extends ViewModelIntentParamBindingHolder {

    private ViewModelIntentParamBindingStringHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
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
        DeclaredType objTypeMirror =(DeclaredType) getElement().asType();
        List<? extends TypeMirror> typeArguments = objTypeMirror.getTypeArguments();
        TypeMirror argTypeMirror = typeArguments.get(0);

        TypeName typeName = ClassName.get(argTypeMirror);
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(typeName);
        methodSpec.addStatement("return intent.getStringExtra($N)", getParamFiledName());
        return true;

    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelIntentParamBindingStringHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ViewModelIntentParamBindingStringHolder getHolder() {

//            ParamInject paramInject = element.getAnnotation(ParamInject.class);
//            ParamPersistence paramPersistence = element.getAnnotation(ParamPersistence.class);
//
//            TypeMirror typeMirror = element.asType();
//            DeclaredType declaredType = (DeclaredType) typeMirror;
//            Name simpleName = declaredType.asElement().getSimpleName();
//
//            if (false) {
//                annotationEnv.mMessager.printMessage(Diagnostic.Kind.ERROR,
//                        String.format(" \"%1$s\"type does not support \"@%2$s\"，please use \"@%3$s\"",
//                                simpleName,
//                                ParamPersistence.class.getSimpleName(),
//                                ParamInject.class.getSimpleName()),
//                        element);
//            }


            return new ViewModelIntentParamBindingStringHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
