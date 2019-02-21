// Generated code from Butter Knife. Do not modify!
package com.yunthink.supercleanmaster.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class CpucoolingActivity$$ViewInjector {
  public static void inject(Finder finder, final com.yunthink.supercleanmaster.ui.CpucoolingActivity target, Object source) {
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
    view = finder.findRequiredView(source, 2131624146, "field 'cpu_Fun'");
    target.cpu_Fun = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624147, "field 'cpu_Xuehua'");
    target.cpu_Xuehua = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624148, "field 'cpu_Jw_Text'");
    target.cpu_Jw_Text = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624143, "field 'cpu_Jw_rl'");
    target.cpu_Jw_rl = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624098, "field 'back_Img'");
    target.back_Img = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624133, "field 'back_Img2'");
    target.back_Img2 = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624142, "field 'cpu_Sm'");
    target.cpu_Sm = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624141, "field 'cpu_Xs_Rl'");
    target.cpu_Xs_Rl = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624149, "field 'zh_Rl'");
    target.zh_Rl = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624150, "field 'init_Fun'");
    target.init_Fun = (android.widget.ImageView) view;
  }

  public static void reset(com.yunthink.supercleanmaster.ui.CpucoolingActivity target) {
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
    target.cpu_Fun = null;
    target.cpu_Xuehua = null;
    target.cpu_Jw_Text = null;
    target.cpu_Jw_rl = null;
    target.back_Img = null;
    target.back_Img2 = null;
    target.cpu_Sm = null;
    target.cpu_Xs_Rl = null;
    target.zh_Rl = null;
    target.init_Fun = null;
  }
}
