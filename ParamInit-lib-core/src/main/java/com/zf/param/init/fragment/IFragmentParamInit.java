package com.zf.param.init.fragment;


import android.app.Fragment;
import android.os.Bundle;

interface IFragmentParamInit {

    void init(final Fragment fragment, final Bundle bundle);

    void init(final Fragment fragment, final Bundle arguments, final Bundle savedInstanceState);

    void saveState(final Fragment fragment, final Bundle outState);
}
