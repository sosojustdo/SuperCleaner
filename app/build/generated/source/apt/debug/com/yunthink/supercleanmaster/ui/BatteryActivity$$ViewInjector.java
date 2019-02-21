// Generated code from Butter Knife. Do not modify!
package com.yunthink.supercleanmaster.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class BatteryActivity$$ViewInjector {
  public static void inject(Finder finder, final com.yunthink.supercleanmaster.ui.BatteryActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624122, "field 'mListView'");
    target.mListView = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131624117, "field 'mwaveView'");
    target.mwaveView = (com.john.waveview.WaveView) view;
    view = finder.findRequiredView(source, 2131624116, "field 'header'");
    target.header = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624118, "field 'textCounter'");
    target.textCounter = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624119, "field 'sufix'");
    target.sufix = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624123, "field 'bottom_lin'");
    target.bottom_lin = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131624125, "field 'mProgressBar'");
    target.mProgressBar = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131624127, "field 'mProgressBarText'");
    target.mProgressBarText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624120, "field 'pb_View'");
    target.pb_View = (android.widget.ProgressBar) view;
    view = finder.findRequiredView(source, 2131624121, "field 'pb_Text'");
    target.pb_Text = (android.widget.TextView) view;
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
    view = finder.findRequiredView(source, 2131624129, "field 'besom_img'");
    target.besom_img = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624128, "field 'besom_Am_Rl'");
    target.besom_Am_Rl = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624131, "field 'clean_Text'");
    target.clean_Text = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624098, "field 'back_Img'");
    target.back_Img = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624135, "field 'sd_Text'");
    target.sd_Text = (android.widget.TextView) view;
  }

  public static void reset(com.yunthink.supercleanmaster.ui.BatteryActivity target) {
    target.mListView = null;
    target.mwaveView = null;
    target.header = null;
    target.textCounter = null;
    target.sufix = null;
    target.bottom_lin = null;
    target.mProgressBar = null;
    target.mProgressBarText = null;
    target.pb_View = null;
    target.pb_Text = null;
    target.clearButton = null;
    target.besom_img = null;
    target.besom_Am_Rl = null;
    target.clean_Text = null;
    target.back_Img = null;
    target.sd_Text = null;
  }
}
