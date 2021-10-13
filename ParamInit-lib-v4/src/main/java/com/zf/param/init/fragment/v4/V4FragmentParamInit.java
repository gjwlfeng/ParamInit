package com.zf.param.init.fragment.v4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zf.param.init.ParamInitUtils;

import java.util.HashMap;

public class V4FragmentParamInit {

    private static final String PARAM_INIT_TAG="ParamInit";
    private static final HashMap<String, IV4FragmentParamInit> sIParamInitMap = new HashMap<>();


    private static IV4FragmentParamInit getV4FragmentParamInit(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        IV4FragmentParamInit fragmentParamInit = sIParamInitMap.get(className);
        if (fragmentParamInit == null) {
            Class<?> aClass = Class.forName(className);
            fragmentParamInit = (IV4FragmentParamInit) aClass.newInstance();
            sIParamInitMap.put(className, fragmentParamInit);
        }
        return fragmentParamInit;
    }

    public static void init(final Class<? extends Fragment> targetClz,final Fragment fragment, final Bundle bundle) {
        String initClassName = ParamInitUtils.getInitClassName(
                targetClz.getPackage().getName(),
                targetClz.getSimpleName());
        try {
            IV4FragmentParamInit fragmentParamInit = getV4FragmentParamInit(initClassName);
            fragmentParamInit.init(fragment, bundle);
        }  catch (ClassNotFoundException e) {
            Log.e(PARAM_INIT_TAG, e.getMessage() == null ? String.format("\"%s\" class not found！", initClassName) : e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init(final Class<? extends Fragment> targetClz,final Fragment fragment, final Bundle arguments, final Bundle savedInstanceState) {

        String initClassName = ParamInitUtils.getInitClassName(
                targetClz.getPackage().getName(),
                targetClz.getSimpleName());
        try {
            IV4FragmentParamInit fragmentParamInit = getV4FragmentParamInit(initClassName);
            fragmentParamInit.init(fragment, arguments, savedInstanceState);
        }  catch (ClassNotFoundException e) {
            Log.e(PARAM_INIT_TAG, e.getMessage() == null ? String.format("\"%s\" class not found！", initClassName) : e.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveState(final Class<? extends Fragment> targetClz,final Fragment fragment, final Bundle outState) {
        String initClassName = ParamInitUtils.getInitClassName(
                targetClz.getPackage().getName(),
                targetClz.getSimpleName());
        try {
            IV4FragmentParamInit fragmentParamInit = getV4FragmentParamInit(initClassName);
            fragmentParamInit.saveState(fragment, outState);
        }  catch (ClassNotFoundException e) {
            Log.e(PARAM_INIT_TAG, e.getMessage() == null ? String.format("\"%s\" class not found！", initClassName) : e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
