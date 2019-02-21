// Generated code from Butter Knife. Do not modify!
package com.yunthink.supercleanmaster.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class RubbishCleanActivity$$ViewInjector {
  public static void inject(Finder finder, final com.yunthink.supercleanmaster.ui.RubbishCleanActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624122, "field 'mListView'");
    target.mListView = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131624116, "field 'header'");
    target.header = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624118, "field 'textCounter'");
    target.textCounter = (com.yunthink.supercleanmaster.widget.textcounter.CounterView) view;
    view = finder.findRequiredView(source, 2131624119, "field 'sufix'");
    target.sufix = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624120, "field 'pb_view'");
    target.pb_view = (android.widget.ProgressBar) view;
    view = finder.findRequiredView(source, 2131624121, "field 'pb_text'");
    target.pb_text = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624162, "field 'back_img'");
    target.back_img = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624098, "field 'back_img2'");
    target.back_img2 = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624163, "field 'noCache'");
    target.noCache = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624165, "field 'clean_Plan'");
    target.clean_Plan = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624166, "field 'clean_Success'");
    target.clean_Success = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624168, "field 'clean_Star2'");
    target.clean_Star2 = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624123, "field 'bottom_lin'");
    target.bottom_lin = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131624124, "field 'clearButton' and method 'onClickClear'");
    target.clearButton = (android.widget.Button) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClickClear();
        }
      });
    view = finder.findRequiredView(source, 2131624171, "field 'spin_Kit'");
    target.spin_Kit = (android.widget.ProgressBar) view;
  }

  public static void reset(com.yunthink.supercleanmaster.ui.RubbishCleanActivity target) {
    target.mListView = null;
    target.header = null;
    target.textCounter = null;
    target.sufix = null;
    target.pb_view = null;
    target.pb_text = null;
    target.back_img = null;
    target.back_img2 = null;
    target.noCache = null;
    target.clean_Plan = null;
    target.clean_Success = null;
    target.clean_Star2 = null;
    target.bottom_lin = null;
    target.clearButton = null;
    target.spin_Kit = null;
  }
}
