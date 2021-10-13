package com.zf.plugins.param.init.handler.viewmodel;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.CallBack;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.plugins.param.init.handler.ParamBindingHandler;
import com.zf.plugins.param.init.handler.ViewModelHandler;
import com.zf.plugins.param.init.handler.binding.viewmodel.ViewModelBundleParamBindingHandler;
import com.zf.plugins.param.init.handler.binding.viewmodel.ViewModelIntentParamBindingHandler;
import com.zf.plugins.param.init.holder.binding.bundle.ViewModelBundleParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.intent.ViewModelIntentParamBindingHolder;
import com.zf.plugins.param.init.holder.viewmodel.ViewModelParamHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class ViewModelParamInitHandler extends ViewModelHandler {

    public final static String PARAM_INIT_INIT_METHOD = "init";

    public ParamBindingHandler<ViewModelIntentParamBindingHolder> bindingIntentHandler;
    public ParamBindingHandler<ViewModelBundleParamBindingHolder> bindingBundleHandler;

    public ViewModelParamInitHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<Element> itemElement,List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
        this.bindingIntentHandler = new ViewModelIntentParamBindingHandler(annotationEnv, rootElement, itemElement,paramBindingElement, isSupportV4, isAndroidX);
        this.bindingBundleHandler = new ViewModelBundleParamBindingHandler(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
    }

    public TypeSpec.Builder createClassType() {
        return TypeSpec.classBuilder(getTargetClassName())
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC);
    }

    public List<FieldSpec> createField(List<ViewModelParamHolder> holders) {
        List<FieldSpec> fieldSpecList = new ArrayList<>();

        FieldSpec viewModule = FieldSpec.builder(getRootElementClassName(), "viewModel")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL).build();
        fieldSpecList.add(viewModule);

        FieldSpec savedStateHandle = FieldSpec.builder(ClassNameConstant.SAVED_STATE_HANDLE, "savedStateHandle")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL).build();
        fieldSpecList.add(savedStateHandle);

//        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(ClassName.get(HashMap.class), ClassName.get(String.class), ClassNameConstant.LIVE_DATA);

//        FieldSpec nonPersistenceMap = FieldSpec.builder(parameterizedTypeName, "nonPersistenceMap")
//                .addModifiers(Modifier.PRIVATE, Modifier.FINAL).initializer("new $L()", parameterizedTypeName).build();
//        fieldSpecList.add(nonPersistenceMap);

        for (ViewModelParamHolder holder : holders) {
            holder.onField(fieldSpecList);
        }
        return fieldSpecList;
    }

    public MethodSpec.Builder createPrivateConstructor() {
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addParameter(
                        ParameterSpec.builder(getRootElementClassName(), "viewModel", Modifier.FINAL)
                                .addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX()))
                                .build())
                .addParameter(
                        ParameterSpec.builder(ClassNameConstant.SAVED_STATE_HANDLE, "savedStateHandle", Modifier.FINAL)
                                .addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX()))
                                .build())
                .addModifiers(Modifier.PRIVATE);
        builder.addStatement("this.viewModel = viewModel");
        builder.addStatement("this.savedStateHandle = savedStateHandle");
        return builder;
    }

    public MethodSpec.Builder createInitMethod(List<ViewModelParamHolder> holders) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder(PARAM_INIT_INIT_METHOD)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        builder.addParameter(
                ParameterSpec.builder(ClassNameConstant.VIEW_MODEL, "target", Modifier.FINAL)
                        .addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX()))
                        .build())
                .addParameter(
                        ParameterSpec.builder(ClassNameConstant.SAVED_STATE_HANDLE, "savedStateHandle", Modifier.FINAL)
                                .addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX()))
                                .build())
                .build();

        builder.addStatement("$T viewModel = ($T) target ", getRootElementClassName(),getRootElementClassName());
        builder.beginControlFlow("if ( savedStateHandle != null)");
        MethodSpecUtils.codeBlock(new CallBack() {
            @Override
            public boolean innerBlock() {
                for (ViewModelParamHolder holder : holders) {
                    CodeBlock codeBlock = holder.onInitMethod();
                    builder.addCode(codeBlock);
                }
                return true;
            }
        });
        builder.endControlFlow();
        builder.returns(getTargetClassName());
        builder.addStatement("return new $T( viewModel , savedStateHandle )", getTargetClassName());
        return builder;
    }

    public List<MethodSpec.Builder> createGetLiveDataMethod(List<ViewModelParamHolder> holders) {
        List<MethodSpec.Builder> methodList = new ArrayList<>();

        for (ViewModelParamHolder holder : holders) {
            TypeMirror typeMirror = holder.getElement().asType();

            TypeName typeName = ClassName.get(typeMirror);
            TypeKind kind = typeMirror.getKind();
            if (kind.isPrimitive()) {
                typeName = typeName.box();
            }

            ParameterizedTypeName returnType = ParameterizedTypeName.get(ClassNameConstant.MUTABLE_LIVE_DATA, typeName);

            MethodSpec.Builder builder = MethodSpec.methodBuilder(holder.getGetLiveDataParamMethodName())
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .returns(returnType);

            boolean result = holder.onGetLiveDataMethod(builder);
            if (result) {
                methodList.add(builder);
            }
        }
        return methodList;
    }

