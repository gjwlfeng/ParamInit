package com.zf.plugins.param.init.holder.viewmodel;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.CallBack;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.param.init.ParamInitViewModel;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.holder.ParamHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

public abstract class ViewModelParamHolder extends ParamHolder {

    public final static String GET_METHOD_NAME_PREFIX = "get";
    public final static String GET_LIVE_DATA_METHOD_NAME_PREFIX = "get";
    public final static String GET_LIVE_DATA_METHOD_NAME_SUFFIX = "LiveData";

    public final static String SET_METHOD_NAME_PREFIX = "set";
    public final static String INIT_METHOD_NAME_PREFIX = "init";

    public ViewModelParamHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    public void onField(List<FieldSpec> fieldSpecList) {
        FieldSpec fieldSpec = FieldSpec.builder(String.class, getParamFiledName(), Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC)
                .initializer("$S", getParamFiledValue())
                .build();
        fieldSpecList.add(fieldSpec);

    }

    public CodeBlock onInitMethod() {
        CodeBlock.Builder builder = CodeBlock.builder();
        TypeMirror typeMirror = getElement().asType();

        ParamInitViewModel paramInitViewModel = getAnnotation(ParamInitViewModel.class);

        builder.beginControlFlow("if ( viewModel.$N != null )", getOriginFiledName());
        MethodSpecUtils.codeBlock(new CallBack() {
            @Override
            public boolean innerBlock() {
                builder.addStatement("viewModel.$N = savedStateHandle.getLiveData($N,viewModel.$N.getValue())", getOriginFiledName(), getParamFiledName(), getOriginFiledName());
                return true;
            }
        });
        builder.nextControlFlow("else");
        MethodSpecUtils.codeBlock(new CallBack() {
            @Override
            public boolean innerBlock() {
                builder.addStatement("viewModel.$N = savedStateHandle.getLiveData($N)", getOriginFiledName(), getParamFiledName());
                return true;
            }
        });
        builder.endControlFlow();
        return builder.build();
    }

    public boolean onGetLiveDataMethod(MethodSpec.Builder methodSpec) {
        methodSpec.addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX()));
        ParamInitViewModel paramInitViewModel = getAnnotation(ParamInitViewModel.class);
        methodSpec.addStatement("return savedStateHandle.getLiveData($N)", getParamFiledName());
        return true;
    }

    public boolean onGetLiveDataMethodWithInit(MethodSpec.Builder methodSpec) {
        methodSpec.addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX()));
        ParamInitViewModel paramInitViewModel = getAnnotation(ParamInitViewModel.class);
        methodSpec.addStatement("return savedStateHandle.getLiveData($N,viewModel.$N)", getParamFiledName(), getOriginFiledName());
        return true;

    }

    public boolean onGetMethod(MethodSpec.Builder methodSpec) {
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        TypeMirror typeMirror = getElement().asType();
        methodSpec.returns(ClassName.get(typeMirror));
        ParamInitViewModel paramInitViewModel = getAnnotation(ParamInitViewModel.class);
        methodSpec.addStatement("return ($T)(savedStateHandle.getLiveData($N,viewModel.$N).getValue())", ClassName.get(typeMirror), getParamFiledName(), getOriginFiledName());
        return true;
    }

