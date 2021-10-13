package com.zf.plugins.param.init.holder.binding.intent.activity;

import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.holder.action.ActivityCreationHolder;
import com.zf.plugins.param.init.holder.binding.intent.ActivityIntentParamBindingHolder;

import javax.lang.model.element.Element;

/**
 * 包装类
 */
public class ActivityIntentParamBindingSparseArrayParcelableHolder extends ActivityIntentParamBindingHolder {
    private ActivityIntentParamBindingSparseArrayParcelableHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
//        methodSpec.beginControlFlow("if ( $N != null )", getFiledName());
//        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
//            @Override
//            public void innerBlock(MethodSpec.Builder builder) {
//                methodSpec.addStatement("intent.putSparseParcelableArray($N,$N)", getParamFiledName(), getFiledName());
//            }
//        });
//        methodSpec.endControlFlow();
        return false;
    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {
//        TypeMirror typeMirror = getElement().asType();
//        methodSpec.addStatement("return intent.getParcelableArrayExtra($N)", getParamFiledName());
//        methodSpec.returns(ClassName.get(typeMirror));
        return false;
    }

    public static class CreationHolder extends ActivityCreationHolder<ActivityIntentParamBindingSparseArrayParcelableHolder> {
        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ActivityIntentParamBindingSparseArrayParcelableHolder getHolder() {
            return new ActivityIntentParamBindingSparseArrayParcelableHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
