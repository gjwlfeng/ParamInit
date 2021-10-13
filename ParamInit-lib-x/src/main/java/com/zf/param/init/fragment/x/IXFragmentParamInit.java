package com.zf.param.init.fragment.x;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

public interface IXFragmentParamInit {

    void init(final Fragment fragment, final Bundle bundle);

    void init(final Fragment fragment, final Bundle arguments, final Bundle savedInstanceState);

    void saveState(final Fragment fragment, final Bundle outState);
}