//    public boolean onGetMethodWithInit(MethodSpec.Builder methodSpec) {
//        TypeMirror typeMirror = getElement().asType();
//        TypeName typeName = ClassName.get(typeMirror);
//        methodSpec.addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX()));
//        methodSpec.returns(typeName);
//        methodSpec.addParameter(ParameterSpec.builder(typeName, "initialValue", Modifier.FINAL).addAnnotation(ClassNameConstant.getNonnullClassName(isAndroidX())).build());
//
//        ParamInitViewModel paramInitViewModel = getAnnotation(ParamInitViewModel.class);
//        if (paramInitViewModel.persistence()) {
//
//            methodSpec.beginControlFlow("if (savedStateHandle.contains($N))", getParamFiledName());
//            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
//                @Override
//                public boolean innerBlock(MethodSpec.Builder builder) {
//                    methodSpec.addStatement("return savedStateHandle.get($N)", getParamFiledName());
//                    return true;
//                }
//            });
//            methodSpec.nextControlFlow("else");
//            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
//                @Override
//                public boolean innerBlock(MethodSpec.Builder builder) {
//                    methodSpec.addStatement("savedStateHandle.set($N,$N)", getParamFiledName(), "initialValue");
//                    return true;
//                }
//            });
//            methodSpec.endControlFlow();
//            methodSpec.addStatement("return initialValue");
//
//        } else {
//
//            ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(ClassNameConstant.MUTABLE_LIVE_DATA, ClassName.get(typeMirror));
//
//            methodSpec.beginControlFlow(" if (nonPersistenceMap.containsKey( $N ) ) ", getParamFiledName());
//            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
//                @Override
//                public boolean innerBlock(MethodSpec.Builder builder) {
//                    methodSpec.addStatement(" $T liveData  =($T) nonPersistenceMap.get($N) ", parameterizedTypeName, parameterizedTypeName, getParamFiledName());
//                    methodSpec.addStatement(" $T value  = ($T) liveData.getValue()  ", ClassName.get(typeMirror), ClassName.get(typeMirror));
//                    methodSpec.addStatement("return value == null? initialValue : value");
//                    return true;
//                }
//            });
//            methodSpec.nextControlFlow("else ");
//            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
//                @Override
//                public boolean innerBlock(MethodSpec.Builder builder) {
//                    methodSpec.addStatement(" $T liveData  = new $T( initialValue ) ", parameterizedTypeName, parameterizedTypeName);
//                    methodSpec.addStatement("nonPersistenceMap.put( $N ,liveData )", getParamFiledName());
//                    methodSpec.addStatement("return initialValue ");
//                    return true;
//                }
//            });
//
//            methodSpec.endControlFlow();
//        }
//
//        return true;
//    }

    public String getExpectKey() {
        ParamInitViewModel paramKey = getAnnotation(ParamInitViewModel.class);
        if (paramKey != null) {
            String value = paramKey.key();
            if (value.length() > 0) {
                return value;
            }
        }
        return null;
    }

    public String getExpectValue() {
        ParamInitViewModel paramKey = getAnnotation(ParamInitViewModel.class);
        if (paramKey != null) {
            String method = paramKey.value();
            if (method.length() > 0) {
                return method;
            }
        }
        return null;
    }

    public String getGetLiveDataParamMethodName() {
        String expectMethodName = getExpectValue();
        if (expectMethodName != null && expectMethodName.trim().length() > 0) {
            return GET_LIVE_DATA_METHOD_NAME_PREFIX + Utils.capitalize(expectMethodName) + GET_LIVE_DATA_METHOD_NAME_SUFFIX;
        }
        return GET_LIVE_DATA_METHOD_NAME_PREFIX + Utils.capitalize(getOriginFiledName()) + GET_LIVE_DATA_METHOD_NAME_SUFFIX;
    }

    public String getGetInitLiveDataParamMethodName() {
        String expectMethodName = getExpectValue();
        if (expectMethodName != null && expectMethodName.trim().length() > 0) {
            return GET_LIVE_DATA_METHOD_NAME_PREFIX + Utils.capitalize(expectMethodName) + "Init" + GET_LIVE_DATA_METHOD_NAME_SUFFIX;
        }
        return GET_LIVE_DATA_METHOD_NAME_PREFIX + Utils.capitalize(getOriginFiledName()) + "Init" + GET_LIVE_DATA_METHOD_NAME_SUFFIX;
    }

    public String getGetParamMethodName() {
        String expectMethodName = getExpectValue();
        if (expectMethodName != null && expectMethodName.trim().length() > 0) {
            return GET_METHOD_NAME_PREFIX + Utils.capitalize(expectMethodName);
        }
        return GET_METHOD_NAME_PREFIX + Utils.capitalize(getOriginFiledName());
    }

}

