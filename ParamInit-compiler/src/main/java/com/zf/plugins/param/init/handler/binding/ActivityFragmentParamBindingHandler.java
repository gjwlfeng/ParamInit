package com.zf.plugins.param.init.handler.binding;

import com.squareup.javapoet.ClassName;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.handler.ParamBindingHandler;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public abstract class ActivityFragmentParamBindingHandler<T> extends ParamBindingHandler<T> {

    public ActivityFragmentParamBindingHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
    }

    protected T getHolder(Element element) {

        TypeMirror typeMirror = element.asType();
        TypeKind kind = typeMirror.getKind();

        if (kind.isPrimitive()) {
            return onPrimitiveHolder(element);
        } else if (kind == TypeKind.ARRAY) {
            ArrayType arrayType = (ArrayType) typeMirror;
            final TypeMirror componentType = arrayType.getComponentType();
            final TypeKind componentTypeKind = componentType.getKind();
            if (componentTypeKind.isPrimitive()) {
                return onArrayPrimitiveHolder(element);
            } else if (componentTypeKind == TypeKind.DECLARED) {
                DeclaredType declaredType = (DeclaredType) componentType;
                TypeElement typeElement = (TypeElement) declaredType.asElement();

                String argumentPackageName = getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
                String argumentClassName = typeElement.getSimpleName().toString();
                String argumentName = ClassName.get(argumentPackageName, argumentClassName).reflectionName();

                if (argumentName.equals(ClassNameConstant.STRING.reflectionName())) {
                    return onArrayStringHolder(element);
                } else if (argumentName.equals(ClassNameConstant.CHAR_SEQUENCE.reflectionName())) {
                    return onArrayCharSequenceHolder(element);
                }

                List<String> interfaceClasses = Utils.getInterfaceClasses(declaredType);

                if (interfaceClasses.contains(ClassNameConstant.PARCELABLE.reflectionName())) {
                    return onArrayParcelableHolder(element);
                } else if (interfaceClasses.contains(ClassNameConstant.CHAR_SEQUENCE.reflectionName())) {
                    return onArrayCharSequenceHolder(element);
                } else {
                    getMessager().printMessage(Diagnostic.Kind.ERROR,
                            String.format("%1$s does not implement %2$s interface",
                                    declaredType.asElement().getSimpleName(),
                                    ClassNameConstant.PARCELABLE),
                            element);
                }
            } else {
                getMessager().printMessage(Diagnostic.Kind.ERROR, "Unsupported type!", element);
            }
        } else if (kind == TypeKind.DECLARED) {

            DeclaredType declaredType = (DeclaredType) typeMirror;
            TypeElement typeElement = (TypeElement) declaredType.asElement();
            final String packageName = getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
            final String simpleName = typeElement.getSimpleName().toString();
            final String className = ClassName.get(packageName, simpleName).reflectionName();

            if (List.class.getName().equals(className)) {
                getMessager().printMessage(Diagnostic.Kind.ERROR, "'List' type is not supported, please use 'ArrayList' type.", element);
                throw new RuntimeException("Cannot handle type");
            } else if (Byte.class.getName().equals(className) ||
                    Character.class.getName().equals(className) ||
                    Short.class.getName().equals(className) ||
                    Integer.class.getName().equals(className) ||
                    Long.class.getName().equals(className) ||
                    Double.class.getName().equals(className) ||
                    Boolean.class.getName().equals(className) ||
                    Float.class.getName().equals(className)) {
                return onPackageClassHolder(element);
            } else if (ClassNameConstant.STRING.reflectionName().equals(className)) {
                return onStringHolder(element);
            } else if (ClassNameConstant.SIZE_F.reflectionName().equals(className)) {
                return onSizeFHolder(element);
            } else if (ClassNameConstant.SIZE.reflectionName().equals(className)) {
                return onSizeHolder(element);
            } else if (ClassNameConstant.BINDER.reflectionName().equals(className)) {
                return onBinderHolder(element);
            } else if (ClassNameConstant.SPARSE_ARRAY.reflectionName().equals(className)) {
                final List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                if (typeArguments.size() == 0) {
                    getMessager().printMessage(Diagnostic.Kind.ERROR, "The 'SparseArray<?>' data type is not supported.", element);
                    throw new RuntimeException("Cannot handle type");
                }
                final DeclaredType argumentType = (DeclaredType) typeArguments.get(0);
                final TypeElement argumentTypeElement = (TypeElement) argumentType.asElement();

                List<String> interfaceClasses = Utils.getInterfaceClasses(argumentType);

                if (interfaceClasses.contains(ClassNameConstant.PARCELABLE.reflectionName())) {
                    return onSparseArrayParcelableHolder(element);
                } else {
                    final String argumentPackageName = getElementUtils().getPackageOf(argumentTypeElement).getQualifiedName().toString();
                    final String argumentClassName = argumentTypeElement.getSimpleName().toString();
                    final String argumentName = ClassName.get(argumentPackageName, argumentClassName).reflectionName();
                    getMessager().printMessage(Diagnostic.Kind.ERROR,
                            String.format("%1$s does not implement %2$s interface", argumentName, ClassNameConstant.PARCELABLE),
                            element);
                }
            } else if (ClassNameConstant.ARRAY_LIST.reflectionName().equals(className)) {
                final List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                if (typeArguments.size() == 0) {
                    getMessager().printMessage(Diagnostic.Kind.ERROR, "The 'ArrayList<?>' data type is not supported.", element);
                    throw new RuntimeException("Cannot handle type");
                }
                final DeclaredType argumentType = (DeclaredType) typeArguments.get(0);
                final TypeElement argumentTypeElement = (TypeElement) argumentType.asElement();

                final String argumentPackageName = getElementUtils().getPackageOf(argumentTypeElement).getQualifiedName().toString();
                final String argumentClassName = argumentTypeElement.getSimpleName().toString();
                final String argumentName = ClassName.get(argumentPackageName, argumentClassName).reflectionName();
                if (Integer.class.getName().equals(argumentName)) {
                    return onArrayListIntegerHolder(element);
                } else if (String.class.getName().equals(argumentName)) {
                    return onArrayListStringHolder(element);
                }

                List<String> interfaceClasses = Utils.getInterfaceClasses(argumentType);

                if (interfaceClasses.contains(ClassNameConstant.PARCELABLE.reflectionName())) {
                    return onArrayListParcelableHolder(element);
                } else if (interfaceClasses.contains(ClassNameConstant.CHAR_SEQUENCE.reflectionName())) {
                    return onArrayListCharSequenceHolder(element);
                }
                getMessager().printMessage(Diagnostic.Kind.ERROR,
                        String.format("%1$s does not implement %2$s interface", argumentName, ClassNameConstant.PARCELABLE),
                        element);
            } else if (ClassNameConstant.BUNDLE.reflectionName().equals(className)) {
                return onBundleHolder(element);
            } else if (ClassNameConstant.STRING.reflectionName().equals(className)) {
                return onSerializableHolder(element);
            } else {
                List<String> interfaceClasses = Utils.getInterfaceClasses(declaredType);

                if (interfaceClasses.contains(ClassNameConstant.PARCELABLE.reflectionName())) {
                    return onParcelableHolder(element);
                } else if (interfaceClasses.contains(ClassNameConstant.SERIALIZABLE.reflectionName())) {
                    return onSerializableHolder(element);
                } else if (interfaceClasses.contains(ClassNameConstant.CHAR_SEQUENCE.reflectionName())) {
                    return onCharSequenceHolder(element);
                } else if (interfaceClasses.contains(ClassNameConstant.I_BINDER.reflectionName())) {
                    return onBinderHolder(element);
                } else {
                    getMessager().printMessage(Diagnostic.Kind.ERROR,
                            String.format("%1$s does not implement %2$s or %3$s interface",
                                    typeElement.getSimpleName(),
                                    ClassNameConstant.PARCELABLE,
                                    ClassNameConstant.SERIALIZABLE),
                            element);
                }
            }

        } else {
            final Name simpleName = element.getSimpleName();
            getMessager().printMessage(Diagnostic.Kind.ERROR,
                    String.format("Cannot handle type %1$s.", simpleName),
                    element);
        }
        throw new RuntimeException("Cannot handle type");
    }

}