//    public List<MethodSpec.Builder> createGetLiveDataMethodWithInit(List<ViewModelParamHolder> holders) {
//        List<MethodSpec.Builder> methodList = new ArrayList<>();
//
//        for (ViewModelParamHolder holder : holders) {
//            TypeMirror typeMirror = holder.getElement().asType();
//            ParameterizedTypeName returnType = ParameterizedTypeName.get(ClassNameConstant.MUTABLE_LIVE_DATA, ClassName.get(typeMirror));
//
//            MethodSpec.Builder builder = MethodSpec.methodBuilder(holder.getGetLiveDataParamMethodName())
//                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                    .returns(returnType);
//            boolean result = holder.onGetLiveDataMethodWithInit(builder);
//            if (result) {
//                methodList.add(builder);
//            }
//        }
//        return methodList;
//    }

    public List<MethodSpec.Builder> createGetMethod(List<ViewModelParamHolder> holders) {
        List<MethodSpec.Builder> methodList = new ArrayList<>();

        for (ViewModelParamHolder paramHolder : holders) {
            MethodSpec.Builder builder = MethodSpec.methodBuilder(paramHolder.getGetParamMethodName())
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
            boolean result = paramHolder.onGetMethod(builder);
            if (result) {
                methodList.add(builder);
            }
        }
        return methodList;
    }

    public List<MethodSpec.Builder> createGetLiveDataMethodWithInit(List<ViewModelParamHolder> holders) {
        List<MethodSpec.Builder> methodList = new ArrayList<>();

        for (ViewModelParamHolder holder : holders) {

            TypeMirror typeMirror = holder.getElement().asType();

            TypeName typeName = ClassName.get(typeMirror);
            TypeKind kind = typeMirror.getKind();
            if (kind.isPrimitive()) {
                typeName = typeName.box();
            }

            ParameterizedTypeName returnType = ParameterizedTypeName.get(ClassNameConstant.MUTABLE_LIVE_DATA, typeName);

            MethodSpec.Builder builder = MethodSpec.methodBuilder(holder.getGetInitLiveDataParamMethodName())
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .returns(returnType);
            boolean result = holder.onGetLiveDataMethodWithInit(builder);
            if (result) {
                methodList.add(builder);
            }
        }
        return methodList;
    }

    @Override
    public TypeSpec.Builder createClass() {

        List<ViewModelParamHolder> holders = getHolders();
        List<ViewModelParamHolder> bindingHolders = getBindingHolders();

        TypeSpec.Builder aClass = createClassType();

        List<FieldSpec> fields = createField(bindingHolders);
        for (FieldSpec fieldSpec : fields) {
            aClass.addField(fieldSpec);
        }

        MethodSpec.Builder privateConstructor = createPrivateConstructor();
        aClass.addMethod(privateConstructor.build());

        MethodSpec.Builder initMethod = createInitMethod(holders);
        aClass.addMethod(initMethod.build());

//        List<MethodSpec.Builder> getMethods = createGetMethod(holders);
//        for (MethodSpec.Builder builder : getMethods) {
//            aClass.addMethod(builder.build());
//        }

//        List<MethodSpec.Builder> getMethodWithInits = createGetMethodWithInit(holders);
//        for (MethodSpec.Builder builder : getMethodWithInits) {
//            aClass.addMethod(builder.build());
//        }

//        List<MethodSpec.Builder> getLiveDataMethods = createGetLiveDataMethod(holders);
//        for (MethodSpec.Builder builder : getLiveDataMethods) {
//            aClass.addMethod(builder.build());
//        }
//
//        List<MethodSpec.Builder> getLiveDataMethodWithInits = createGetLiveDataMethodWithInit(holders);
//        for (MethodSpec.Builder builder : getLiveDataMethodWithInits) {
//            aClass.addMethod(builder.build());
//        }

        TypeSpec.Builder paramBindingIntentClass = bindingIntentHandler.createClass();
        aClass.addType(paramBindingIntentClass.build());
//
        TypeSpec.Builder paramBindingBundleClass = bindingBundleHandler.createClass();
        aClass.addType(paramBindingBundleClass.build());

        return aClass;
    }


    @Override
    protected ViewModelParamHolder onArrayParcelableHolder(Element element) {
        return null;
    }
}
