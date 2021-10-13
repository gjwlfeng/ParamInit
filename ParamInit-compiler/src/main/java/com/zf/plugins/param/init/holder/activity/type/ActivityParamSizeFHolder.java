package com.zf.plugins.param.init.holder.activity.type;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.param.init.ParamInitActivity;
import com.zf.plugins.param.init.holder.action.ActivityCreationHolder;
import com.zf.plugins.param.init.holder.activity.ActivityParamHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

public class ActivityParamSizeFHolder extends ActivityParamHolder {

    private ActivityParamSizeFHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
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
                    builder.addStatement("activity.$N =$N.getSizeF($L)",
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
    public void onInitMethodWithIntent(MethodSpec.Builder methodSpec, String globalVarName) {
    }

    @Override
    public void onSaveStateMethod(MethodSpec.Builder methodSpec) {
        ParamInitActivity paramInit = getAnnotation(ParamInitActivity.class);
        if (paramInit.persistence()) {
            methodSpec.beginControlFlow("if (activity.$N != null)", getOriginFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    builder.addStatement("savedInstanceState.putSizeF($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    return false;
                }
            });
            methodSpec.endControlFlow();
        }
    }

    public static class CreationHolder extends ActivityCreationHolder<ActivityParamSizeFHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4,boolean isAndroidX) {
           super(annotationEnv,element,isSupportV4,isAndroidX);
        }

        public ActivityParamSizeFHolder getHolder() {

            return new ActivityParamSizeFHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
