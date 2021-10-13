package com.zf.plugins.param.init.handler.binding.activity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.zf.param.init.Constant;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.handler.ParamBindingHandler;
import com.zf.plugins.param.init.handler.binding.ActivityFragmentParamBindingHandler;
import com.zf.plugins.param.init.holder.ParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.bundle.ActivityBundleParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingArrayCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingArrayListCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingArrayListIntegerHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingArrayListParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingArrayListStringHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingArrayPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingArrayStringHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingBinderHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingBundleHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingPackageClassHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingSerializableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingSizeFHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingSizeHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingSparseArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.activity.ActivityBundleParamBindingStringHolder;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class ActivityBundleParamBindingHandler extends ActivityFragmentParamBindingHandler<ActivityBundleParamBindingHolder> {

    public ActivityBundleParamBindingHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
    }

    public String getTargetParamBindingClassSimpleName() {
        return "ParamBindingBundle";
    }

    public CodeBlock getClassTypeDoc() {
        return CodeBlock.builder().
                add("Fragment Bundle 参数传参工具类\n")
                .add("@author zf").build();
    }

    protected TypeSpec.Builder createParamBindingClassType() {
        return TypeSpec.classBuilder(ClassName.get(getPackageName(), getTargetParamBindingClassSimpleName()))
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC, Modifier.STATIC)
                .addJavadoc(getClassTypeDoc());
    }

    protected FieldSpec.Builder onField() {
        return FieldSpec.builder(ClassNameConstant.BUNDLE, "bundle", Modifier.FINAL, Modifier.PRIVATE);
    }

    protected MethodSpec.Builder onConstructorMethod() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameter(ParameterSpec.builder(ClassNameConstant.BUNDLE, "bundle", Modifier.FINAL).addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX())).build())
                .addStatement("this.bundle = bundle");
    }

    protected List<MethodSpec.Builder> onBindingMethod() {

        List<MethodSpec.Builder> bindingMethods = new ArrayList<>(2);

        ClassName target = ClassName.get(getTargetClassName().reflectionName(), getTargetParamBindingClassSimpleName());
        MethodSpec.Builder withParamBinding = MethodSpec.methodBuilder("binding")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ParameterSpec.builder(ClassNameConstant.BUNDLE, "bundle", Modifier.FINAL).addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX())).build())
                .returns(target)
                .addStatement("return new $T( $N )", target, "bundle");
        bindingMethods.add(withParamBinding);

        MethodSpec.Builder binding = MethodSpec.methodBuilder("binding")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(target)
                .addStatement("return new $T( new $T() )", target, ClassNameConstant.BUNDLE);
        bindingMethods.add(binding);
        return bindingMethods;
    }

    protected List<MethodSpec.Builder> onSetMethods(List<ActivityBundleParamBindingHolder> holders) {

        ClassName target = ClassName.get(getTargetClassName().reflectionName(), getTargetParamBindingClassSimpleName());

        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (ParamBindingHolder holder : holders) {
            String paramFiledName = holder.getOriginFiledName();
            String putMethodName = holder.getPutMethodName();
            TypeMirror typeMirror = holder.getElement().asType();
            MethodSpec.Builder builder = MethodSpec.methodBuilder(putMethodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(target)
                    .addParameter(
                            ParameterSpec.builder(ClassName.get(typeMirror), paramFiledName, Modifier.FINAL)
                                    .addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()))
                                    .build());
            boolean result = holder.onSetValue(builder);
            builder.addStatement("return this");

            if (result) {
                builders.add(builder);
            }
        }
        return builders;
    }

    protected List<MethodSpec.Builder> onGetMethods(List<ActivityBundleParamBindingHolder> holders) {

        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (ActivityBundleParamBindingHolder holder : holders) {
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
    protected MethodSpec.Builder onGetResult(List<ActivityBundleParamBindingHolder> holders) {
        String getMethodName = "getBundle";
        MethodSpec.Builder builder = MethodSpec.methodBuilder(getMethodName)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return this.bundle")
                .returns(ClassNameConstant.BUNDLE);
        return builder;
    }

    protected List<MethodSpec.Builder> onContainKeyMethods(List<ActivityBundleParamBindingHolder> holders) {
        ClassName target = ClassName.get(getTargetClassName().reflectionName(), getTargetParamBindingClassSimpleName());
        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (ActivityBundleParamBindingHolder holder : holders) {
            String getMethodName = holder.getContainKeyMethodName();
            MethodSpec.Builder builder = MethodSpec.methodBuilder(getMethodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(target);
            boolean result = holder.onContainKey(builder);
            if (result) {
                builders.add(builder);
            }
        }
        return builders;
    }

    protected ActivityBundleParamBindingHolder onPrimitiveHolder(Element element) {
        return new ActivityBundleParamBindingPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onArrayPrimitiveHolder(Element element) {
        return new ActivityBundleParamBindingArrayPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onArrayStringHolder(Element element) {
        return new ActivityBundleParamBindingArrayStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onArrayParcelableHolder(Element element) {

        return new ActivityBundleParamBindingArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onArrayCharSequenceHolder(Element element) {

        return new ActivityBundleParamBindingArrayCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onPackageClassHolder(Element element) {

        return new ActivityBundleParamBindingPackageClassHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onStringHolder(Element element) {

        return new ActivityBundleParamBindingStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onSizeFHolder(Element element) {

        return new ActivityBundleParamBindingSizeFHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onSizeHolder(Element element) {

        return new ActivityBundleParamBindingSizeHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onBinderHolder(Element element) {

        return new ActivityBundleParamBindingBinderHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onSparseArrayParcelableHolder(Element element) {

        return new ActivityBundleParamBindingSparseArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }


    protected ActivityBundleParamBindingHolder onArrayListIntegerHolder(Element element) {

        return new ActivityBundleParamBindingArrayListIntegerHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onArrayListStringHolder(Element element) {

        return new ActivityBundleParamBindingArrayListStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onArrayListParcelableHolder(Element element) {

        return new ActivityBundleParamBindingArrayListParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onArrayListCharSequenceHolder(Element element) {

        return new ActivityBundleParamBindingArrayListCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onBundleHolder(Element element) {

        return new ActivityBundleParamBindingBundleHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onParcelableHolder(Element element) {

        return new ActivityBundleParamBindingParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onSerializableHolder(Element element) {

        return new ActivityBundleParamBindingSerializableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ActivityBundleParamBindingHolder onCharSequenceHolder(Element element) {

        return new ActivityBundleParamBindingCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    public TypeSpec.Builder createClass() {

        List<ActivityBundleParamBindingHolder> holders = getHolders();

        TypeSpec.Builder classInternal = createClassInternal(holders);
        List<MethodSpec.Builder> onContainKeyMethods = onContainKeyMethods(holders);
        for (MethodSpec.Builder methodBuilder : onContainKeyMethods) {
            classInternal.addMethod(methodBuilder.build());
        }
        return classInternal;
    }
}
