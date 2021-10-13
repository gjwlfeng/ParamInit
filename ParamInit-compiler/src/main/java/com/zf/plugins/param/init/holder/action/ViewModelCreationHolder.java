package com.zf.plugins.param.init.holder.action;

import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.param.init.ParamInitViewModel;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;

public abstract class ViewModelCreationHolder<T> implements Action<T> {
    protected final AnnotationEnv annotationEnv;
    protected final Element element;
    protected final boolean isSupportV4;
    protected final boolean isAndroidX;

    public ViewModelCreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        this.annotationEnv = annotationEnv;
        this.element = element;
        this.isSupportV4 = isSupportV4;
        this.isAndroidX = isAndroidX;
    }

    @Override
    public T create() {

//        boolean publicModifier =element.getModifiers().contains(Modifier.PUBLIC);
//        if (!publicModifier) {
//            annotationEnv.mMessager.printMessage(Diagnostic.Kind.ERROR, "Please use the \"public\" modifier", element);
//            return null;
//        }

        ParamInitViewModel paramInject = element.getAnnotation(ParamInitViewModel.class);
        if (paramInject == null) {
            annotationEnv.mMessager.printMessage(Diagnostic.Kind.ERROR,
                    String.format(" \"Please use \"@%1$s\"", ParamInitViewModel.class.getSimpleName()),
                    element);
        }
        return getHolder();
    }

    public abstract T getHolder();
}
