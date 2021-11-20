package com.zf.plugins.param.init.handler.fragment;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.holder.fragment.FragmentParamHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class XFragmentParamInitHandler extends BaseFragmentParamInitHandler {

    public XFragmentParamInitHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
    }

    @Override
    public ClassName getTargetBaseClassName() {
        return ClassNameConstant.X_FRAGMENT;
    }

//    public TypeSpec.Builder createClassType() {
//        return super.createClassType()
//                .addSuperinterface(ClassName.get("com.zf.param.init.fragment.x", "IXFragmentParamInit"));
//    }
}
