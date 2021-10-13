package com.zf.plugins.param.init.holder.binding.intent.activity;

import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.param.init.ParamInitActivity;
import com.zf.plugins.param.init.holder.action.ActivityCreationHolder;
import com.zf.plugins.param.init.holder.binding.intent.ActivityIntentParamBindingHolder;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class ActivityIntentParamBindingSizeHolder extends ActivityIntentParamBindingHolder {

    private ActivityIntentParamBindingSizeHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        ParamInitActivity annotation = element.getAnnotation(ParamInitActivity.class);
        if (annotation != null) {
            if (annotation.inject()) {
                getMessager().printMessage(Diagnostic.Kind.ERROR,"Size type does not support injection",element);
            }
        }
        return false;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {
        ParamInitActivity annotation = element.getAnnotation(ParamInitActivity.class);
        if (annotation != null) {
            if (annotation.inject()) {
                getMessager().printMessage(Diagnostic.Kind.ERROR,"Size type does not support injection",element);
            }
        }

        return false;
    }

    public static class CreationHolder extends ActivityCreationHolder<ActivityIntentParamBindingSizeHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ActivityIntentParamBindingSizeHolder getHolder() {
            return new ActivityIntentParamBindingSizeHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
