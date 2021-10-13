package com.zf.plugins.param.init;

import com.squareup.javapoet.ClassName;

public class ClassNameConstant {

    public static final ClassName SIZE = ClassName.get("android.util", "Size");
    public static final ClassName SIZE_F = ClassName.get("android.util", "SizeF");
    public static final ClassName BINDER = ClassName.get("android.os", "Binder");
    public static final ClassName I_BINDER = ClassName.get("android.os", "IBinder");

    public static final ClassName BUNDLE = ClassName.get("android.os", "Bundle");
    public static final ClassName INTENT = ClassName.get("android.content", "Intent");
    public static final ClassName PARCELABLE = ClassName.get("android.os", "Parcelable");
    public static final ClassName SERIALIZABLE = ClassName.get("java.io", "Serializable");
    public static final ClassName CHAR_SEQUENCE = ClassName.get("java.lang", "CharSequence");
    public static final ClassName ARRAY_LIST = ClassName.get("java.util", "ArrayList");
    public static final ClassName STRING = ClassName.get("java.lang", "String");
    public static final ClassName ACTIVITY = ClassName.get("android.app", "Activity");
    public static final ClassName X_ACTIVITY = ClassName.get("androidx.core.app", "ComponentActivity");
    public static final ClassName V4_FRAGMENT = ClassName.get("android.support.v4.app", "Fragment");
    public static final ClassName FRAGMENT = ClassName.get("android.app", "Fragment");
    public static final ClassName X_FRAGMENT = ClassName.get("androidx.fragment.app", "Fragment");
    public static final ClassName VIEW_MODEL = ClassName.get("androidx.lifecycle", "ViewModel");
    public static final ClassName SAVED_STATE_HANDLE = ClassName.get("androidx.lifecycle", "SavedStateHandle");
    public static final ClassName MUTABLE_LIVE_DATA = ClassName.get("androidx.lifecycle", "MutableLiveData");
    public static final ClassName LIVE_DATA = ClassName.get("androidx.lifecycle", "LiveData");

    public static final ClassName SPARSE_ARRAY = ClassName.get("android.util", "SparseArray");


    public static final ClassName REQUIRES_API_SUPPORT_ANNOTATION = ClassName.get("android.support.annotation", "RequiresApi");
    public static final ClassName REQUIRES_API_ANDROID_X_ANNOTATION = ClassName.get("androidx.annotation", "RequiresApi");

    public static final ClassName I_VIEW_MODEL_PARAM_INIT =ClassName.get("com.zf.plugins.param.init.viewmodel", "IViewModelParamInit");

    public static final ClassName BUILD = ClassName.get("android.os", "Build");

    public static final ClassName NULLABLE = ClassName.get("android.annotation", "Nullable");
    public static final ClassName NONNULL = ClassName.get("android.annotation", "NonNull");

    public static final ClassName NULLABLE_X = ClassName.get("androidx.annotation", "Nullable");
    public static final ClassName NONNULL_X = ClassName.get("androidx.annotation", "NonNull");



    public static ClassName getNullableClassName(boolean isAndroidX){
        return isAndroidX? ClassNameConstant.NULLABLE_X:ClassNameConstant.NULLABLE;
    }

    public static ClassName getNonnullClassName(boolean isAndroidX){
        return isAndroidX? ClassNameConstant.NONNULL_X:ClassNameConstant.NONNULL;
    }

    public static ClassName getRequiresApiSupportAnnotation(boolean isAndroidX){
        return isAndroidX? ClassNameConstant.REQUIRES_API_ANDROID_X_ANNOTATION:ClassNameConstant.REQUIRES_API_SUPPORT_ANNOTATION;
    }


}
