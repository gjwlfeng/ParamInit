package com.zf.param.init.viewmodel;

import android.util.Log;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.zf.param.init.ParamInitUtils;

import java.lang.reflect.Method;

public class ViewModelParamInit {

    private static final String PARAM_INIT_TAG = "ParamInit";

    public static void init(final Class<? extends ViewModel> targetClass, final ViewModel viewModel, final SavedStateHandle stateHandle) {
        String initClassName = ParamInitUtils.getInitClassName(
                targetClass.getPackage().getName(),
                targetClass.getSimpleName());
        try {
            Class<?> aClass = Class.forName(initClassName);
            Method init = aClass.getDeclaredMethod("init", ViewModel.class, SavedStateHandle.class);
            init.invoke(null, viewModel, stateHandle);
        } catch (ClassNotFoundException e) {
            Log.e(PARAM_INIT_TAG, e.getMessage() == null ? String.format("\"%s\" class not found！", initClassName) : e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
