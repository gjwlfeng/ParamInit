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

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * 包装类
 */
public class ViewModelIntentParamBindingBinderHolder extends ViewModelIntentParamBindingHolder {

    private ViewModelIntentParamBindingBinderHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        return false;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {
        return false;

    }
    public static class CreationHolder extends ViewModelCreationHolder<ViewModelIntentParamBindingBinderHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element , boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4, isAndroidX);
        }

        public ViewModelIntentParamBindingBinderHolder getHolder() {
            return new ViewModelIntentParamBindingBinderHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
