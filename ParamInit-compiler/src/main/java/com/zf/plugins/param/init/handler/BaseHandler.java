package com.zf.plugins.param.init.handler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeSpec;
import com.zf.plugins.param.init.AnnotationEnv;
import com.zf.plugins.param.init.ClassNameConstant;
import com.zf.param.init.Constant;
import com.zf.param.init.ParamInitClass;
import com.zf.plugins.param.init.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public abstract class BaseHandler<T> {
    public final AnnotationEnv annotationEnv;
    public final TypeElement rootElement;
    public final List<? extends Element> itemElement;
    public List<? extends Element> paramBindingElement;
    private final boolean isSupportV4;
    private final boolean isAndroidX;

    public BaseHandler(AnnotationEnv annotationEnv, TypeElement rootElement, List<? extends Element> itemElement, List<? extends Element> paramBindingElement, boolean isSupportV4, boolean isAndroidX) {
        this.annotationEnv = annotationEnv;
        this.rootElement = rootElement;
        this.itemElement = itemElement;
        this.paramBindingElement = paramBindingElement;
        this.isAndroidX = isAndroidX;
        this.isSupportV4 = isSupportV4;
    }

    public boolean isSupportV4() {
        return isSupportV4;
    }

    public boolean isAndroidX() {
        return isAndroidX;
    }

    public Elements getElementUtils() {
        return annotationEnv.mElementUtils;
    }

    public Types getTypeUtils() {
        return annotationEnv.mTypeUtils;
    }

    public Messager getMessager() {
        return annotationEnv.mMessager;
    }

    public Filer getFiler() {
        return annotationEnv.mFiler;
    }


    public ClassName getRootElementClassName() {
        return ClassName.get(rootElement);
    }

    public ClassName getTargetClassName() {
        String packageName = getPackageName();
        String simpleName = getTargetClassSimpleName();
        return ClassName.get(packageName, simpleName);
    }

    public String getPackageName() {
        return getElementUtils().getPackageOf(rootElement).getQualifiedName().toString();
    }

    public String getTargetClassSimpleName() {
        return rootElement.getSimpleName().toString().concat(Constant.CLASS_NAME_SUFFIX);
    }

    public CodeBlock getClassTypeDoc() {
        return CodeBlock.builder().
                add("data operation tool class\n")
                .add("@author zf").build();
    }


    private void printMessageCannotHandleType(Element element) {
        final Name simpleName = element.getSimpleName();
        getMessager().printMessage(Diagnostic.Kind.ERROR,
                String.format("Cannot handle type %1$s.",
                        simpleName),
                element);
    }

    protected T onPrimitiveHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onArrayPrimitiveHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onArrayStringHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onArrayParcelableHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onArrayCharSequenceHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onPackageClassHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onStringHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onSizeFHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onSizeHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onBinderHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onSparseArrayParcelableHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }


    protected T onArrayListIntegerHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onArrayListStringHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onArrayListParcelableHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onArrayListCharSequenceHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onBundleHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onParcelableHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onSerializableHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }

    protected T onCharSequenceHolder(Element element) {
        printMessageCannotHandleType(element);
        throw new RuntimeException(String.format("Cannot handle type %1$s.", element.toString()));
    }


    public List<T> getHolders() {
        List<T> paramHolderList = new ArrayList<>();
        T holder;
        for (Element element : itemElement) {
            ParamInitClass paramInitClass = element.getAnnotation(ParamInitClass.class);
            if (paramInitClass != null) {
                continue;
            }
            holder = getHolder(element);
            paramHolderList.add(holder);
        }
        return paramHolderList;
    }

    public List<T> getBindingHolders() {
        List<T> paramHolderList = new ArrayList<>();
        T holder;
        for (Element element : paramBindingElement) {
            ParamInitClass paramInitClass = element.getAnnotation(ParamInitClass.class);
            if (paramInitClass != null) {
                continue;
            }
            holder = getHolder(element);
            paramHolderList.add(holder);
        }
        return paramHolderList;
    }

    protected abstract T getHolder(Element element);

    public abstract TypeSpec.Builder createClass();
}
