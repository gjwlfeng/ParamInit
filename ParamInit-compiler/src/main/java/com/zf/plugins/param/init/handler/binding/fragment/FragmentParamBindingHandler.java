package com.zf.plugins.param.init.handler.binding.fragment;

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
import com.zf.plugins.param.init.holder.binding.bundle.FragmentBundleParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingArrayCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingArrayListCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingArrayListIntegerHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingArrayListParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingArrayListStringHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingArrayPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingArrayStringHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingBinderHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingBundleHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingPackageClassHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingSerializableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingSizeFHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingSizeHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingSparseArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.fragment.BundleParamBindingStringHolder;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class FragmentParamBindingHandler extends ActivityFragmentParamBindingHandler<FragmentBundleParamBindingHolder> {

    public FragmentParamBindingHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
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
        List<MethodSpec.Builder> bindingMethod = new ArrayList<>(2);
        ClassName target = ClassName.get(getTargetClassName().reflectionName(), getTargetParamBindingClassSimpleName());

        MethodSpec.Builder withParamBinding = MethodSpec.methodBuilder("binding")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ParameterSpec.builder(ClassNameConstant.BUNDLE, "bundle", Modifier.FINAL).addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX())).build())
                .returns(target)
                .addStatement("return new $T( $N )", target, "bundle");

        MethodSpec.Builder binding = MethodSpec.methodBuilder("binding")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(target)
                .addStatement("return new $T( new $T() )", target, ClassNameConstant.BUNDLE);

        bindingMethod.add(binding);
        bindingMethod.add(withParamBinding);

        return bindingMethod;
    }

    protected List<MethodSpec.Builder> onSetMethods(List<FragmentBundleParamBindingHolder> holders) {

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

    protected List<MethodSpec.Builder> onGetMethods(List<FragmentBundleParamBindingHolder> holders) {
        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (FragmentBundleParamBindingHolder holder : holders) {
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
    protected MethodSpec.Builder onGetResult(List<FragmentBundleParamBindingHolder> holders) {
        String getMethodName = "getBundle";
        MethodSpec.Builder builder = MethodSpec.methodBuilder(getMethodName)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return this.bundle")
                .returns(ClassNameConstant.BUNDLE);
        return builder;
    }

    protected List<MethodSpec.Builder> onContainKeyMethods(List<FragmentBundleParamBindingHolder> holders) {
        ClassName target = ClassName.get(getTargetClassName().reflectionName(), getTargetParamBindingClassSimpleName());
        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (FragmentBundleParamBindingHolder holder : holders) {
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

    protected FragmentBundleParamBindingHolder onPrimitiveHolder(Element element) {
        return new BundleParamBindingPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onArrayPrimitiveHolder(Element element) {
        return new BundleParamBindingArrayPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onArrayStringHolder(Element element) {
        return new BundleParamBindingArrayStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onArrayParcelableHolder(Element element) {

        return new BundleParamBindingArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onArrayCharSequenceHolder(Element element) {

        return new BundleParamBindingArrayCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onPackageClassHolder(Element element) {

        return new BundleParamBindingPackageClassHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onStringHolder(Element element) {

        return new BundleParamBindingStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onSizeFHolder(Element element) {

        return new BundleParamBindingSizeFHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onSizeHolder(Element element) {

        return new BundleParamBindingSizeHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onBinderHolder(Element element) {

        return new BundleParamBindingBinderHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onSparseArrayParcelableHolder(Element element) {

        return new BundleParamBindingSparseArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }


    protected FragmentBundleParamBindingHolder onArrayListIntegerHolder(Element element) {

        return new BundleParamBindingArrayListIntegerHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onArrayListStringHolder(Element element) {

        return new BundleParamBindingArrayListStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onArrayListParcelableHolder(Element element) {

        return new BundleParamBindingArrayListParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onArrayListCharSequenceHolder(Element element) {

        return new BundleParamBindingArrayListCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onBundleHolder(Element element) {
        return new BundleParamBindingBundleHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onParcelableHolder(Element element) {

        return new BundleParamBindingParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onSerializableHolder(Element element) {

        return new BundleParamBindingSerializableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected FragmentBundleParamBindingHolder onCharSequenceHolder(Element element) {
        return new BundleParamBindingCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    public TypeSpec.Builder createClass() {

        List<FragmentBundleParamBindingHolder> holders = getHolders();

        TypeSpec.Builder classInternal = createClassInternal(holders);

        List<MethodSpec.Builder> onContainKeyMethods = onContainKeyMethods(holders);
        for (MethodSpec.Builder methodBuilder : onContainKeyMethods) {
            classInternal.addMethod(methodBuilder.build());
        }

        return classInternal;
    }
}
