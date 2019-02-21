// Generated code from Butter Knife. Do not modify!
package com.yunthink.supercleanmaster.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class AutoStartFragment$$ViewInjector {
  public static void inject(Finder finder, final com.yunthink.supercleanmaster.fragment.AutoStartFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624122, "field 'listview'");
    target.listview = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131624123, "field 'bottom_lin'");
    target.bottom_lin = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131624197, "field 'disableButton' and method 'onClickDisable'");
    target.disableButton = (android.widget.Button) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClickDisable();
        }
      });
    view = finder.findRequiredView(source, 2131624196, "field 'topText'");
    target.topText = (android.widget.TextView) view;
  }

  public static void reset(com.yunthink.supercleanmaster.fragment.AutoStartFragment target) {
    target.listview = null;
    target.bottom_lin = null;
    target.disableButton = null;
    target.topText = null;
  }
}
