package com.zf.param.init;

public class ParamInitUtils {
    public static String getInitClassName(String packageName, String simpleName) {
        return packageName.concat(".").concat(getSimpleClassName(simpleName));
    }

    public static String getSimpleClassName(String simpleName) {
        return simpleName.concat(Constant.CLASS_NAME_PREFIX);
    }
}
