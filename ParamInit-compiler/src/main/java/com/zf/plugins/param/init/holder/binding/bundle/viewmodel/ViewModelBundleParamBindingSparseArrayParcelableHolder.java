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

/**
 * 包装类
 */
public class ViewModelBundleParamBindingSparseArrayParcelableHolder extends ViewModelBundleParamBindingHolder {
    private ViewModelBundleParamBindingSparseArrayParcelableHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        methodSpec.beginControlFlow("if ( $N != null )", getOriginFiledName());
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                builder.addStatement("bundle.putSparseParcelableArray($N,$N)", getParamFiledName(), getOriginFiledName());
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
        methodSpec.addStatement("return bundle.getSparseParcelableArray($N)", getParamFiledName());
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(typeName);
        return true;
    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelBundleParamBindingSparseArrayParcelableHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ViewModelBundleParamBindingSparseArrayParcelableHolder getHolder() {
            return new ViewModelBundleParamBindingSparseArrayParcelableHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
