package com.zf.param.init.fragment.v4;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public interface IV4FragmentParamInit {

    void init(final Fragment fragment, final Bundle bundle);

    void init(final Fragment fragment, final Bundle arguments, final Bundle savedInstanceState);

    void saveState(final Fragment fragment, final Bundle outState);
}
