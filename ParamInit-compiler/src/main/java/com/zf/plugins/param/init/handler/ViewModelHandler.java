package com.zf.plugins.param.init.handler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.zf.param.init.Constant;
import com.zf.param.init.ParamInitClass;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.plugins.param.init.Utils;
import com.zf.plugins.param.init.exception.UnsupportedDataTypeException;
import com.zf.plugins.param.init.holder.viewmodel.ViewModelParamHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamArrayCharSequenceHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamArrayListCharSequenceHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamArrayListIntegerHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamArrayListParcelableHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamArrayListStringHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamArrayParcelableHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamArrayPrimitiveHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamArrayStringHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamBinderHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamBundleHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamCharSequenceHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamPackageClassHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamParcelableHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamPrimitiveHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamSerializableHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamSizeFHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamSizeHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamSparseArrayParcelableHolder;
import com.zf.plugins.param.init.holder.viewmodel.type.ViewModelParamStringHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public abstract class ViewModelHandler extends BaseHandler<ViewModelParamHolder> {

    public ViewModelHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        super(annotationEnv, rootElement, itemElement, paramBindingElement, isSupportV4, isAndroidX);
    }

    public void generateClass() throws IOException {
        TypeSpec.Builder process = this.createClass();
        JavaFile.builder(getPackageName(), process.build())
                .addFileComment("Generated code from Param Init. Do not modify!")
                .build()
                .writeTo(annotationEnv.mFiler);
    }

    public CodeBlock getClassTypeDoc() {
        return CodeBlock.builder().
                add("ViewModel 传参，持久化的工具类\n")
                .add("@author zf").build();
    }

    public List<ViewModelParamHolder> getHolders() {
        List<ViewModelParamHolder> paramHolderList = new ArrayList<>();
        ViewModelParamHolder holder;
        for (Element element : itemElement) {
            ParamInitClass paramInitClass = element.getAnnotation(ParamInitClass.class);
            if (paramInitClass != null) {
                continue;
            }

            holder = getHolder(element);
            if (holder == null) {
                continue;
            }
            paramHolderList.add(holder);
        }
        return paramHolderList;
    }

    public List<ViewModelParamHolder> getBindingHolders() {
        List<ViewModelParamHolder> paramHolderList = new ArrayList<>();
        ViewModelParamHolder holder;
        for (Element element : paramBindingElement) {
            ParamInitClass paramInitClass = element.getAnnotation(ParamInitClass.class);
            if (paramInitClass != null) {
                continue;
            }

            holder = getHolder(element);
            if (holder == null) {
                continue;
            }
            paramHolderList.add(holder);
        }
        return paramHolderList;
    }


    private void printMessageCannotHandleType(Element element) {
        final Name simpleName = element.getSimpleName();
        getMessager().printMessage(Diagnostic.Kind.ERROR,
                String.format("Cannot handle type %1$s.",
                        simpleName),
                element);
    }

    protected ViewModelParamHolder onPrimitiveHolder(Element element) {
        return new ViewModelParamPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
//        throw new UnsupportedOperationException(String.format("Basic data type(%s) is not supported, please use wrapper type!", element.asType()));
    }

    protected ViewModelParamHolder onArrayPrimitiveHolder(Element element) {
        return new ViewModelParamArrayPrimitiveHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onArrayStringHolder(Element element) {
        return new ViewModelParamArrayStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onArrayParcelableHolder(Element element) {
        return new ViewModelParamArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onArrayCharSequenceHolder(Element element) {
        return new ViewModelParamArrayCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onPackageClassHolder(Element element) {

        return new ViewModelParamPackageClassHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onStringHolder(Element element) {

        return new ViewModelParamStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onSizeFHolder(Element element) {
        return new ViewModelParamSizeFHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onSizeHolder(Element element) {
        return new ViewModelParamSizeHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onBinderHolder(Element element) {

        return new ViewModelParamBinderHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onSparseArrayParcelableHolder(Element element) {

        return new ViewModelParamSparseArrayParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }


    protected ViewModelParamHolder onArrayListIntegerHolder(Element element) {

        return new ViewModelParamArrayListIntegerHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onArrayListStringHolder(Element element) {

        return new ViewModelParamArrayListStringHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onArrayListParcelableHolder(Element element) {

        return new ViewModelParamArrayListParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onArrayListCharSequenceHolder(Element element) {

        return new ViewModelParamArrayListCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onBundleHolder(Element element) {
        return new ViewModelParamBundleHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onParcelableHolder(Element element) {

        return new ViewModelParamParcelableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onSerializableHolder(Element element) {

        return new ViewModelParamSerializableHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder onCharSequenceHolder(Element element) {
        return new ViewModelParamCharSequenceHolder.CreationHolder(annotationEnv, element, isSupportV4(), isAndroidX()).create();
    }

    protected ViewModelParamHolder getHolder(Element element) {

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
