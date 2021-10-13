package com.zf.plugins.param.init.holder.activity.type;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.param.init.ParamInitActivity;
import com.zf.plugins.param.init.holder.action.ActivityCreationHolder;
import com.zf.plugins.param.init.holder.activity.ActivityParamArrayHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

public class ActivityParamArrayParcelableHolder extends ActivityParamArrayHolder {

    private ActivityParamArrayParcelableHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
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

        ParamInitActivity paramInit = getAnnotation(ParamInitActivity.class);
        if (paramInit.inject()) {
            methodSpec.beginControlFlow("if ($N.containsKey($L))", globalVarName, getParamFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    TypeMirror typeMirror = element.asType();
                    builder.addStatement("activity.$N = ($T)$N.getParcelableArray($L)",
                            getOriginFiledName(),
                            ClassName.get(typeMirror),
                            globalVarName,
                            getParamFiledName());
                    return false;
                }
            });
            methodSpec.endControlFlow();
        }
    }

    @Override
    public void onInitMethodWithIntent(MethodSpec.Builder methodSpec, String globalVarName) {

        ParamInitActivity paramInit = getAnnotation(ParamInitActivity.class);
        if (paramInit.inject()) {
            methodSpec.beginControlFlow("if ($N.hasExtra($L))",globalVarName, getParamFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    TypeMirror typeMirror = element.asType();
                    builder.addStatement("activity.$N = ($T)$N.getParcelableArrayExtra($L)",
                            getOriginFiledName(),
                            ClassName.get(typeMirror),
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

        ParamInitActivity paramInit = getAnnotation(ParamInitActivity.class);
        if (paramInit.persistence()) {
            methodSpec.beginControlFlow("if (activity.$N != null)", getOriginFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    builder.addStatement("savedInstanceState.putParcelableArray($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    return false;
                }
            });
            methodSpec.endControlFlow();
        }
    }

    public static class CreationHolder extends ActivityCreationHolder<ActivityParamArrayParcelableHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4,boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public ActivityParamArrayParcelableHolder getHolder() {
            return new ActivityParamArrayParcelableHolder(this.annotationEnv, this.element,isSupportV4,isAndroidX);
        }
    }
}
