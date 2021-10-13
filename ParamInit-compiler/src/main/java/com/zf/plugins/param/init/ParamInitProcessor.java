package com.zf.plugins.param.init;

import com.google.auto.service.AutoService;
import com.zf.param.init.ParamInitActivity;
import com.zf.param.init.ParamInitClass;
import com.zf.param.init.ParamInitFragment;
import com.zf.param.init.ParamInitViewModel;
import com.zf.plugins.param.init.handler.activity.ActivityParamInitHandler;
import com.zf.plugins.param.init.handler.fragment.FragmentParamInitHandler;
import com.zf.plugins.param.init.handler.fragment.V4FragmentParamInitHandler;
import com.zf.plugins.param.init.handler.fragment.XFragmentParamInitHandler;
import com.zf.plugins.param.init.handler.viewmodel.ViewModelParamInitHandler;

import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class ParamInitProcessor extends AbstractProcessor {

    AnnotationEnv annotationEnv;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        annotationEnv = new AnnotationEnv(processingEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> annotations = new HashSet<>();
        annotations.add(ParamInitClass.class.getCanonicalName());
        annotations.add(ParamInitActivity.class.getCanonicalName());
        annotations.add(ParamInitFragment.class.getCanonicalName());
        annotations.add(ParamInitViewModel.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (roundEnv.processingOver() || roundEnv.errorRaised()) {
            return true;
        }
        processAnnotations(annotations, roundEnv);
        return true;
    }

    public Map<TypeElement, List<Element>> findParamAnnotationByFileCategory(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Map<TypeElement, List<Element>> elementsMap = new LinkedHashMap<>();

        annotations.forEach(new Consumer<TypeElement>() {
            @Override
            public void accept(TypeElement typeElement) {


                Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(typeElement);

                for (Element element : elements) {

                    String classFullName = null;
                    TypeElement classElement = null;
                    if (element.getKind() == ElementKind.FIELD) {
                        classElement = (TypeElement) element.getEnclosingElement();
                        classFullName = classElement.toString();
                    } else if (element.getKind() == ElementKind.PACKAGE) {
                        classElement = (TypeElement) element;
                        classFullName = element.toString();
                    } else if (element.getKind() == ElementKind.METHOD) {
                        classElement = (TypeElement) element.getEnclosingElement().getEnclosingElement();
                        classFullName = element.getEnclosingElement().getEnclosingElement().toString();
                    } else if (element.getKind() == ElementKind.CLASS) {
                        classElement = (TypeElement) element;
                        classFullName = element.toString();
                    }

                    if (classFullName == null) {
                        continue;
                    }

//                    StringBuffer sg = new StringBuffer();
//                    List<? extends Element> allMembers = annotationEnv.mElementUtils.get(classElement);
//                    for (Element allMember : allMembers) {
//                        sg.append(allMember.toString()).append(",");
//                    }
//                    annotationEnv.mMessager.printMessage(Diagnostic.Kind.WARNING, sg.toString(), typeElement);


                    List<Element> elementList = null;
                    for (Map.Entry<TypeElement, List<Element>> next : elementsMap.entrySet()) {
                        if (classFullName.equals(next.getKey().toString())) {
                            elementList = next.getValue();
                        }
                    }

                    if (elementList == null) {
                        elementList = new ArrayList<>();
                        elementsMap.put(classElement, elementList);
                    }
                    if (!elementList.contains(element)) {
                        elementList.add(element);
                    }
                }
            }
        });

        return elementsMap;
    }

    public ParamInitClassType getParamInitClassType(TypeMirror typeMirror) {
        final List<String> superClasses = Utils.getSuperClasses(typeMirror);
        if (superClasses.contains(ClassNameConstant.X_ACTIVITY.reflectionName())) {
            return ParamInitClassType.X_ACTIVITY;
        } else if (superClasses.contains(ClassNameConstant.ACTIVITY.reflectionName())) {
            return ParamInitClassType.ACTIVITY;
        } else if (superClasses.contains(ClassNameConstant.FRAGMENT.reflectionName())) {
            return ParamInitClassType.FRAGMENT;
        } else if (superClasses.contains(ClassNameConstant.V4_FRAGMENT.reflectionName())) {
            return ParamInitClassType.V4_FRAGMENT;
        } else if (superClasses.contains(ClassNameConstant.X_FRAGMENT.reflectionName())) {
            return ParamInitClassType.X_FRAGMENT;
        } else if (superClasses.contains(ClassNameConstant.VIEW_MODEL.reflectionName())) {
            return ParamInitClassType.VIEW_MODEL;
        } else {
            return ParamInitClassType.OTHER;
        }
    }

    public void processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Map<TypeElement, List<Element>> paramAnnotationMap = findParamAnnotationByFileCategory(annotations, roundEnv);
        for (Map.Entry<TypeElement, List<Element>> elementListEntry : paramAnnotationMap.entrySet()) {
            TypeElement classElement = elementListEntry.getKey();
            List<Element> childElements = elementListEntry.getValue();
            final ParamInitClassType paramInitClassType = getParamInitClassType(classElement.asType());

            try {
                if (paramInitClassType == ParamInitClassType.ACTIVITY) {
                    TypeMirror typeMirror = classElement.asType();
                    ArrayList<Element> paramBindingList = new ArrayList<>(childElements);
                    List<String> superClasses = Utils.getSuperClasses(typeMirror);
                    if (superClasses.contains(ClassNameConstant.ACTIVITY.reflectionName())) {
                        List<? extends Element> allMembers = annotationEnv.mElementUtils.getAllMembers(classElement);
                        for (Element allMember : allMembers) {
                            if (allMember instanceof VariableElement) {
                                ParamInitActivity annotation = allMember.getAnnotation(ParamInitActivity.class);
                                if (annotation != null) {
                                    if (!paramBindingList.contains(allMember)) {
                                        paramBindingList.add(allMember);
                                    }
                                }
                            }
                        }
                    }
                    new ActivityParamInitHandler(annotationEnv, classElement, paramBindingList, paramBindingList, false, false).generateClass();
                } else if (paramInitClassType == ParamInitClassType.X_ACTIVITY) {
                    TypeMirror typeMirror = classElement.asType();
                    ArrayList<Element> paramBindingList = new ArrayList<>(childElements);
                    List<String> superClasses = Utils.getSuperClasses(typeMirror);
                    if (superClasses.contains(ClassNameConstant.X_ACTIVITY.reflectionName())) {
                        List<? extends Element> allMembers = annotationEnv.mElementUtils.getAllMembers(classElement);
                        for (Element allMember : allMembers) {
                            if (allMember instanceof VariableElement) {
                                ParamInitActivity annotation = allMember.getAnnotation(ParamInitActivity.class);
                                if (annotation != null) {
                                    if (!paramBindingList.contains(allMember)) {
                                        paramBindingList.add(allMember);
                                    }
                                }
                            }
                        }
                    }
                    new ActivityParamInitHandler(annotationEnv, classElement, paramBindingList, paramBindingList, false, true).generateClass();
                } else if (paramInitClassType == ParamInitClassType.FRAGMENT) {
                    TypeMirror typeMirror = classElement.asType();
                    ArrayList<Element> paramBindingList = new ArrayList<>(childElements);
                    List<String> superClasses = Utils.getSuperClasses(typeMirror);
                    if (superClasses.contains(ClassNameConstant.FRAGMENT.reflectionName())) {
                        List<? extends Element> allMembers = annotationEnv.mElementUtils.getAllMembers(classElement);
                        for (Element allMember : allMembers) {
                            if (allMember instanceof VariableElement) {
                                ParamInitFragment annotation = allMember.getAnnotation(ParamInitFragment.class);
                                if (annotation != null) {
                                    if (!paramBindingList.contains(allMember)) {
                                        paramBindingList.add(allMember);
                                    }
                                }
                            }
                        }
                    }
                    new FragmentParamInitHandler(annotationEnv, classElement, paramBindingList, paramBindingList, false, false).generateClass();
                } else if (paramInitClassType == ParamInitClassType.V4_FRAGMENT) {
                    TypeMirror typeMirror = classElement.asType();
                    ArrayList<Element> paramBindingList = new ArrayList<>(childElements);
                    List<String> superClasses = Utils.getSuperClasses(typeMirror);
                    if (superClasses.contains(ClassNameConstant.V4_FRAGMENT.reflectionName())) {
                        List<? extends Element> allMembers = annotationEnv.mElementUtils.getAllMembers(classElement);
                        for (Element allMember : allMembers) {
                            if (allMember instanceof VariableElement) {
                                ParamInitFragment annotation = allMember.getAnnotation(ParamInitFragment.class);
                                if (annotation != null) {
                                    if (!paramBindingList.contains(allMember)) {
                                        paramBindingList.add(allMember);
                                    }
                                }
                            }
                        }
                    }
                    new V4FragmentParamInitHandler(annotationEnv, classElement, paramBindingList, paramBindingList, true, false).generateClass();
                } else if (paramInitClassType == ParamInitClassType.X_FRAGMENT) {
                    TypeMirror typeMirror = classElement.asType();
                    ArrayList<Element> paramBindingList = new ArrayList<>(childElements);
                    List<String> superClasses = Utils.getSuperClasses(typeMirror);
                    if (superClasses.contains(ClassNameConstant.X_FRAGMENT.reflectionName())) {
                        List<? extends Element> allMembers = annotationEnv.mElementUtils.getAllMembers(classElement);
                        for (Element allMember : allMembers) {
                            if (allMember instanceof VariableElement) {
                                ParamInitFragment annotation = allMember.getAnnotation(ParamInitFragment.class);
                                if (annotation != null) {
                                    if (!paramBindingList.contains(allMember)) {
                                        paramBindingList.add(allMember);
                                    }
                                }
                            }
                        }
                    }
                    new XFragmentParamInitHandler(annotationEnv, classElement, paramBindingList, paramBindingList, false, true).generateClass();
                } else if (paramInitClassType == ParamInitClassType.VIEW_MODEL) {
                    TypeMirror typeMirror = classElement.asType();
                    ArrayList<Element> paramBindingList = new ArrayList<>(childElements);
                    List<String> superClasses = Utils.getSuperClasses(typeMirror);
                    if (superClasses.contains(ClassNameConstant.VIEW_MODEL.reflectionName())) {
                        List<? extends Element> allMembers = annotationEnv.mElementUtils.getAllMembers(classElement);
                        for (Element allMember : allMembers) {
                            if (allMember instanceof VariableElement) {
                                ParamInitViewModel annotation = allMember.getAnnotation(ParamInitViewModel.class);
                                if (annotation != null) {
                                    if (!paramBindingList.contains(allMember)) {
                                        paramBindingList.add(allMember);
                                    }
                                }
                            }
                        }
                    }
                    new ViewModelParamInitHandler(annotationEnv, classElement, childElements, paramBindingList, false, true).generateClass();
                } else {
                    annotationEnv.mMessager.printMessage(Diagnostic.Kind.ERROR, "$T Target property type is not activity, fragment,viewmodel type", classElement);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
