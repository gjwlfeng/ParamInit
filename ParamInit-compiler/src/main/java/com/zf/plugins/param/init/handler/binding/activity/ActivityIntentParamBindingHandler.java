package com.zf.plugins.param.init.handler.binding.activity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.handler.ParamBindingHandler;
import com.zf.plugins.param.init.handler.binding.ActivityFragmentParamBindingHandler;
import com.zf.plugins.param.init.holder.ParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.intent.ActivityIntentParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingArrayCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingArrayListCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingArrayListIntegerHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingArrayListParcelableHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingArrayListStringHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingArrayPrimitiveClassHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingArrayStringHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingBinderHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingBundleHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingPackageClassHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingParcelableHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingSerializableHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingSizeFHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingSizeHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingSparseArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.intent.activity.ActivityIntentParamBindingStringHolder;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class ActivityIntentParamBindingHandler extends ActivityFragmentParamBindingHandler<ActivityIntentParamBindingHolder> {

    public ActivityIntentParamBindingHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
    }

    public String getTargetParamBindingClassSimpleName() {
        return "ParamBindingIntent";
    }

    public CodeBlock getClassTypeDoc() {
        return CodeBlock.builder().
                add("Activity Intent 参数传参的工具类\n")
                .add("@author zf").build();
    }

    protected TypeSpec.Builder createParamBindingClassType() {
        return TypeSpec.classBuilder(ClassName.get(getPackageName(), getTargetParamBindingClassSimpleName()))
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC, Modifier.STATIC)
                .addJavadoc(getClassTypeDoc());
    }

    protected FieldSpec.Builder onField() {
        return FieldSpec.builder(ClassNameConstant.INTENT, "intent", Modifier.FINAL, Modifier.PRIVATE);
    }

    protected MethodSpec.Builder onConstructorMethod() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameter(ParameterSpec.builder(ClassNameConstant.INTENT, "intent", Modifier.FINAL).addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX())).build())
                .addStatement("this.intent = intent");
    }

    protected List<MethodSpec.Builder> onBindingMethod() {
        List<MethodSpec.Builder> bindingMethod = new ArrayList<>(1);
        ClassName target = ClassName.get(getTargetClassName().reflectionName(), getTargetParamBindingClassSimpleName());

        MethodSpec.Builder binding = MethodSpec.methodBuilder("binding")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(target)
                .addStatement("return new $T( new $T() )", target, ClassNameConstant.INTENT);

        MethodSpec.Builder withIntentBinding = MethodSpec.methodBuilder("binding")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ParameterSpec.builder(ClassNameConstant.INTENT, "intent", Modifier.FINAL).addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX())).build())
                .returns(target)
                .addStatement("return new $T( $N )", target, "intent");
        bindingMethod.add(binding);
        bindingMethod.add(withIntentBinding);
        return bindingMethod;
    }

    protected List<MethodSpec.Builder> onSetMethods(List<ActivityIntentParamBindingHolder> holders) {
        ClassName target = ClassName.get(getTargetClassName().reflectionName(), getTargetParamBindingClassSimpleName());
        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (ParamBindingHolder holder : holders) {
            String paramFiledName = holder.getOriginFiledName();
            String putMethodName = holder.getPutMethodName();
            TypeMirror typeMirror = holder.getElement().asType();
            MethodSpec.Builder builder = MethodSpec.methodBuilder(putMethodName)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(
                            ParameterSpec.builder(ClassName.get(typeMirror), paramFiledName, Modifier.FINAL)
                                    .addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()))
                                    .build())
                    .returns(target);
            boolean result = holder.onSetValue(builder);
            builder.addStatement("return this");

            if (result) {
                builders.add(builder);
            }
        }
        return builders;
    }

    protected List<MethodSpec.Builder> onGetMethods(List<ActivityIntentParamBindingHolder> holders) {
        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (ParamBindingHolder holder : holders) {
            String getMethodName = holder.getGetMethodName();
            MethodSpec.Builder builder = MethodSpec.methodBuilder(getMethodName)
                    .addModifiers(Modifier.PUBLIC);
            boolean result = holder.onGetValue(builder);
            if (result) {
                builders.add(builder);
            }
        }
        return builders;
    }

    @Override
    protected MethodSpec.Builder onGetResult(List<ActivityIntentParamBindingHolder> holders) {
        String getMethodName = "getIntent";
        MethodSpec.Builder builder = MethodSpec.methodBuilder(getMethodName)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return this.intent")
                .returns(ClassNameConstant.INTENT);
        return builder;
    }

    protected List<MethodSpec.Builder> onHasExtraMethods(List<ActivityIntentParamBindingHolder> holders) {
        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (ActivityIntentParamBindingHolder holder : holders) {
            String getMethodName = holder.getHasExtraMethodName();
            MethodSpec.Builder builder = MethodSpec.methodBuilder(getMethodName)
                    .addModifiers(Modifier.PUBLIC);
            boolean result = holder.onHasExtra(builder);
            if (result) {
                builders.add(builder);
            }
        }
        return builders;
    }

    @Override
    protected ActivityIntentParamBindingHolder onPrimitiveHolder(Element element) {
        return new ActivityIntentParamBindingPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onArrayPrimitiveHolder(Element element) {
        return new ActivityIntentParamBindingArrayPrimitiveClassHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onArrayStringHolder(Element element) {
        return new ActivityIntentParamBindingArrayStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onArrayParcelableHolder(Element element) {
        return new ActivityIntentParamBindingArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onArrayCharSequenceHolder(Element element) {
        return new ActivityIntentParamBindingArrayCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onPackageClassHolder(Element element) {
        return new ActivityIntentParamBindingPackageClassHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onStringHolder(Element element) {
        return new ActivityIntentParamBindingStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onBinderHolder(Element element) {
        return new ActivityIntentParamBindingBinderHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onSparseArrayParcelableHolder(Element element) {
        return new ActivityIntentParamBindingSparseArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }


    @Override
    protected ActivityIntentParamBindingHolder onArrayListIntegerHolder(Element element) {
        return new ActivityIntentParamBindingArrayListIntegerHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onArrayListStringHolder(Element element) {
        return new ActivityIntentParamBindingArrayListStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onArrayListParcelableHolder(Element element) {

        return new ActivityIntentParamBindingArrayListParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onArrayListCharSequenceHolder(Element element) {
        return new ActivityIntentParamBindingArrayListCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onBundleHolder(Element element) {

        return new ActivityIntentParamBindingBundleHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onParcelableHolder(Element element) {
        return new ActivityIntentParamBindingParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onSizeFHolder(Element element) {
        return new ActivityIntentParamBindingSizeFHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onSizeHolder(Element element) {
        return new ActivityIntentParamBindingSizeHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onSerializableHolder(Element element) {
        return new ActivityIntentParamBindingSerializableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ActivityIntentParamBindingHolder onCharSequenceHolder(Element element) {
        return new ActivityIntentParamBindingCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    public TypeSpec.Builder createClass() {

        List<ActivityIntentParamBindingHolder> holders = getHolders();

        TypeSpec.Builder classInternal = createClassInternal(holders);
        List<MethodSpec.Builder> onHasExtraMethods = onHasExtraMethods(holders);
        for (MethodSpec.Builder methodBuilder : onHasExtraMethods) {
            classInternal.addMethod(methodBuilder.build());
        }
        return classInternal;
    }
}
