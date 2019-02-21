// Generated code from Butter Knife. Do not modify!
package com.yunthink.supercleanmaster.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ShortCutActivity$$ViewInjector {
  public static void inject(Finder finder, final com.yunthink.supercleanmaster.ui.ShortCutActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624173, "field 'layoutAnim'");
    target.layoutAnim = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624172, "field 'mRelativeLayout'");
    target.mRelativeLayout = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624175, "field 'cleanLightImg'");
    target.cleanLightImg = (android.widget.ImageView) view;
  }

  public static void reset(com.yunthink.supercleanmaster.ui.ShortCutActivity target) {
    target.layoutAnim = null;
    target.mRelativeLayout = null;
    target.cleanLightImg = null;
  }
}
