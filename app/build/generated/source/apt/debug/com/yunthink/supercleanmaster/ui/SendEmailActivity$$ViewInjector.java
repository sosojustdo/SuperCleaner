// Generated code from Butter Knife. Do not modify!
package com.yunthink.supercleanmaster.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SendEmailActivity$$ViewInjector {
  public static void inject(Finder finder, final com.yunthink.supercleanmaster.ui.SendEmailActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624102, "field 'subVersion'");
    target.subVersion = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624098, "field 'back_img'");
    target.back_img = (android.widget.ImageView) view;
  }

  public static void reset(com.yunthink.supercleanmaster.ui.SendEmailActivity target) {
    target.subVersion = null;
    target.back_img = null;
  }
}
