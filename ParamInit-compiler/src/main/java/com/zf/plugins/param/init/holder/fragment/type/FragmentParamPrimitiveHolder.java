package com.zf.plugins.param.init.holder.fragment.type;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.param.init.ParamInitFragment;
import com.zf.plugins.param.init.holder.action.FragmentCreationHolder;
import com.zf.plugins.param.init.holder.fragment.FragmentParamHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class FragmentParamPrimitiveHolder extends FragmentParamHolder {

    private FragmentParamPrimitiveHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
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
        ParamInitFragment paramInject = getAnnotation(ParamInitFragment.class);
        if (paramInject.inject()) {
            methodSpec.beginControlFlow("if ($N.containsKey($L))",globalVarName, getParamFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    TypeMirror typeMirror = element.asType();
                    TypeKind kind = typeMirror.getKind();

                    builder.addComment("Handling $S types.",typeMirror);

                    if (kind == TypeKind.BOOLEAN) {
                        builder.addStatement("fragment.$N = $N.getBoolean($L,fragment.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (kind == TypeKind.BYTE) {
                        builder.addStatement("fragment.$N = $N.getByte($L,fragment.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (kind == TypeKind.SHORT) {
                        builder.addStatement("fragment.$N = $N.getShort($L,fragment.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.INT) {
                        builder.addStatement("fragment.$N = $N.getInt($L,fragment.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.LONG) {
                        builder.addStatement("fragment.$N = $N.getLong($L,fragment.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.CHAR) {
                        builder.addStatement("fragment.$N = $N.getChar($L,fragment.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.FLOAT) {
                        builder.addStatement("fragment.$N = $N.getFloat($L,fragment.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (kind == TypeKind.DOUBLE) {
                        builder.addStatement("fragment.$N = $N.getDouble($L,fragment.$N)",
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

        ParamInitFragment paramInject = getAnnotation(ParamInitFragment.class);

        //判断是否要持久化
        if (paramInject.persistence() ) {

            TypeMirror typeMirror = element.asType();
            TypeKind kind = typeMirror.getKind();
            methodSpec.addComment("Handling $S types.", typeMirror);
            if (kind == TypeKind.BOOLEAN) {
                methodSpec.addStatement("savedInstanceState.putBoolean($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.BYTE) {
                methodSpec.addStatement("savedInstanceState.putByte($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.SHORT) {
                methodSpec.addStatement("savedInstanceState.putShort($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.INT) {
                methodSpec.addStatement("savedInstanceState.putInt($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.LONG) {
                methodSpec.addStatement("savedInstanceState.putLong($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.CHAR) {
                methodSpec.addStatement("savedInstanceState.putChar($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.FLOAT) {
                methodSpec.addStatement("savedInstanceState.putFloat($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
            } else if (kind == TypeKind.DOUBLE) {
                methodSpec.addStatement("savedInstanceState.putDouble($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
            } else {
                getMessager().printMessage(Diagnostic.Kind.ERROR,
                        String.format("Cannot handle type %1$s.", kind.name()),
                        getElement());
            }
        }
    }

    public static class CreationHolder extends FragmentCreationHolder<FragmentParamPrimitiveHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public FragmentParamPrimitiveHolder getHolder() {
            return new FragmentParamPrimitiveHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
