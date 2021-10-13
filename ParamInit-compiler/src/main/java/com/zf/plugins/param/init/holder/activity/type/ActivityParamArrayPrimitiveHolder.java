package com.zf.plugins.param.init.holder.activity.type;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.param.init.ParamInitActivity;
import com.zf.plugins.param.init.holder.action.ActivityCreationHolder;
import com.zf.plugins.param.init.holder.activity.ActivityParamArrayHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;

public class ActivityParamArrayPrimitiveHolder extends ActivityParamArrayHolder {

    private ActivityParamArrayPrimitiveHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }


    @Override
    public void onField(List<FieldSpec> fieldSpecList) {
        FieldSpec fieldSpec = FieldSpec.builder(String.class, getParamFiledName(), Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC)
                .initializer("$S", getParamFiledValue())
                .build();
        fieldSpecList.add(fieldSpec);
    }

    @Override
    public void onInitMethodWithBundle(MethodSpec.Builder methodSpec, String globalVarName) {

        ParamInitActivity paramInit = getAnnotation(ParamInitActivity.class);
        if (paramInit.inject()) {
            methodSpec.beginControlFlow("if ($N.containsKey($L))", globalVarName, getParamFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    ArrayType arrayType = (ArrayType) element.asType();
                    TypeKind kind = arrayType.getComponentType().getKind();

                    builder.addComment("Handling $S types.", arrayType);

                    if (kind == TypeKind.BOOLEAN) {
                        builder.addStatement("activity.$N = $N.getBooleanArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (kind == TypeKind.BYTE) {
                        builder.addStatement("activity.$N = $N.getByteArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (kind == TypeKind.SHORT) {
                        builder.addStatement("activity.$N = $N.getShortArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (kind == TypeKind.INT) {
                        builder.addStatement("activity.$N = $N.getIntArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (kind == TypeKind.LONG) {
                        builder.addStatement("activity.$N = $N.getLongArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (kind == TypeKind.CHAR) {
                        builder.addStatement("activity.$N = $N.getCharArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (kind == TypeKind.FLOAT) {
                        builder.addStatement("activity.$N = $N.getFloatArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (kind == TypeKind.DOUBLE) {
                        builder.addStatement("activity.$N = $N.getDoubleArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else {
                        getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("Cannot handle type %1$s[].", kind.name()),
                                getElement());
                    }
                    return false;
                }
            });
            methodSpec.endControlFlow();
        }
    }

    @Override
    public void onInitMethodWithIntent(MethodSpec.Builder methodSpec, String globalVarName) {

        ParamInitActivity paramInit = getAnnotation(ParamInitActivity.class);
        if (paramInit.persistence()) {
            methodSpec.beginControlFlow("if ($N.hasExtra($L))", globalVarName, getParamFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    ArrayType typeMirror = (ArrayType) getElement().asType();
                    TypeKind kind = typeMirror.getComponentType().getKind();

                    builder.addComment("Handling $T types", typeMirror);

                    if (kind == TypeKind.BOOLEAN) {
                        builder.addStatement("activity.$N = $N.getBooleanArrayExtra($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (kind == TypeKind.BYTE) {
                        builder.addStatement("activity.$N = $N.getByteArrayExtra($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (kind == TypeKind.SHORT) {
                        builder.addStatement("activity.$N = $N.getShortArrayExtra($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (kind == TypeKind.INT) {
                        builder.addStatement("activity.$N = $N.getIntArrayExtra($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (kind == TypeKind.LONG) {
                        builder.addStatement("activity.$N = $N.getLongArrayExtra($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (kind == TypeKind.CHAR) {
                        builder.addStatement("activity.$N = $N.getCharArrayExtra($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (kind == TypeKind.FLOAT) {

                        builder.addStatement("activity.$N = $N.getFloatArrayExtra($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (kind == TypeKind.DOUBLE) {

                        builder.addStatement("activity.$N = $N.getDoubleArrayExtra($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    }
                    return false;
                }
            });
            methodSpec.endControlFlow();
        }

    }

    @Override
    public void onSaveStateMethod(MethodSpec.Builder methodSpec) {

        ParamInitActivity paramInit = getAnnotation(ParamInitActivity.class);
        if (paramInit.persistence()) {
            methodSpec.beginControlFlow("if (activity.$N != null)", getOriginFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {

                    ArrayType typeMirror = (ArrayType) getElement().asType();
                    TypeKind kind = typeMirror.getComponentType().getKind();

                    builder.addComment("Handling $S types", typeMirror);

                    if (kind == TypeKind.BOOLEAN) {
                        builder.addStatement("savedInstanceState.putBooleanArray($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.BYTE) {
                        builder.addStatement("savedInstanceState.putByteArray($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.SHORT) {
                        builder.addStatement("savedInstanceState.putShortArray($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.INT) {
                        builder.addStatement("savedInstanceState.putIntArray($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.LONG) {
                        builder.addStatement("savedInstanceState.putLongArray($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.CHAR) {
                        builder.addStatement("savedInstanceState.putCharArray($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.FLOAT) {
                        builder.addStatement("savedInstanceState.putFloatArray($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.DOUBLE) {
                        builder.addStatement("savedInstanceState.putDoubleArray($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else {
                        getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("Cannot handle type %1$s[].", kind.name()),
                                getElement());
                    }
                    return false;
                }
            });
            methodSpec.endControlFlow();
        }

    }

    public static class CreationHolder extends ActivityCreationHolder<ActivityParamArrayPrimitiveHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ActivityParamArrayPrimitiveHolder getHolder() {
            return new ActivityParamArrayPrimitiveHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
