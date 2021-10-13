package com.zf.plugins.param.init.handler.binding.viewmodel;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.handler.ParamBindingHandler;
import com.zf.plugins.param.init.handler.binding.ViewModelParamBindingHandler;
import com.zf.plugins.param.init.holder.binding.ViewModelParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.bundle.FragmentBundleParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.bundle.ViewModelBundleParamBindingHolder;
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
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayListCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayListIntegerHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayListParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayListStringHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayStringHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingBinderHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingBundleHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSizeFHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSizeHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingViewModelBundleHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingPackageClassHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSerializableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSparseArrayParcelableHolder;
import com.zf.plugins.param.init.holder.ParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingStringHolder;

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

public class ViewModelBundleParamBindingHandler extends ViewModelParamBindingHandler<ViewModelBundleParamBindingHolder> {

    public ViewModelBundleParamBindingHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement,paramBindingElement, isSupportV4, isAndroidX);
    }

    public String getTargetParamBindingClassSimpleName() {
        return "ParamBindingBundle";
    }

    public CodeBlock getClassTypeDoc() {
        return CodeBlock.builder().
                add("ViewModel Bundle 参数传参的工具类\n")
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


    protected List<MethodSpec.Builder> onSetMethods(List<ViewModelBundleParamBindingHolder> holders) {
        ClassName target = ClassName.get(getTargetClassName().reflectionName(), getTargetParamBindingClassSimpleName());
        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (ParamBindingHolder holder : holders) {
            String paramFiledName = holder.getOriginFiledName();
            String putMethodName = holder.getPutMethodName();

            DeclaredType objTypeMirror =(DeclaredType) holder.getElement().asType();
            List<? extends TypeMirror> typeArguments = objTypeMirror.getTypeArguments();
            TypeMirror argTypeMirror = typeArguments.get(0);

            MethodSpec.Builder builder = MethodSpec.methodBuilder(putMethodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(target)
                    .addParameter(ParameterSpec.builder(ClassName.get(argTypeMirror), paramFiledName, Modifier.FINAL)
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

    protected List<MethodSpec.Builder> onGetMethods(List<ViewModelBundleParamBindingHolder> holders) {
        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (ViewModelParamBindingHolder holder : holders) {
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
    protected MethodSpec.Builder onGetResult(List<ViewModelBundleParamBindingHolder> holders) {

        String getMethodName = "getBundle";
        MethodSpec.Builder builder = MethodSpec.methodBuilder(getMethodName)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return this.bundle")
                .returns(ClassNameConstant.BUNDLE);
        return builder;

    }

    protected List<MethodSpec.Builder> onContainKeyMethods(List<ViewModelBundleParamBindingHolder> holders) {
        ClassName target = ClassName.get(getTargetClassName().reflectionName(), getTargetParamBindingClassSimpleName());
        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (ViewModelBundleParamBindingHolder holder : holders) {
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

    protected ViewModelBundleParamBindingHolder onPrimitiveHolder(Element element) {
        return new ViewModelBundleParamBindingPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onArrayPrimitiveHolder(Element element) {
        return new ViewModelBundleParamBindingArrayPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onArrayStringHolder(Element element) {
        return new ViewModelBundleParamBindingArrayStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onArrayParcelableHolder(Element element) {

        return new ViewModelBundleParamBindingArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onArrayCharSequenceHolder(Element element) {

        return new ViewModelBundleParamBindingArrayCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onPackageClassHolder(Element element) {

        return new ViewModelBundleParamBindingPackageClassHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onStringHolder(Element element) {

        return new ViewModelBundleParamBindingStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onSizeFHolder(Element element) {

        return new ViewModelBundleParamBindingSizeFHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onSizeHolder(Element element) {
        return new ViewModelBundleParamBindingSizeHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onBinderHolder(Element element) {

        return new ViewModelBundleParamBindingBinderHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onSparseArrayParcelableHolder(Element element) {

        return new ViewModelBundleParamBindingSparseArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }


    protected ViewModelBundleParamBindingHolder onArrayListIntegerHolder(Element element) {

        return new ViewModelBundleParamBindingArrayListIntegerHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onArrayListStringHolder(Element element) {

        return new ViewModelBundleParamBindingArrayListStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onArrayListParcelableHolder(Element element) {

        return new ViewModelBundleParamBindingArrayListParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onArrayListCharSequenceHolder(Element element) {

        return new ViewModelBundleParamBindingArrayListCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onBundleHolder(Element element) {
        return new ViewModelBundleParamBindingBundleHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onParcelableHolder(Element element) {

        return new ViewModelBundleParamBindingParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onSerializableHolder(Element element) {

        return new ViewModelBundleParamBindingSerializableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelBundleParamBindingHolder onCharSequenceHolder(Element element) {
        return new ViewModelBundleParamBindingCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }


    @Override
    public TypeSpec.Builder createClass() {
        //        classInternal.addMethod(onPutExtrasMethod().build());

        List<ViewModelBundleParamBindingHolder> holders = getHolders();

        TypeSpec.Builder classInternal = createClassInternal(holders);

        List<MethodSpec.Builder> onContainKeyMethods = onContainKeyMethods(holders);
        for (MethodSpec.Builder methodBuilder : onContainKeyMethods) {
            classInternal.addMethod(methodBuilder.build());
        }

        return classInternal;
    }
}
