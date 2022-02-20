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
import com.zf.plugins.param.init.holder.binding.bundle.FragmentBundleParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.bundle.ViewModelBundleParamBindingHolder;
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
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingPackageClassHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSerializableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSizeFHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSizeHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSparseArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingStringHolder;
import com.zf.plugins.param.init.holder.binding.intent.ViewModelIntentParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingArrayCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingArrayCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingArrayListCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingArrayListIntegerHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingArrayListParcelableHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingArrayListStringHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingArrayPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingArrayStringHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingBinderHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingBundleHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingPackageClassHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingParcelableHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingSerializableHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingSizeFHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingSizeHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingSparseArrayParcelableHolder;
import com.zf.plugins.param.init.holder.ParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.intent.viewmodel.ViewModelIntentParamBindingStringHolder;

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

public class ViewModelIntentParamBindingHandler extends ViewModelParamBindingHandler<ViewModelIntentParamBindingHolder> {

    public ViewModelIntentParamBindingHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
    }

    public String getTargetParamBindingClassSimpleName() {
        return "ParamBindingIntent";
    }

    public CodeBlock getClassTypeDoc() {
        return CodeBlock.builder().
                add("ViewModel Intent data operation tool class\n")
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
        List<MethodSpec.Builder> methods = new ArrayList<>(1);
        ClassName target = ClassName.get(getTargetClassName().reflectionName(), getTargetParamBindingClassSimpleName());

        MethodSpec.Builder binding = MethodSpec.methodBuilder("binding")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(target)
                .addStatement("return new $T( new $T() )", target, ClassNameConstant.INTENT);

        MethodSpec.Builder withParamBinding = MethodSpec.methodBuilder("binding")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ParameterSpec.builder(ClassNameConstant.INTENT, "intent", Modifier.FINAL).addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX())).build())
                .returns(target)
                .addStatement("return new $T(  $N )", target, "intent");
        methods.add(binding);
        methods.add(withParamBinding);
        return methods;
    }


    protected List<MethodSpec.Builder> onSetMethods(List<ViewModelIntentParamBindingHolder> holders) {
        ClassName target = ClassName.get(getTargetClassName().reflectionName(), getTargetParamBindingClassSimpleName());
        List<MethodSpec.Builder> builders = new ArrayList<>();

        for (ParamBindingHolder holder : holders) {
            String paramFiledName = holder.getOriginFiledName();
            String putMethodName = holder.getPutMethodName();

            Element element = holder.getElement();
            DeclaredType typeMirror = (DeclaredType) element.asType();
            List<? extends TypeMirror> typeArguments = typeMirror.getTypeArguments();
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

    protected List<MethodSpec.Builder> onGetMethods(List<ViewModelIntentParamBindingHolder> holders) {
        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (ViewModelIntentParamBindingHolder holder : holders) {
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
    protected MethodSpec.Builder onGetResult(List<ViewModelIntentParamBindingHolder> holders) {

        String getMethodName = "getIntent";
        MethodSpec.Builder builder = MethodSpec.methodBuilder(getMethodName)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return this.intent")
                .returns(ClassNameConstant.INTENT);
        return builder;


    }

    protected List<MethodSpec.Builder> onHasExtraMethods(List<ViewModelIntentParamBindingHolder> holders) {
        List<MethodSpec.Builder> builders = new ArrayList<>();
        for (ViewModelIntentParamBindingHolder holder : holders) {
            String getMethodName = holder.getHasExtraMethodName();
            MethodSpec.Builder builder = MethodSpec.methodBuilder(getMethodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(ClassName.BOOLEAN);
            boolean result = holder.onHasExtra(builder);
            if (result) {
                builders.add(builder);
            }
        }
        return builders;
    }

    protected ViewModelIntentParamBindingHolder onPrimitiveHolder(Element element) {
        return new ViewModelIntentParamBindingPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onArrayPrimitiveHolder(Element element) {
        return new ViewModelIntentParamBindingArrayPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onArrayStringHolder(Element element) {
        return new ViewModelIntentParamBindingArrayStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onArrayParcelableHolder(Element element) {

        return new ViewModelIntentParamBindingArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onArrayCharSequenceHolder(Element element) {

        return new ViewModelIntentParamBindingArrayCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onPackageClassHolder(Element element) {

        return new ViewModelIntentParamBindingPackageClassHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onStringHolder(Element element) {

        return new ViewModelIntentParamBindingStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onBinderHolder(Element element) {

        return new ViewModelIntentParamBindingBinderHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onSparseArrayParcelableHolder(Element element) {

        return new ViewModelIntentParamBindingSparseArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }


    protected ViewModelIntentParamBindingHolder onArrayListIntegerHolder(Element element) {

        return new ViewModelIntentParamBindingArrayListIntegerHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onArrayListStringHolder(Element element) {

        return new ViewModelIntentParamBindingArrayListStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onArrayListParcelableHolder(Element element) {

        return new ViewModelIntentParamBindingArrayListParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onArrayListCharSequenceHolder(Element element) {

        return new ViewModelIntentParamBindingArrayListCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onBundleHolder(Element element) {
        return new ViewModelIntentParamBindingBundleHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onParcelableHolder(Element element) {

        return new ViewModelIntentParamBindingParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onSerializableHolder(Element element) {

        return new ViewModelIntentParamBindingSerializableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelIntentParamBindingHolder onCharSequenceHolder(Element element) {
        return new ViewModelIntentParamBindingCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ViewModelIntentParamBindingHolder onSizeHolder(Element element) {
        return new ViewModelIntentParamBindingSizeHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    protected ViewModelIntentParamBindingHolder onSizeFHolder(Element element) {
        return new ViewModelIntentParamBindingSizeFHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    @Override
    public TypeSpec.Builder createClass() {
        //        classInternal.addMethod(onPutExtrasMethod().build());

        List<ViewModelIntentParamBindingHolder> holders = getHolders();

        TypeSpec.Builder classInternal = createClassInternal(holders);

        List<MethodSpec.Builder> onHasExtraMethods = onHasExtraMethods(holders);
        for (MethodSpec.Builder methodBuilder : onHasExtraMethods) {
            classInternal.addMethod(methodBuilder.build());
        }

        return classInternal;
    }
}
