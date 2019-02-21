// Generated code from Butter Knife. Do not modify!
package com.yunthink.supercleanmaster.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainFragment$$ViewInjector {
  public static void inject(Finder finder, final com.yunthink.supercleanmaster.fragment.MainFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624200, "field 'arcStore'");
    target.arcStore = (com.yunthink.supercleanmaster.widget.circleprogress.ArcProgress) view;
    view = finder.findRequiredView(source, 2131624198, "field 'arcProcess'");
    target.arcProcess = (com.yunthink.supercleanmaster.widget.circleprogress.ArcProgress) view;
    view = finder.findRequiredView(source, 2131624202, "field 'cpuProcess'");
    target.cpuProcess = (com.yunthink.supercleanmaster.widget.circleprogress.ArcProgress) view;
    view = finder.findRequiredView(source, 2131624201, "field 'capacity'");
    target.capacity = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624208, "field 'c1_js'");
    target.c1_js = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624207, "field 'c2_ql'");
    target.c2_ql = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624209, "field 'c3_cpu'");
    target.c3_cpu = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131624210, "field 'c4_dc'");
    target.c4_dc = (android.widget.RelativeLayout) view;
  }

  public static void reset(com.yunthink.supercleanmaster.fragment.MainFragment target) {
    target.arcStore = null;
    target.arcProcess = null;
    target.cpuProcess = null;
    target.capacity = null;
    target.c1_js = null;
    target.c2_ql = null;
    target.c3_cpu = null;
    target.c4_dc = null;
  }
}
