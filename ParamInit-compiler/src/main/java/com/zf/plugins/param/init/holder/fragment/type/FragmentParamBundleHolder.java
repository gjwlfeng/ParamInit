package com.zf.plugins.param.init.holder.fragment.type;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.param.init.ParamInitFragment;
import com.zf.plugins.param.init.holder.action.FragmentCreationHolder;
import com.zf.plugins.param.init.holder.fragment.FragmentParamArrayHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

public class FragmentParamBundleHolder extends FragmentParamArrayHolder {

    private FragmentParamBundleHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
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

        ParamInitFragment paramInject = getAnnotation(ParamInitFragment.class);

        if (paramInject.inject()) {
            methodSpec.beginControlFlow("if ($N.containsKey($L))",globalVarName, getParamFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    builder.addStatement("fragment.$N = $N.getBundle($L)",
                            getOriginFiledName(),
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

        ParamInitFragment paramInject = getAnnotation(ParamInitFragment.class);

        //????????????????????????
        if (paramInject.persistence()) {
            methodSpec.beginControlFlow("if (fragment.$N != null)", getOriginFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    builder.addStatement("savedInstanceState.putBundle($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    return false;
                }
            });
            methodSpec.endControlFlow();
        }
    }

    public static class CreationHolder extends FragmentCreationHolder<FragmentParamBundleHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public FragmentParamBundleHolder getHolder() {
            return new FragmentParamBundleHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
