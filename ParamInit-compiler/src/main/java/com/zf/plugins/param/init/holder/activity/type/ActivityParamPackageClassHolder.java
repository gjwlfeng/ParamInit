package com.zf.plugins.param.init.holder.activity.type;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.param.init.ParamInitActivity;
import com.zf.plugins.param.init.holder.action.ActivityCreationHolder;
import com.zf.plugins.param.init.holder.activity.ActivityParamHolder;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class ActivityParamPackageClassHolder extends ActivityParamHolder {

    private ActivityParamPackageClassHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
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
                    TypeName typeName = ClassName.get(typeMirror).unbox();

                    builder.addComment("Handling $S types",typeMirror);

                    if (typeName == TypeName.BOOLEAN) {
                        builder.addStatement("activity.$N = $N.getBoolean($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (typeName == TypeName.BYTE) {
                        builder.addStatement("activity.$N = $N.getByte($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (typeName == TypeName.SHORT) {
                        builder.addStatement("activity.$N = $N.getShort($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (typeName == TypeName.INT) {
                        builder.addStatement("activity.$N = $N.getInt($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (typeName == TypeName.LONG) {
                        builder.addStatement("activity.$N = $N.getLong($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (typeName == TypeName.CHAR) {
                        builder.addStatement("activity.$N = $N.getChar($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (typeName == TypeName.FLOAT) {
                        builder.addStatement("activity.$N = $N.getFloat($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (typeName == TypeName.DOUBLE) {
                        builder.addStatement("activity.$N = $N.getDouble($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else {
                        final Element element = ((DeclaredType) getElement().asType()).asElement();
                        final String packageName = getElementUtils().getPackageOf(element).getQualifiedName().toString();
                        final String simpleName = element.getSimpleName().toString();
                        final String className = ClassName.get(packageName, simpleName).reflectionName();

                        getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("Cannot handle type %1$s.", className),
                                ActivityParamPackageClassHolder.this.getElement());
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
                    TypeName typeName = ClassName.get(typeMirror).unbox();
                    builder.addComment("Handling $S types", typeMirror);
                    if (typeName==TypeName.BOOLEAN) {
                        builder.addStatement("activity.$N = $N.getBooleanExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (typeName==TypeName.BYTE) {
                        builder.addStatement("activity.$N = $N.getByteExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (typeName==TypeName.SHORT) {
                        builder.addStatement("activity.$N = $N.getShortExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (typeName==TypeName.INT) {
                        builder.addStatement("activity.$N = $N.getIntExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());

                    } else if (typeName==TypeName.LONG) {
                        builder.addStatement("activity.$N = $N.getLongExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (typeName==TypeName.CHAR) {
                        builder.addStatement("activity.$N = $N.getCharExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (typeName==TypeName.FLOAT) {
                        builder.addStatement("activity.$N = intent.getFloatExtra($L,activity.$N)",
                                getOriginFiledName(),
                                getParamFiledName(),
                                getOriginFiledName());
                    } else if (typeName==TypeName.DOUBLE) {
                        builder.addStatement("activity.$N = $N.getDoubleExtra($L,activity.$N)",
                                getOriginFiledName(),
                                globalVarName,
                                getParamFiledName(),
                                getOriginFiledName());
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

        ParamInitActivity paramInit = getAnnotation(ParamInitActivity.class);
        if (paramInit.persistence()) {
            methodSpec.beginControlFlow("if (activity.$N != null)", getOriginFiledName());
            MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
                @Override
                public boolean innerBlock(MethodSpec.Builder methodSpec) {

                    TypeMirror typeMirror = element.asType();
                    TypeName typeName = TypeName.get(typeMirror).unbox();

                    methodSpec.addComment("Handling $S types.", typeMirror);

                    if (typeName == TypeName.BOOLEAN) {
                        methodSpec.addStatement("savedInstanceState.putBoolean($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.BYTE) {
                        methodSpec.addStatement("savedInstanceState.putByte($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.SHORT) {
                        methodSpec.addStatement("savedInstanceState.putShort($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.INT) {
                        methodSpec.addStatement("savedInstanceState.putInt($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.LONG) {
                        methodSpec.addStatement("savedInstanceState.putLong($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.CHAR) {
                        methodSpec.addStatement("savedInstanceState.putChar($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.FLOAT) {
                        methodSpec.addStatement("savedInstanceState.putFloat($L,activity.$N)", getParamFiledName(), getOriginFiledName());
                    } else if (typeName == TypeName.DOUBLE) {
                        methodSpec.addStatement("savedInstanceState.putDouble($L,activity.$N)", getParamFiledName(), getOriginFiledName());
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

    public static class CreationHolder extends ActivityCreationHolder<ActivityParamPackageClassHolder> {

        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public ActivityParamPackageClassHolder getHolder() {

            return new ActivityParamPackageClassHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
