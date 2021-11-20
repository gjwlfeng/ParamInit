package com.zf.param.init.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zf.param.init.ParamInitUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ActivityParamInit {

    private static final String PARAM_INIT_TAG = "ParamInit";

    private static final HashMap<String, IActivityParamInit> sIParamInitMap = new HashMap<>();

    private static IActivityParamInit getActivityParamInit(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        IActivityParamInit iActivityParamInit = sIParamInitMap.get(className);
        if (iActivityParamInit == null) {
            Class<?> aClass = Class.forName(className);
            iActivityParamInit = (IActivityParamInit) aClass.getDeclaredConstructor().newInstance();
            sIParamInitMap.put(className, iActivityParamInit);
        }
        return iActivityParamInit;
    }

    public static void init(final Class<? extends Activity> targetClz, final Activity activity, final Intent intent, final Bundle savedInstanceState) {
        String initClassName = ParamInitUtils.getInitClassName(
                targetClz.getPackage().getName(),
                targetClz.getSimpleName());
        try {
            IActivityParamInit iActivityParamInit = getActivityParamInit(initClassName);
            iActivityParamInit.init(activity, intent, savedInstanceState);
        } catch (ClassNotFoundException e) {
            Log.e(PARAM_INIT_TAG, e.getMessage() == null ? String.format("\"%s\" class not found!", initClassName) : e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void init(final Class<? extends Activity> targetActivity, final Activity activity, final Bundle savedInstanceState) {
        String initClassName = ParamInitUtils.getInitClassName(
                targetActivity.getPackage().getName(),
                targetActivity.getSimpleName());
        try {
            IActivityParamInit iActivityParamInit = getActivityParamInit(initClassName);
            iActivityParamInit.init(activity, savedInstanceState);
        } catch (ClassNotFoundException e) {
            Log.e(PARAM_INIT_TAG, e.getMessage() == null ? String.format("\"%s\" class not found!", initClassName) : e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init(final Class<? extends Activity> targetClz, final Activity activity, final Intent intent) {
        String initClassName = ParamInitUtils.getInitClassName(
                targetClz.getPackage().getName(),
                targetClz.getSimpleName());
        try {
            IActivityParamInit iActivityParamInit = getActivityParamInit(initClassName);
            iActivityParamInit.init(activity, intent);
        } catch (ClassNotFoundException e) {
            Log.e(PARAM_INIT_TAG, e.getMessage() == null ? String.format("\"%s\" class not found!", initClassName) : e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveState(final Class<? extends Activity> targetClz, final Activity activity, final Bundle outState) {

        String initClassName = ParamInitUtils.getInitClassName(
                targetClz.getPackage().getName(),
                targetClz.getSimpleName());
        try {
            IActivityParamInit iActivityParamInit = getActivityParamInit(initClassName);
            iActivityParamInit.saveState(activity, outState);
        } catch (ClassNotFoundException e) {
            Log.e(PARAM_INIT_TAG, e.getMessage() == null ? String.format("\"%s\" class not found!", initClassName) : e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
