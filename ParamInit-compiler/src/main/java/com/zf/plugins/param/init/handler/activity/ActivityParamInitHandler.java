package com.zf.plugins.param.init.handler.activity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.handler.AndroidHandler;
import com.zf.plugins.param.init.handler.ParamBindingHandler;
import com.zf.plugins.param.init.handler.binding.activity.ActivityBundleParamBindingHandler;
import com.zf.plugins.param.init.handler.binding.activity.ActivityIntentParamBindingHandler;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamArrayStringHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamStringHolder;
import com.zf.plugins.param.init.holder.activity.ActivityParamHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamArrayCharSequenceHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamArrayListCharSequenceHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamArrayListIntegerHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamArrayListParcelableHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamArrayListStringHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamArrayParcelableHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamArrayPrimitiveHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamBinderHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamBundleHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamCharSequenceHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamPackageClassHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamParcelableHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamPrimitiveHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamSerializableHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamSizeFHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamSizeHolder;
import com.zf.plugins.param.init.holder.activity.type.ActivityParamSparseArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.ActivityBundleParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.intent.ActivityIntentParamBindingHolder;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class ActivityParamInitHandler extends AndroidHandler<ActivityParamHolder> {

    public final static String PARAM_INIT_INIT_METHOD = "init";
    public final static String PARAM_INIT_SAVE_STATE_METHOD = "onSaveInstanceState";

    public ParamBindingHandler<ActivityIntentParamBindingHolder> intentBindingHandler;
    public ParamBindingHandler<ActivityBundleParamBindingHolder> bundleBindingHandler;

    public ActivityParamInitHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement, paramBindingElement,isSupportV4, isAndroidX);
        this.intentBindingHandler = new ActivityIntentParamBindingHandler(annotationEnv, rootElement,itemElement, paramBindingElement, isSupportV4, isAndroidX);
        this.bundleBindingHandler = new ActivityBundleParamBindingHandler(annotationEnv, rootElement,itemElement, paramBindingElement, isSupportV4, isAndroidX);
    }

    @Override
    public ClassName getTargetBaseClassName() {
        return ClassNameConstant.ACTIVITY;
    }

    public TypeSpec.Builder createClassType() {
        return TypeSpec.classBuilder(getTargetClassName())
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .addSuperinterface(ClassName.get("com.zf.param.init.activity", "IActivityParamInit"))
                .addJavadoc(getClassTypeDoc());
    }

    public List<FieldSpec> createField(List<ActivityParamHolder> holders) {
        List<FieldSpec> fieldSpecList = new ArrayList<>();
        for (ActivityParamHolder holder : holders) {
            holder.onField(fieldSpecList);
        }
        return fieldSpecList;
    }

    public MethodSpec.Builder createInitMethod(List<ActivityParamHolder> holders) {

        final MethodSpec.Builder builder = MethodSpec.methodBuilder(PARAM_INIT_INIT_METHOD)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class);

        String target = "target";
        String intent = "intent";
        String savedInstanceState = "savedInstanceState";

        builder.addParameter(ParameterSpec.builder(ClassNameConstant.ACTIVITY, target, Modifier.FINAL)
                .addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX()))
                .build())
                .addParameter(ParameterSpec.builder(ClassNameConstant.INTENT, intent, Modifier.FINAL)
                        .addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()))
                        .build())
                .addParameter(ParameterSpec.builder(ClassNameConstant.BUNDLE, savedInstanceState, Modifier.FINAL)
                        .addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()))
                        .build());


        builder.addStatement("$T $N = ($T) target", getRootElementClassName(), "activity", getRootElementClassName());

        builder.addStatement("init($N,$N)", target, savedInstanceState);
        builder.addStatement("init($N,$N)", target, intent);
        return builder;
    }

    public MethodSpec.Builder createInitWithIntentMethod(List<ActivityParamHolder> holders) {

        final MethodSpec.Builder builder = MethodSpec.methodBuilder(PARAM_INIT_INIT_METHOD)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class);

        String target = "target";
        String intent = "intent";

        builder.addParameter(ParameterSpec.builder(ClassNameConstant.ACTIVITY, target, Modifier.FINAL)
                .addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX()))
                .build())
                .addParameter(ParameterSpec.builder(ClassNameConstant.INTENT, intent, Modifier.FINAL)
                        .addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()))
                        .build());


        builder.addStatement("$T $N = ($T) target", getRootElementClassName(), "activity", getRootElementClassName());

        builder.beginControlFlow("if ($N != null)", intent);
        for (ActivityParamHolder holder : holders) {
            holder.onInitMethodWithIntent(builder, intent);
        }
        builder.endControlFlow();

        return builder;
    }

    public MethodSpec.Builder createInitWithBundleMethod(List<ActivityParamHolder> holders) {

        final MethodSpec.Builder builder = MethodSpec.methodBuilder(PARAM_INIT_INIT_METHOD)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class);

        String target = "target";
        String savedInstanceState = "savedInstanceState";

        builder.addParameter(ParameterSpec.builder(ClassNameConstant.ACTIVITY, target, Modifier.FINAL)
                .addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX()))
                .build())
                .addParameter(ParameterSpec.builder(ClassNameConstant.BUNDLE, savedInstanceState, Modifier.FINAL)
                        .addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()))
                        .build());


        builder.addStatement("$T $N = ($T) target", getRootElementClassName(), "activity", getRootElementClassName());
        builder.beginControlFlow("if ($N != null)", savedInstanceState);
        for (ActivityParamHolder holder : holders) {
            holder.onInitMethodWithBundle(builder, savedInstanceState);
        }
        builder.endControlFlow();

        return builder;
    }

    public MethodSpec.Builder createSaveStateMethod(List<ActivityParamHolder> holders) {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(PARAM_INIT_SAVE_STATE_METHOD)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class);

        String target = "target";

        builder.addParameter(ParameterSpec.builder(ClassNameConstant.ACTIVITY, target, Modifier.FINAL).addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX())).build())
                .addParameter(ParameterSpec.builder(ClassNameConstant.BUNDLE, "savedInstanceState", Modifier.FINAL).addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX())).build());

        builder.addStatement("$T $N = ($T) target", getRootElementClassName(), "activity", getRootElementClassName());

        for (ActivityParamHolder holder : holders) {
            holder.onSaveStateMethod(builder);
        }

        return builder;
    }

    @Override
    public TypeSpec.Builder createClass() {

        List<ActivityParamHolder> holders = getHolders();

        TypeSpec.Builder aClass = createClassType();

        List<FieldSpec> field = createField(holders);
        MethodSpec.Builder initMethod = createInitMethod(holders);
        MethodSpec.Builder initWithIntentMethod = createInitWithIntentMethod(holders);
        MethodSpec.Builder initWithBundleMethod = createInitWithBundleMethod(holders);
        MethodSpec.Builder saveStateMethod = createSaveStateMethod(holders);

        for (FieldSpec fieldSpec : field) {
            aClass.addField(fieldSpec);
        }
        aClass.addMethod(initMethod.build());
        aClass.addMethod(initWithIntentMethod.build());
        aClass.addMethod(initWithBundleMethod.build());
        aClass.addMethod(saveStateMethod.build());

        TypeSpec.Builder intentParamBindingClass = intentBindingHandler.createClass();
        aClass.addType(intentParamBindingClass.build());

        TypeSpec.Builder bundleParamBindingClass = bundleBindingHandler.createClass();
        aClass.addType(bundleParamBindingClass.build());

        return aClass;
    }

    protected ActivityParamHolder onPrimitiveHolder(Element element) {
        return new ActivityParamPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onArrayPrimitiveHolder(Element element) {
        return new ActivityParamArrayPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onArrayStringHolder(Element element) {
        return new ActivityParamArrayStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onArrayParcelableHolder(Element element) {

        return new ActivityParamArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onArrayCharSequenceHolder(Element element) {

        return new ActivityParamArrayCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onPackageClassHolder(Element element) {

        return new ActivityParamPackageClassHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityParamHolder onSizeFHolder(Element element) {
        return new ActivityParamSizeFHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityParamHolder onSizeHolder(Element element) {
        return new ActivityParamSizeHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onStringHolder(Element element) {

        return new ActivityParamStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onBinderHolder(Element element) {

        return new ActivityParamBinderHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onSparseArrayParcelableHolder(Element element) {

        return new ActivityParamSparseArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }


    protected ActivityParamHolder onArrayListIntegerHolder(Element element) {

        return new ActivityParamArrayListIntegerHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onArrayListStringHolder(Element element) {

        return new ActivityParamArrayListStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onArrayListParcelableHolder(Element element) {

        return new ActivityParamArrayListParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onArrayListCharSequenceHolder(Element element) {

        return new ActivityParamArrayListCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onBundleHolder(Element element) {

        return new ActivityParamBundleHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onParcelableHolder(Element element) {

        return new ActivityParamParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onSerializableHolder(Element element) {

        return new ActivityParamSerializableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityParamHolder onCharSequenceHolder(Element element) {
        return new ActivityParamCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }
}
