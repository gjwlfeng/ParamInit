package com.zf.plugins.param.init.holder.viewmodel.type;

import com.squareup.javapoet.FieldSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.holder.action.ViewModelCreationHolder;
import com.zf.plugins.param.init.holder.viewmodel.ViewModelParamArrayHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

public class ViewModelParamArrayStringHolder extends ViewModelParamArrayHolder {

    public ViewModelParamArrayStringHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element,isSupportV4,isAndroidX);
    }


    @Override
    public void onField(List<FieldSpec> fieldSpecList) {
        FieldSpec fieldSpec = FieldSpec.builder(String.class, getParamFiledName(), Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .initializer("$S", getParamFiledValue())
                .build();
        fieldSpecList.add(fieldSpec);
    }


    public static class CreationHolder extends ViewModelCreationHolder<ViewModelParamArrayStringHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element ,boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public ViewModelParamArrayStringHolder getHolder() {
            return new ViewModelParamArrayStringHolder(this.annotationEnv, this.element,isSupportV4,isAndroidX);
        }
    }
}
