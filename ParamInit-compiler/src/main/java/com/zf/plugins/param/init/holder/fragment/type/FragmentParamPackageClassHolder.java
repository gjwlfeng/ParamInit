package com.zf.plugins.param.init.holder.fragment.type;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.param.init.ParamInitFragment;
import com.zf.plugins.param.init.holder.action.FragmentCreationHolder;
import com.zf.plugins.param.init.holder.fragment.FragmentParamHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class FragmentParamPackageClassHolder extends FragmentParamHolder {

    private FragmentParamPackageClassHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
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
            methodSpec.beginControlFlow("if ($N.containsKey($L))", globalVarName, getParamFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder builder) {
                    TypeMirror typeMirror = element.asType();
                    TypeName typeName = TypeName.get(typeMirror).unbox();

                    builder.addComment("Handling $S types.", typeMirror);

                    if (typeName == TypeName.BOOLEAN) {
                        builder.addStatement("fragment.$N = $N.getBoolean($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (typeName == TypeName.BYTE) {
                        builder.addStatement("fragment.$N = $N.getByte($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else if (typeName == TypeName.SHORT) {
                        builder.addStatement("fragment.$N = $N.getShort($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (typeName == TypeName.INT) {
                        builder.addStatement("fragment.$N = $N.getInt($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (typeName == TypeName.LONG) {
                        builder.addStatement("fragment.$N = $N.getLong($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (typeName == TypeName.CHAR) {
                        builder.addStatement("fragment.$N = $N.getChar($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (typeName == TypeName.FLOAT) {
                        builder.addStatement("fragment.$N = $N.getFloat($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());

                    } else if (typeName == TypeName.DOUBLE) {
                        builder.addStatement("fragment.$N = $N.getDouble($L)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName());
                    } else {
                        final Element element = ((DeclaredType) getElement().asType()).asElement();
                        final String packageName = getElementUtils().getPackageOf(element).getQualifiedName().toString();
                        final String simpleName = element.getSimpleName().toString();
                        final String className = ClassName.get(packageName, simpleName).reflectionName();
                        getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("Cannot handle type %1$s.", className),
                                element);
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
        if (paramInject.persistence()) {
            methodSpec.beginControlFlow("if (fragment.$N != null)", getOriginFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder methodSpec) {

                    TypeMirror typeMirror = getElement().asType();
                    TypeName typeName = ClassName.get(typeMirror).unbox();

                    methodSpec.addComment("Handling $S types.", typeMirror);

                    if (typeName == TypeName.BOOLEAN) {
                        methodSpec.addStatement("savedInstanceState.putBoolean($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.BYTE) {
                        methodSpec.addStatement("savedInstanceState.putByte($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.SHORT) {
                        methodSpec.addStatement("savedInstanceState.putShort($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.INT) {
                        methodSpec.addStatement("savedInstanceState.putInt($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.LONG) {
                        methodSpec.addStatement("savedInstanceState.putLong($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.CHAR) {
                        methodSpec.addStatement("savedInstanceState.putChar($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.FLOAT) {
                        methodSpec.addStatement("savedInstanceState.putFloat($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.DOUBLE) {
                        methodSpec.addStatement("savedInstanceState.putDouble($L,fragment.$N)", getParamFiledName(), getOriginFiledName());
                    } else {
                        final Element element = ((DeclaredType) typeMirror).asElement();
                        final String packageName = getElementUtils().getPackageOf(element).getQualifiedName().toString();
                        final String simpleName = element.getSimpleName().toString();
                        final String className = ClassName.get(packageName, simpleName).reflectionName();
                        getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("Cannot handle type %1$s.", className),
                                element);
                    }
                    return false;
                }
            });
            methodSpec.endControlFlow();

        }
    }

    public static class CreationHolder extends FragmentCreationHolder<FragmentParamPackageClassHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public FragmentParamPackageClassHolder getHolder() {
            return new FragmentParamPackageClassHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
