package com.zf.plugins.param.init.holder.fragment.type;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.param.init.ParamInitFragment;
import com.zf.plugins.param.init.holder.action.FragmentCreationHolder;
import com.zf.plugins.param.init.holder.fragment.FragmentParamArrayHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class FragmentParamArrayPrimitiveHolder extends FragmentParamArrayHolder {

    private FragmentParamArrayPrimitiveHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
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

        ParamInitFragment paramInit = getAnnotation(ParamInitFragment.class);
        if (paramInit.inject()) {
            methodSpec.beginControlFlow("if ($N.containsKey($L))",globalVarName, getParamFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {

                    ArrayType arrayType = (ArrayType) element.asType();
                    TypeMirror componentType = arrayType.getComponentType();
                    TypeName typeName = TypeName.get(componentType).unbox();

                    builder.addComment("Handling $S types.", arrayType);

                    if (typeName == TypeName.BOOLEAN) {
                        builder.addStatement("fragment.$N = $N.getBooleanArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (typeName == TypeName.BYTE) {
                        builder.addStatement("fragment.$N = $N.getByteArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (typeName == TypeName.SHORT) {
                        builder.addStatement("fragment.$N = $N.getShortArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (typeName == TypeName.INT) {
                        builder.addStatement("fragment.$N = $N.getIntArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (typeName == TypeName.LONG) {
                        builder.addStatement("fragment.$N = $N.getLongArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (typeName == TypeName.CHAR) {
                        builder.addStatement("fragment.$N = $N.getCharArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (typeName == TypeName.FLOAT) {
                        builder.addStatement("fragment.$N = $N.getFloatArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (typeName == TypeName.DOUBLE) {

                        builder.addStatement("fragment.$N = $N.getDoubleArray($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else {
                        getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("Cannot handle type %1$s[].", componentType.getKind().name()),
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

        ParamInitFragment paramInit = getAnnotation(ParamInitFragment.class);
        if (paramInit.persistence()) {
            methodSpec.beginControlFlow("if (fragment.$N != null)", getOriginFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {

                    ArrayType typeMirror = (ArrayType) getElement().asType();
                    TypeKind kind = typeMirror.getComponentType().getKind();
                    builder.addComment("Handling $S types", typeMirror);
                    if (kind == TypeKind.BOOLEAN) {
                        builder.addStatement("savedInstanceState.putBooleanArray($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.BYTE) {
                        builder.addStatement("savedInstanceState.putByteArray($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.SHORT) {
                        builder.addStatement("savedInstanceState.putShortArray($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.INT) {
                        builder.addStatement("savedInstanceState.putIntArray($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.LONG) {
                        builder.addStatement("savedInstanceState.putLongArray($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.CHAR) {
                        builder.addStatement("savedInstanceState.putCharArray($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.FLOAT) {
                        builder.addStatement("savedInstanceState.putFloatArray($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (kind == TypeKind.DOUBLE) {
                        builder.addStatement("savedInstanceState.putDoubleArray($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    }
                    return false;
                }
            });
            methodSpec.endControlFlow();
        }
    }

    public static class CreationHolder extends FragmentCreationHolder<FragmentParamArrayPrimitiveHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element,boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element,isSupportV4,isAndroidX);
        }

        public FragmentParamArrayPrimitiveHolder getHolder() {

            return new FragmentParamArrayPrimitiveHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
