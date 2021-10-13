package com.zf.plugins.param.init.holder.binding.bundle.fragment;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.MethodSpecBuilderCallBack;
import com.zf.plugins.param.init.MethodSpecUtils;
import com.zf.plugins.param.init.holder.action.FragmentCreationHolder;
import com.zf.plugins.param.init.holder.binding.bundle.FragmentBundleParamBindingHolder;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class BundleParamBindingPackageClassHolder extends FragmentBundleParamBindingHolder {

    private BundleParamBindingPackageClassHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, element, isSupportV4, isAndroidX);
    }

    @Override
    public boolean onSetValue(MethodSpec.Builder methodSpec) {
        methodSpec.beginControlFlow("if ( $N != null )", getOriginFiledName());
        boolean result = MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                TypeMirror typeMirror = getElement().asType();
                TypeName typeName = ClassName.get(typeMirror).unbox();
                builder.addComment("Handling $T types.", typeMirror);

                if (typeName == TypeName.BOOLEAN) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.BYTE) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.SHORT) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.INT) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.LONG) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.CHAR) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.FLOAT) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else if (typeName == TypeName.DOUBLE) {
                    builder.addStatement("bundle.putSerializable($N,$N)", getParamFiledName(), getOriginFiledName());
                } else {
                    final Element element = ((DeclaredType) typeMirror).asElement();
                    final String packageName = getElementUtils().getPackageOf(element).getQualifiedName().toString();
                    final String simpleName = element.getSimpleName().toString();
                    final String className = ClassName.get(packageName, simpleName).reflectionName();
                    getMessager().printMessage(Diagnostic.Kind.ERROR,
                            String.format("Cannot handle type %1$s.", className),
                            element);
                    return false;
                }
                return true;
            }
        });
        methodSpec.endControlFlow();
        return result;

    }

    @Override
    public boolean onGetValue(MethodSpec.Builder methodSpec) {
        TypeMirror typeMirror = getElement().asType();
        TypeName typeName = ClassName.get(typeMirror).unbox();
        methodSpec.addAnnotation(ClassNameConstant.getNullableClassName(isAndroidX()));
        methodSpec.returns(ClassName.get(typeMirror));

        methodSpec.addStatement("$T obj = bundle.getSerializable($N)", ClassNameConstant.SERIALIZABLE, getParamFiledName());
        methodSpec.beginControlFlow("if ( obj != null )");
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                methodSpec.addComment("Handling $S types.", typeMirror);
                if (typeName == TypeName.BOOLEAN) {
                    methodSpec.addStatement("return ($T)obj", ClassName.get(typeMirror));
                } else if (typeName == TypeName.BYTE) {
                    methodSpec.addStatement("return ($T)obj", ClassName.get(typeMirror));
                } else if (typeName == TypeName.SHORT) {
                    methodSpec.addStatement("return ($T)obj", ClassName.get(typeMirror));
                } else if (typeName == TypeName.INT) {
                    methodSpec.addStatement("return ($T)obj", ClassName.get(typeMirror));
                } else if (typeName == TypeName.LONG) {
                    methodSpec.addStatement("return ($T)obj", ClassName.get(typeMirror));
                } else if (typeName == TypeName.CHAR) {
                    methodSpec.addStatement("return ($T)obj", ClassName.get(typeMirror));
                } else if (typeName == TypeName.FLOAT) {
                    methodSpec.addStatement("return ($T)obj", ClassName.get(typeMirror));
                } else if (typeName == TypeName.DOUBLE) {
                    methodSpec.addStatement("return ($T)obj", ClassName.get(typeMirror));
                } else {
                    final Element element = ((DeclaredType) typeMirror).asElement();
                    final String packageName = getElementUtils().getPackageOf(element).getQualifiedName().toString();
                    final String simpleName = element.getSimpleName().toString();
                    final String className = ClassName.get(packageName, simpleName).reflectionName();
                    getMessager().printMessage(Diagnostic.Kind.ERROR,
                            String.format("Cannot handle type %1$s.", className),
                            element);
                    return false;
                }
                return false;
            }
        });
        methodSpec.nextControlFlow("else");
        MethodSpecUtils.codeBlock(methodSpec, new MethodSpecBuilderCallBack() {
            @Override
            public boolean innerBlock(MethodSpec.Builder builder) {
                methodSpec.addStatement("return null");
                return false;
            }
        });
        methodSpec.endControlFlow();


        return true;
    }

    public static class CreationHolder extends FragmentCreationHolder<BundleParamBindingPackageClassHolder> {


        public CreationHolder(AnnotationEnv annotationEnv, Element element, boolean isSupportV4, boolean isAndroidX) {
            super(annotationEnv, element, isSupportV4, isAndroidX);
        }

        public BundleParamBindingPackageClassHolder getHolder() {
            return new BundleParamBindingPackageClassHolder(this.annotationEnv, this.element, isSupportV4, isAndroidX);
        }
    }
}
