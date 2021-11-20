package com.zf.plugins.param.init.handler.fragment;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.handler.AndroidHandler;
import com.zf.plugins.param.init.handler.ParamBindingHandler;
import com.zf.plugins.param.init.handler.binding.fragment.FragmentParamBindingHandler;
import com.zf.plugins.param.init.holder.binding.bundle.FragmentBundleParamBindingHolder;
import com.zf.plugins.param.init.holder.fragment.FragmentParamHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamArrayCharSequenceHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamArrayListCharSequenceHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamArrayListIntegerHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamArrayListParcelableHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamArrayListStringHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamArrayParcelableHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamArrayPrimitiveHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamArrayStringHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamBinderHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamBundleHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamCharSequenceHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamPackageClassHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamParcelableHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamPrimitiveHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamSerializableHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamSizeFHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamSizeHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamSparseArrayParcelableHolder;
import com.zf.plugins.param.init.holder.fragment.type.FragmentParamStringHolder;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public abstract class BaseFragmentParamInitHandler extends AndroidHandler<FragmentParamHolder> {

    public final static String PARAM_INIT_INIT_METHOD = "init";
    public final static String PARAM_INIT_SAVE_STATE_METHOD = "saveState";

    public ParamBindingHandler<FragmentBundleParamBindingHolder> bindingHandler;

    public BaseFragmentParamInitHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
        this.bindingHandler = new FragmentParamBindingHandler(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
    }

    public TypeSpec.Builder createClassType() {
        return TypeSpec.classBuilder(getTargetClassName())
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc(getClassTypeDoc());
    }

    public List<FieldSpec> createField(List<FragmentParamHolder> holders) {
        List<FieldSpec> fieldSpecList = new ArrayList<>();
        for (FragmentParamHolder paramHolder : holders) {
            paramHolder.onField(fieldSpecList);
        }
        return fieldSpecList;
    }

    /**
     * 综合初始化
     *
     * @param holders
     * @return
     */
    public MethodSpec.Builder createComprehensiveInitMethod(List<FragmentParamHolder> holders) {

        final MethodSpec.Builder builder = MethodSpec.methodBuilder(PARAM_INIT_INIT_METHOD)
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC);

        builder.addParameter(ParameterSpec.builder(getTargetBaseClassName(), "target", Modifier.FINAL).addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX())).build())
                .addParameter(ParameterSpec.builder(ClassNameConstant.BUNDLE, "arguments", Modifier.FINAL).addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX())).build())
                .addParameter(ParameterSpec.builder(ClassNameConstant.BUNDLE, "savedInstanceState", Modifier.FINAL).addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX())).build());

        if (!holders.isEmpty()) {

            builder.addStatement("$T $N = ($T) target", getRootElementClassName(), "fragment", getRootElementClassName());

            String arguments = "arguments";
            String savedInstanceState = "savedInstanceState";

            builder.beginControlFlow("if ($N != null)", arguments);
            for (FragmentParamHolder paramHolder : holders) {
                paramHolder.onInitMethodWithBundle(builder, arguments);
            }
            builder.endControlFlow();

            builder.beginControlFlow("if ($N != null)", savedInstanceState);
            for (FragmentParamHolder paramHolder : holders) {
                paramHolder.onInitMethodWithBundle(builder, savedInstanceState);
            }
            builder.endControlFlow();
        }

        return builder;
    }


    /**
     * 初始化
     *
     * @param holders
     * @return
     */
    public MethodSpec.Builder createInitMethod(List<FragmentParamHolder> holders) {

        final MethodSpec.Builder builder = MethodSpec.methodBuilder(PARAM_INIT_INIT_METHOD)
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC);

        String target = "target";

        builder.addParameter(ParameterSpec.builder(getTargetBaseClassName(), target, Modifier.FINAL).addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX())).build())
                .addParameter(ParameterSpec.builder(ClassNameConstant.BUNDLE, "bundle", Modifier.FINAL).addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX())).build());

        builder.addStatement("$T $N = ($T) target", getRootElementClassName(), "fragment", getRootElementClassName());

        String bundle = "bundle";
        builder.beginControlFlow("if ($N != null)", bundle);
        for (FragmentParamHolder paramHolder : holders) {
            paramHolder.onInitMethodWithBundle(builder, bundle);
        }
        builder.endControlFlow();

        return builder;
    }

    public MethodSpec.Builder createSaveStateMethod(List<FragmentParamHolder> holders) {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(PARAM_INIT_SAVE_STATE_METHOD)
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC);

        String target = "target";

        builder.addParameter(ParameterSpec.builder(getTargetBaseClassName(), target, Modifier.FINAL).addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX())).build())
                .addParameter(ParameterSpec.builder(ClassNameConstant.BUNDLE, "savedInstanceState", Modifier.FINAL).addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX())).build());

        builder.addStatement("$T $N = ($T) target", getRootElementClassName(), "fragment", getRootElementClassName());


        for (FragmentParamHolder paramHolder : holders) {
            paramHolder.onSaveStateMethod(builder);
        }
        return builder;
    }


    @Override
    public TypeSpec.Builder createClass() {

        List<FragmentParamHolder> holders = getHolders();

        TypeSpec.Builder aClass = createClassType();
        List<FieldSpec> field = createField(holders);

        MethodSpec.Builder initMethod = createInitMethod(holders);
        MethodSpec.Builder comprehensiveInitMethod = createComprehensiveInitMethod(holders);
        MethodSpec.Builder saveStateMethod = createSaveStateMethod(holders);

        for (FieldSpec fieldSpec : field) {
            aClass.addField(fieldSpec);
        }
        aClass.addMethod(initMethod.build());
        aClass.addMethod(comprehensiveInitMethod.build());
        aClass.addMethod(saveStateMethod.build());

        TypeSpec.Builder paramBindingClass = bindingHandler.createClass();
        aClass.addType(paramBindingClass.build());

        return aClass;
    }

    protected FragmentParamHolder onPrimitiveHolder(Element element) {
        return new FragmentParamPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onArrayPrimitiveHolder(Element element) {
        return new FragmentParamArrayPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onArrayStringHolder(Element element) {
        return new FragmentParamArrayStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onArrayParcelableHolder(Element element) {

        return new FragmentParamArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onArrayCharSequenceHolder(Element element) {

        return new FragmentParamArrayCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onPackageClassHolder(Element element) {

        return new FragmentParamPackageClassHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onStringHolder(Element element) {

        return new FragmentParamStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onSizeFHolder(Element element) {

        return new FragmentParamSizeFHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onSizeHolder(Element element) {

        return new FragmentParamSizeHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onBinderHolder(Element element) {

        return new FragmentParamBinderHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onSparseArrayParcelableHolder(Element element) {

        return new FragmentParamSparseArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }


    protected FragmentParamHolder onArrayListIntegerHolder(Element element) {

        return new FragmentParamArrayListIntegerHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onArrayListStringHolder(Element element) {

        return new FragmentParamArrayListStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onArrayListParcelableHolder(Element element) {

        return new FragmentParamArrayListParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onArrayListCharSequenceHolder(Element element) {

        return new FragmentParamArrayListCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onBundleHolder(Element element) {
        return new FragmentParamBundleHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onParcelableHolder(Element element) {

        return new FragmentParamParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onSerializableHolder(Element element) {

        return new FragmentParamSerializableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentParamHolder onCharSequenceHolder(Element element) {
        return new FragmentParamCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

}
