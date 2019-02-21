// Generated code from Butter Knife. Do not modify!
package com.yunthink.supercleanmaster.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SoftwareManageActivity$$ViewInjector {
  public static void inject(Finder finder, final com.yunthink.supercleanmaster.ui.SoftwareManageActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624113, "field 'tabs'");
    target.tabs = (com.yunthink.supercleanmaster.views.SlidingTab) view;
    view = finder.findRequiredView(source, 2131624114, "field 'pager'");
    target.pager = (android.support.v4.view.ViewPager) view;
  }

  public static void reset(com.yunthink.supercleanmaster.ui.SoftwareManageActivity target) {
    target.tabs = null;
    target.pager = null;
  }
}
