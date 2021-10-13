package com.zf.plugins.param.init.holder.activity.type;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.param.init.ParamInitActivity;
import com.zf.plugins.param.init.holder.action.ActivityCreationHolder;
import com.zf.plugins.param.init.holder.activity.ActivityParamHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;


public class ActivityParamPrimitiveHolder extends ActivityParamHolder {

    private ActivityParamPrimitiveHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
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
                    TypeMirror typeMirror = element.asType();
                    TypeKind kind = typeMirror.getKind();

                    builder.addComment("Handling $S types.", typeMirror);
                    if (kind == TypeKind.BOOLEAN) {
                        builder.addStatement("activity.$N = $N.getBoolean($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (kind == TypeKind.BYTE) {
                        builder.addStatement("activity.$N = $N.getByte($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (kind == TypeKind.SHORT) {
                        builder.addStatement("activity.$N = $N.getShort($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.INT) {
                        builder.addStatement("activity.$N = $N.getInt($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.LONG) {
                        builder.addStatement("activity.$N = $N.getLong($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.CHAR) {
                        builder.addStatement("activity.$N = $N.getChar($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.FLOAT) {
                        builder.addStatement("activity.$N = $N.getFloat($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.DOUBLE) {

                        builder.addStatement("activity.$N = $N.getDouble($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else {
                        getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("Cannot handle type %1$s.", kind.name()),
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
        if (paramInit.inject()) {
            methodSpec.beginControlFlow("if ($N.hasExtra($L))", globalVarName, getParamFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    TypeMirror typeMirror = element.asType();
                    TypeKind kind = typeMirror.getKind();
                    builder.addComment("Handling $S types.", typeMirror);
                    if (kind == TypeKind.BOOLEAN) {
                        builder.addStatement("activity.$N = $N.getBooleanExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (kind == TypeKind.BYTE) {
                        builder.addStatement("activity.$N = $N.getByteExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (kind == TypeKind.SHORT) {
                        builder.addStatement("activity.$N = $N.getShortExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.INT) {
                        builder.addStatement("activity.$N = $N.getIntExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.LONG) {
                        builder.addStatement("activity.$N = $N.getLongExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (kind == TypeKind.CHAR) {
                        builder.addStatement("activity.$N = $N.getCharExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (kind == TypeKind.FLOAT) {
                        builder.addStatement("activity.$N = $N.getFloatExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (kind == TypeKind.DOUBLE) {
                        builder.addStatement("activity.$N = $N.getDoubleExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else {
                        getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("Cannot handle type %1$s.", kind.name()),
                                getElement());
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

            TypeMirror typeMirror = element.asType();
            TypeKind kind = typeMirror.getKind();

            methodSpec.addComment("Handling $S types.", typeMirror);

            if (kind == TypeKind.BOOLEAN) {
                methodSpec.addStatement("savedInstanceState.putBoolean($L,activity.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.BYTE) {
                methodSpec.addStatement("savedInstanceState.putByte($L,activity.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.SHORT) {
                methodSpec.addStatement("savedInstanceState.putShort($L,activity.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.INT) {
                methodSpec.addStatement("savedInstanceState.putInt($L,activity.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.LONG) {
                methodSpec.addStatement("savedInstanceState.putLong($L,activity.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.CHAR) {
                methodSpec.addStatement("savedInstanceState.putChar($L,activity.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.FLOAT) {
                methodSpec.addStatement("savedInstanceState.putFloat($L,activity.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.DOUBLE) {
                methodSpec.addStatement("savedInstanceState.putDouble($L,activity.$N)", getParamFiledName(), getOriginFiledName());
            } else {
                getMessager().printMessage(Diagnostic.Kind.ERROR,
                        String.format("Cannot handle type3 %1$s.", kind.name()),
                        getElement());
            }
        }
    }

    public static class CreationHolder extends ActivityCreationHolder<ActivityParamPrimitiveHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public ActivityParamPrimitiveHolder getHolder() {

            return new ActivityParamPrimitiveHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
