package com.zf.plugins.param.init.handler.binding;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.handler.ParamBindingHandler;
import com.zf.plugins.param.init.handler.ViewModelHandler;
import com.zf.plugins.param.init.holder.ParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.ViewModelParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.bundle.ViewModelBundleParamBindingHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayListCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayListIntegerHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayListParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayListStringHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingArrayStringHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingBinderHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingBundleHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingCharSequenceHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingPackageClassHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingPrimitiveHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSerializableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSizeFHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSizeHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingSparseArrayParcelableHolder;
import com.zf.plugins.param.init.holder.binding.bundle.viewmodel.ViewModelBundleParamBindingStringHolder;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public abstract class ViewModelParamBindingHandler<T> extends ParamBindingHandler<T> {

    public ViewModelParamBindingHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
    }

    protected T getHolder(Element element) {

        TypeMirror objTypeMirror = element.asType();
        TypeKind objKind = objTypeMirror.getKind();

        if (objKind == TypeKind.DECLARED) {
            DeclaredType liveDataDeclaredType = (DeclaredType) objTypeMirror;
//            TypeElement typeElement = (TypeElement) liveDataDeclaredType.asElement();
            List<String> superClasses = Utils.getSuperClasses(liveDataDeclaredType);

            if (superClasses.contains(ClassNameConstant.LIVE_DATA.reflectionName())) {

                final List<? extends TypeMirror> typeArgs = liveDataDeclaredType.getTypeArguments();
                TypeMirror argTypeMirror = typeArgs.get(0);

//                Element argElement = argTypeMirror.asElement();
//                TypeMirror argTypeMirror = argElement.asType();
                TypeKind argKind = argTypeMirror.getKind();

                if (argKind.isPrimitive()) {
                    return onPrimitiveHolder(element);
                } else if (argKind == TypeKind.ARRAY) {
                    ArrayType arrayType = (ArrayType) argTypeMirror;
                    final TypeMirror componentType = arrayType.getComponentType();
                    final TypeKind componentTypeKind = componentType.getKind();
                    if (componentTypeKind.isPrimitive()) {
                        return onArrayPrimitiveHolder(element);
                    } else if (componentTypeKind == TypeKind.DECLARED) {
                        DeclaredType declaredType = (DeclaredType) argTypeMirror;
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
                } else if (argKind == TypeKind.DECLARED) {
                    DeclaredType declaredType = (DeclaredType) argTypeMirror;

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
                        final DeclaredType argumentType1 = (DeclaredType) typeArguments.get(0);
                        final TypeElement argumentTypeElement = (TypeElement) argumentType1.asElement();

                        List<String> interfaceClasses = Utils.getInterfaceClasses(argumentType1);

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
                        final DeclaredType argumentType1 = (DeclaredType) typeArguments.get(0);
                        final TypeElement argumentTypeElement = (TypeElement) argumentType1.asElement();

                        final String argumentPackageName = getElementUtils().getPackageOf(argumentTypeElement).getQualifiedName().toString();
                        final String argumentClassName = argumentTypeElement.getSimpleName().toString();
                        final String argumentName = ClassName.get(argumentPackageName, argumentClassName).reflectionName();
                        if (Integer.class.getName().equals(argumentName)) {
                            return onArrayListIntegerHolder(element);
                        } else if (String.class.getName().equals(argumentName)) {
                            return onArrayListStringHolder(element);
                        }

                        List<String> interfaceClasses = Utils.getInterfaceClasses(argumentType1);

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
                    }

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
                                        declaredType.toString(),
                                        ClassNameConstant.PARCELABLE,
                                        ClassNameConstant.SERIALIZABLE),
                                element);
                    }
                }
            }
        }

        final Name simpleName = element.getSimpleName();
        getMessager().printMessage(Diagnostic.Kind.ERROR,
                String.format("Cannot handle type %1$s.", simpleName),
                element);
        throw new RuntimeException("Cannot handle type");
    }
}
