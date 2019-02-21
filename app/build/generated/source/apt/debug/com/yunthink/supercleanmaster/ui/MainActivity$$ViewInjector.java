// Generated code from Butter Knife. Do not modify!
package com.yunthink.supercleanmaster.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.yunthink.supercleanmaster.ui.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624153, "field 'container'");
    target.container = (android.widget.FrameLayout) view;
    view = finder.findRequiredView(source, 2131624152, "field 'mDrawerLayout'");
    target.mDrawerLayout = (android.support.v4.widget.DrawerLayout) view;
  }

  public static void reset(com.yunthink.supercleanmaster.ui.MainActivity target) {
    target.container = null;
    target.mDrawerLayout = null;
  }
}
