package com.zf.plugins.param.init.holder.fragment.type;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.param.init.ParamInitFragment;
import com.zf.plugins.param.init.holder.action.FragmentCreationHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

public class FragmentParamBinderHolder extends com.zf.plugins.param.init.holder.fragment.FragmentParamBinderHolder {

    private FragmentParamBinderHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public void onField(List<FieldSpec> fieldSpecList) {
        FieldSpec fieldSpec = FieldSpec.builder(String.class, getParamFiledName(), Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC)
                .initializer("$S", getParamFiledValue())
                .build();
        fieldSpecList.add(fieldSpec);
    }

    @Override
    public void onInitMethodWithBundle(MethodSpec.Builder methodSpec, String globalVarName) {
        ParamInitFragment param = getAnnotation(ParamInitFragment.class);
        if (param.inject()) {
            methodSpec.beginControlFlow("if ($N.containsKey($L))", globalVarName, getParamFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {

                    builder.addStatement("fragment.$N =($T)$N.getBinder($L)",
                            getOriginFiledName(),
                            ClassName.get(element.asType()),
                            globalVarName,
                            getParamFiledName());
                    return false;
                }
            });
            methodSpec.endControlFlow();
        }
    }


    @Override
    public void onSaveStateMethod(MethodSpec.Builder methodSpec) {
        ParamInitFragment param = getAnnotation(ParamInitFragment.class);
        if (param.persistence()) {
            methodSpec.beginControlFlow("if (fragment.$N != null )", getOriginFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    builder.addStatement("savedInstanceState.putBinder($L,fragment.$N)",
                            getParamFiledName(),
                            getOriginFiledName());
                    return false;
                }
            });
            methodSpec.endControlFlow();
        }
    }

    public static class CreationHolder extends FragmentCreationHolder<FragmentParamBinderHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public FragmentParamBinderHolder getHolder() {

            return new FragmentParamBinderHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
