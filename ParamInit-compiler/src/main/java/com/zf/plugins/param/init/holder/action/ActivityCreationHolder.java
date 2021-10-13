package com.zf.plugins.param.init.holder.action;

import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.param.init.ParamInitActivity;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;

public abstract class ActivityCreationHolder<T> implements Action<T> {
    protected final AnnotationEnv annotationEnv;
    protected final Element element;
    protected final boolean isSupportV4;
    protected final boolean isAndroidX;

    public ActivityCreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        this.annotationEnv = annotationEnv;
        this.element = element;
        this.isSupportV4 = isSupportV4;
        this.isAndroidX = isAndroidX;
    }

    @Override
    public T create() {
        boolean publicModifier =element.getModifiers().contains(Modifier.PUBLIC);
        if (!publicModifier) {
            annotationEnv.mMessager.printMessage(Diagnostic.Kind.ERROR, "Please use the \"public\" modifier", element);
            return null;
        }
        boolean staticModifier =element.getModifiers().contains(Modifier.STATIC);
        if (staticModifier) {
            annotationEnv.mMessager.printMessage(Diagnostic.Kind.ERROR, "Please do not use the \"static\" modifier.", element);
            return null;
        }
        boolean finalModifier =element.getModifiers().contains(Modifier.FINAL);
        if (finalModifier) {
            annotationEnv.mMessager.printMessage(Diagnostic.Kind.ERROR, "Please do not use the \"final\" modifier.", element);
            return null;
        }

        ParamInitActivity paramInject = element.getAnnotation(ParamInitActivity.class);
        if (paramInject == null) {
            annotationEnv.mMessager.printMessage(Diagnostic.Kind.ERROR,
                    String.format(" \"Please1 use \"@%1$s\"", ParamInitActivity.class.getSimpleName()),
                    element);
            return null;
        }


        return getHolder();
    }

    public abstract T getHolder();
}
