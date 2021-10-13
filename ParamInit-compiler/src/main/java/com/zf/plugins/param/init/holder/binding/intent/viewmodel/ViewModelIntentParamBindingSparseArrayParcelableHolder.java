package com.zf.plugins.param.init.holder.binding.intent.viewmodel;

import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;
import com.zf.plugins.param.init.holder.binding.intent.ViewModelIntentParamBindingHolder;

import javax.lang.model.element.Element;

/**
 * 包装类
 */
public class ViewModelIntentParamBindingSparseArrayParcelableHolder extends ViewModelIntentParamBindingHolder {
    private ViewModelIntentParamBindingSparseArrayParcelableHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
//        methodSpec.beginControlFlow("if ( $N != null )", getFiledName());
//        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
//            @Override
//            public boolean innerBlock(MethodSpec.Builder builder) {
//                methodSpec.addStatement("intent.putSparseParcelableArray($N,$N)", getParamFiledName(), getFiledName());
//                return false;
//            }
//        });
//        methodSpec.endControlFlow();
        return false;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {
//        TypeMirror typeMirror = getElement().asType();
//        methodSpec.addStatement("return intent.getParcelableArrayExtra($N)", getParamFiledName());
//        methodSpec.returns(ClassName.get(typeMirror));
        return false;
    }

    public static class CreationHolder extends ViewModelCreationHolder<ViewModelIntentParamBindingSparseArrayParcelableHolder> {
        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ViewModelIntentParamBindingSparseArrayParcelableHolder getHolder() {
            return new ViewModelIntentParamBindingSparseArrayParcelableHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
