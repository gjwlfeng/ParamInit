package com.zf.plugins.param.init;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class AnnotationEnv {
    public ProcessingEnvironment mProcessingEnv;
    public Elements mElementUtils;
    public Types mTypeUtils;
    public Messager mMessager;
    public Filer mFiler;

    public AnnotationEnv(ProcessingEnvironment processingEnv) {
        this.mProcessingEnv = processingEnv;
        this.mElementUtils = processingEnv.getElementUtils();
        this.mTypeUtils = processingEnv.getTypeUtils();
        this.mMessager = processingEnv.getMessager();
        this.mFiler = processingEnv.getFiler();
    }
}
