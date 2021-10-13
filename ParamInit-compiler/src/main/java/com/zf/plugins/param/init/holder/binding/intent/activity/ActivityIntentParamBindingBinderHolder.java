package com.zf.plugins.param.init.holder.binding.intent.activity;

import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.param.init.ParamInitActivity;
import com.zf.plugins.param.init.holder.action.ActivityCreationHolder;
import com.zf.plugins.param.init.holder.binding.intent.ActivityIntentParamBindingHolder;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * 包装类
 */
public class ActivityIntentParamBindingBinderHolder extends ActivityIntentParamBindingHolder {

    private ActivityIntentParamBindingBinderHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        ParamInitActivity initActivity = element.getAnnotation(ParamInitActivity.class);
        if(initActivity!=null){
            if (initActivity.persistence()) {
                getMessager().printMessage(Diagnostic.Kind.ERROR,"Binder types do not support persistence",element);
            }
        }
        return false;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {
        ParamInitActivity initActivity = element.getAnnotation(ParamInitActivity.class);
        if(initActivity!=null){
            if (initActivity.persistence()) {
                getMessager().printMessage(Diagnostic.Kind.ERROR,"Binder types do not support persistence",element);
            }
        }
        return false;
    }

    public static class CreationHolder extends ActivityCreationHolder<ActivityIntentParamBindingBinderHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ActivityIntentParamBindingBinderHolder getHolder() {
            return new ActivityIntentParamBindingBinderHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
