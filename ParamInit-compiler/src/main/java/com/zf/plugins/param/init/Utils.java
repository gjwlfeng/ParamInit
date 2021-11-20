package com.zf.plugins.param.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

public class Utils {
    /**
     * 获取泛型
     *
     * @param fullName
     * @return
     */
    public static String[] getGenericFullName(String fullName) {
        if (fullName == null) {
            return null;
        }
        int genericStartPosition = fullName.indexOf("<");
        if (genericStartPosition < 0) {
            return null;
        }
        String generics = fullName.substring(genericStartPosition, fullName.length() - 1);

        return generics.split(",");
    }


    public static String capitalize(String str) {
        char[] chars = str.toCharArray();
        if (chars.length > 0 && chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char) (chars[0] - 32);
        }
        return new String(chars);
    }


    public static String generateGetMethodName(String str) {
        char[] chars = str.toCharArray();
        if (chars.length == 1) {
            return "get" + str.toUpperCase();
        } else {
            if (chars.length > 0 && chars[0] == 'm') {
                if (chars[1] >= 'A' && chars[1] <= 'Z') {
                    return "get" + new String(chars, 1, chars.length);
                }
            }
            return "get" + capitalize(str);
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        return str.trim().length() == 0;
    }


    public static List<String> getSuperClasses(TypeMirror typeMirror) {
        List<String> typeMirrors = new ArrayList<>();
        getSuperClassesInternal(typeMirror, typeMirrors);
        return typeMirrors;
    }

    public static List<String> getInterfaceClasses(TypeMirror typeMirror) {
        List<String> typeMirrors = new ArrayList<>();
        getInterfaceClassesInternal(typeMirror, typeMirrors);
        return typeMirrors;
    }

    private static void getSuperClassesInternal(TypeMirror typeMirror, List<String> typeMirrors) {
        if (typeMirror instanceof DeclaredType) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            Element element2 = declaredType.asElement();
            TypeElement typeElement = (TypeElement) element2;

            if (!typeElement.getKind().isInterface()) {
                typeMirrors.add(typeElement.toString());
            }

            TypeMirror superclass = typeElement.getSuperclass();
            if (superclass != null) {
                typeMirrors.add(superclass.toString());
                getSuperClassesInternal(superclass, typeMirrors);
            }
        }
    }

    private static void getInterfaceClassesInternal(TypeMirror typeMirror, List<String> typeMirrors) {
        if (typeMirror instanceof DeclaredType) {
            Element element = ((DeclaredType) typeMirror).asElement();
            TypeElement typeElement = (TypeElement) element;

            if (typeElement.getKind().isInterface()) {
                typeMirrors.add(typeElement.toString());
            }

            List<? extends TypeMirror> interfaces = typeElement.getInterfaces();
            if (interfaces != null && !interfaces.isEmpty()) {
                interfaces.forEach(new Consumer<TypeMirror>() {
                    @Override
                    public void accept(TypeMirror typeMirror) {
                        typeMirrors.add(typeMirror.toString());
                        getInterfaceClassesInternal(typeMirror, typeMirrors);
                    }
                });
            }

            TypeMirror superclass = typeElement.getSuperclass();
            if (superclass != null) {
                getInterfaceClassesInternal(superclass, typeMirrors);
            }
        }
    }
}
